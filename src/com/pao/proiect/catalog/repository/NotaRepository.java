package com.pao.proiect.catalog.repository;

import com.pao.proiect.catalog.model.Materie;
import com.pao.proiect.catalog.model.Nota;
import com.pao.proiect.catalog.model.Profesor;
import com.pao.proiect.catalog.model.Student;
import com.pao.proiect.catalog.model.Grupa;
import com.pao.proiect.catalog.model.Specializare;
import com.pao.proiect.catalog.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NotaRepository implements Repository<Nota, Integer> {

    private Connection getConn() {
        return DatabaseConnection.getInstance().getConnection();
    }

    // Salveaza o nota pentru un student — operatie simpla
    @Override
    public void save(Nota nota) {
        String sql = """
            INSERT INTO note (student_id, materie_id, valoare, data)
            VALUES (
                (SELECT id FROM studenti WHERE email = ?),
                (SELECT id FROM materii WHERE nume = ?),
                ?, ?
            )
        """;
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            // Aceasta metoda necesita contextul studentului — foloseste saveForStudent()
            throw new UnsupportedOperationException("Foloseste saveForStudent(student, nota)");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Tranzactie JDBC: inregistreaza nota + verifica sa nu existe duplicat
    // Daca ceva esueaza, rollback complet
    public void saveForStudent(Student student, Nota nota) {
        Connection conn = getConn();
        String sqlVerifica = "SELECT COUNT(*) FROM note WHERE student_id=(SELECT id FROM studenti WHERE email=?) AND materie_id=(SELECT id FROM materii WHERE nume=?) AND data=?";
        String sqlInsert = """
            INSERT INTO note (student_id, materie_id, valoare, data)
            VALUES (
                (SELECT id FROM studenti WHERE email = ?),
                (SELECT id FROM materii WHERE nume = ?),
                ?, ?
            )
        """;
        try {
            conn.setAutoCommit(false);
            try (PreparedStatement psVerifica = conn.prepareStatement(sqlVerifica)) {
                psVerifica.setString(1, student.getEmail());
                psVerifica.setString(2, nota.getMaterie().getNume());
                psVerifica.setString(3, nota.getData().toString());
                try (ResultSet rs = psVerifica.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        conn.rollback();
                        throw new RuntimeException("Nota deja existenta pentru studentul "
                                + student.getNume() + " la " + nota.getMaterie().getNume()
                                + " in data " + nota.getData());
                    }
                }
            }
            try (PreparedStatement psInsert = conn.prepareStatement(sqlInsert)) {
                psInsert.setString(1, student.getEmail());
                psInsert.setString(2, nota.getMaterie().getNume());
                psInsert.setDouble(3, nota.getValoare());
                psInsert.setString(4, nota.getData().toString());
                psInsert.executeUpdate();
            }
            conn.commit();
        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ex) { /* ignored */ }
            throw new RuntimeException("Eroare saveForStudent nota: " + e.getMessage(), e);
        } finally {
            try { conn.setAutoCommit(true); } catch (SQLException e) { /* ignored */ }
        }
    }

    @Override
    public Optional<Nota> findById(Integer id) {
        String sql = """
            SELECT n.valoare, n.data,
                   m.nume AS mat_nume, m.credite,
                   p.nume AS prof_nume, p.prenume AS prof_prenume,
                   p.email AS prof_email, p.departament, p.titlu
            FROM note n
            JOIN materii m ON n.materie_id = m.id
            JOIN profesori p ON m.profesor_id = p.id
            WHERE n.id = ?
        """;
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare findById nota: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<Nota> findAll() {
        List<Nota> list = new ArrayList<>();
        String sql = """
            SELECT n.valoare, n.data,
                   m.nume AS mat_nume, m.credite,
                   p.nume AS prof_nume, p.prenume AS prof_prenume,
                   p.email AS prof_email, p.departament, p.titlu
            FROM note n
            JOIN materii m ON n.materie_id = m.id
            JOIN profesori p ON m.profesor_id = p.id
        """;
        try (PreparedStatement ps = getConn().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare findAll note: " + e.getMessage(), e);
        }
        return list;
    }

    // JOIN 3: toate notele unui student cu detalii despre materie si profesor
    public List<Nota> findByStudent(Student student) {
        List<Nota> list = new ArrayList<>();
        String sql = """
            SELECT n.valoare, n.data,
                   m.nume AS mat_nume, m.credite,
                   p.nume AS prof_nume, p.prenume AS prof_prenume,
                   p.email AS prof_email, p.departament, p.titlu
            FROM note n
            JOIN studenti s ON n.student_id = s.id
            JOIN materii m ON n.materie_id = m.id
            JOIN profesori p ON m.profesor_id = p.id
            WHERE s.email = ?
        """;
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setString(1, student.getEmail());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare findByStudent: " + e.getMessage(), e);
        }
        return list;
    }

    // Studentul cu cea mai mare medie la o materie (JOIN peste 3 tabele)
    public Optional<Student> findTopStudentLaMaterie(String numeMaterie) {
        String sql = """
            SELECT s.*, g.nume AS grupa_nume, sp.nume AS spec_nume, sp.domeniu, sp.durata,
                   AVG(n.valoare) AS medie
            FROM note n
            JOIN studenti s ON n.student_id = s.id
            JOIN grupe g ON s.grupa_id = g.id
            JOIN specializari sp ON g.specializare_id = sp.id
            JOIN materii m ON n.materie_id = m.id
            WHERE m.nume = ?
            GROUP BY s.id
            ORDER BY medie DESC
            LIMIT 1
        """;
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setString(1, numeMaterie);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Specializare spec = new Specializare(
                            rs.getString("spec_nume"),
                            rs.getString("domeniu"),
                            rs.getInt("durata")
                    );
                    Grupa grupa = new Grupa(rs.getString("grupa_nume"), spec);
                    Student student = new Student(
                            rs.getString("nume"),
                            rs.getString("prenume"),
                            rs.getString("email"),
                            grupa,
                            rs.getInt("an")
                    );
                    return Optional.of(student);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare findTopStudentLaMaterie: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public void update(Nota nota) {
        throw new UnsupportedOperationException("Nota este imutabila — update nesuportat.");
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM note WHERE id = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare delete nota: " + e.getMessage(), e);
        }
    }

    private Nota mapRow(ResultSet rs) throws SQLException {
        Profesor profesor = new Profesor(
                rs.getString("prof_nume"),
                rs.getString("prof_prenume"),
                rs.getString("prof_email"),
                rs.getString("departament"),
                rs.getString("titlu")
        );
        Materie materie = new Materie(
                rs.getString("mat_nume"),
                rs.getInt("credite"),
                profesor
        );
        return new Nota(
                rs.getDouble("valoare"),
                LocalDate.parse(rs.getString("data")),
                materie
        );
    }
}