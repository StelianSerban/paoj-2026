package com.pao.proiect.catalog.repository;

import com.pao.proiect.catalog.model.Grupa;
import com.pao.proiect.catalog.model.Specializare;
import com.pao.proiect.catalog.model.Student;
import com.pao.proiect.catalog.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentRepository implements Repository<Student, Integer> {

    private Connection getConn() {
        return DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void save(Student s) {
        String sqlGrupa = "SELECT id FROM grupe WHERE nume = ?";
        String sqlInsert = "INSERT INTO studenti (nume, prenume, email, an, grupa_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement psGrupa = getConn().prepareStatement(sqlGrupa)) {
            psGrupa.setString(1, s.getGrupa().getNume());
            try (ResultSet rs = psGrupa.executeQuery()) {
                if (!rs.next()) {
                    throw new RuntimeException("Grupa '" + s.getGrupa().getNume() + "' nu exista in baza de date.");
                }
                int grupaId = rs.getInt("id");
                try (PreparedStatement psInsert = getConn().prepareStatement(sqlInsert)) {
                    psInsert.setString(1, s.getNume());
                    psInsert.setString(2, s.getPrenume());
                    psInsert.setString(3, s.getEmail());
                    psInsert.setInt(4, s.getAn());
                    psInsert.setInt(5, grupaId);
                    psInsert.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare save student: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Student> findById(Integer id) {
        String sql = """
            SELECT s.*, g.nume AS grupa_nume, sp.nume AS spec_nume, sp.domeniu, sp.durata
            FROM studenti s
            JOIN grupe g ON s.grupa_id = g.id
            JOIN specializari sp ON g.specializare_id = sp.id
            WHERE s.id = ?
        """;
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare findById student: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    public Optional<Student> findByEmail(String email) {
        String sql = """
            SELECT s.*, g.nume AS grupa_nume, sp.nume AS spec_nume, sp.domeniu, sp.durata
            FROM studenti s
            JOIN grupe g ON s.grupa_id = g.id
            JOIN specializari sp ON g.specializare_id = sp.id
            WHERE s.email = ?
        """;
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare findByEmail student: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    public List<Student> findByNume(String nume) {
        List<Student> list = new ArrayList<>();
        String sql = """
            SELECT s.*, g.nume AS grupa_nume, sp.nume AS spec_nume, sp.domeniu, sp.durata
            FROM studenti s
            JOIN grupe g ON s.grupa_id = g.id
            JOIN specializari sp ON g.specializare_id = sp.id
            WHERE s.nume = ?
        """;
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setString(1, nume);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare findByNume student: " + e.getMessage(), e);
        }
        return list;
    }

    // JOIN 1: toti studentii dintr-o grupa
    public List<Student> findByGrupa(String numeGrupa) {
        List<Student> list = new ArrayList<>();
        String sql = """
            SELECT s.*, g.nume AS grupa_nume, sp.nume AS spec_nume, sp.domeniu, sp.durata
            FROM studenti s
            JOIN grupe g ON s.grupa_id = g.id
            JOIN specializari sp ON g.specializare_id = sp.id
            WHERE g.nume = ?
        """;
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setString(1, numeGrupa);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare findByGrupa: " + e.getMessage(), e);
        }
        return list;
    }

    @Override
    public List<Student> findAll() {
        List<Student> list = new ArrayList<>();
        String sql = """
            SELECT s.*, g.nume AS grupa_nume, sp.nume AS spec_nume, sp.domeniu, sp.durata
            FROM studenti s
            JOIN grupe g ON s.grupa_id = g.id
            JOIN specializari sp ON g.specializare_id = sp.id
        """;
        try (PreparedStatement ps = getConn().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare findAll studenti: " + e.getMessage(), e);
        }
        return list;
    }

    @Override
    public void update(Student s) {
        String sql = """
            UPDATE studenti SET nume=?, prenume=?, an=?,
            grupa_id=(SELECT id FROM grupe WHERE nume=?)
            WHERE email=?
        """;
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setString(1, s.getNume());
            ps.setString(2, s.getPrenume());
            ps.setInt(3, s.getAn());
            ps.setString(4, s.getGrupa().getNume());
            ps.setString(5, s.getEmail());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare update student: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM studenti WHERE id = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare delete student: " + e.getMessage(), e);
        }
    }

    private Student mapRow(ResultSet rs) throws SQLException {
        Specializare spec = new Specializare(
                rs.getString("spec_nume"),
                rs.getString("domeniu"),
                rs.getInt("durata")
        );
        Grupa grupa = new Grupa(rs.getString("grupa_nume"), spec);
        return new Student(
                rs.getString("nume"),
                rs.getString("prenume"),
                rs.getString("email"),
                grupa,
                rs.getInt("an")
        );
    }
}