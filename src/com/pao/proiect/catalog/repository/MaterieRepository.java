package com.pao.proiect.catalog.repository;

import com.pao.proiect.catalog.model.Materie;
import com.pao.proiect.catalog.model.Profesor;
import com.pao.proiect.catalog.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MaterieRepository implements Repository<Materie, Integer> {

    private Connection getConn() {
        return DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void save(Materie m) {
        String sql = "INSERT INTO materii (nume, credite, profesor_id) VALUES (?, ?, (SELECT id FROM profesori WHERE email = ?))";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setString(1, m.getNume());
            ps.setInt(2, m.getCredite());
            ps.setString(3, m.getProfesor().getEmail());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare save materie: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Materie> findById(Integer id) {
        String sql = """
            SELECT m.*, p.nume AS prof_nume, p.prenume AS prof_prenume,
                   p.email AS prof_email, p.departament, p.titlu
            FROM materii m
            JOIN profesori p ON m.profesor_id = p.id
            WHERE m.id = ?
        """;
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare findById materie: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    public Optional<Materie> findByNume(String nume) {
        String sql = """
            SELECT m.*, p.nume AS prof_nume, p.prenume AS prof_prenume,
                   p.email AS prof_email, p.departament, p.titlu
            FROM materii m
            JOIN profesori p ON m.profesor_id = p.id
            WHERE m.nume = ?
        """;
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setString(1, nume);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare findByNume materie: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<Materie> findAll() {
        List<Materie> list = new ArrayList<>();
        String sql = """
            SELECT m.*, p.nume AS prof_nume, p.prenume AS prof_prenume,
                   p.email AS prof_email, p.departament, p.titlu
            FROM materii m
            JOIN profesori p ON m.profesor_id = p.id
        """;
        try (PreparedStatement ps = getConn().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare findAll materii: " + e.getMessage(), e);
        }
        return list;
    }

    // JOIN 2: materiile predate de un profesor
    public List<Materie> findByProfesor(String emailProfesor) {
        List<Materie> list = new ArrayList<>();
        String sql = """
            SELECT m.*, p.nume AS prof_nume, p.prenume AS prof_prenume,
                   p.email AS prof_email, p.departament, p.titlu
            FROM materii m
            JOIN profesori p ON m.profesor_id = p.id
            WHERE p.email = ?
        """;
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setString(1, emailProfesor);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare findByProfesor: " + e.getMessage(), e);
        }
        return list;
    }

    @Override
    public void update(Materie m) {
        String sql = """
            UPDATE materii SET credite=?,
            profesor_id=(SELECT id FROM profesori WHERE email=?)
            WHERE nume=?
        """;
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setInt(1, m.getCredite());
            ps.setString(2, m.getProfesor().getEmail());
            ps.setString(3, m.getNume());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare update materie: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM materii WHERE id = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare delete materie: " + e.getMessage(), e);
        }
    }

    private Materie mapRow(ResultSet rs) throws SQLException {
        Profesor profesor = new Profesor(
                rs.getString("prof_nume"),
                rs.getString("prof_prenume"),
                rs.getString("prof_email"),
                rs.getString("departament"),
                rs.getString("titlu")
        );
        return new Materie(
                rs.getString("nume"),
                rs.getInt("credite"),
                profesor
        );
    }
}