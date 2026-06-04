package com.pao.proiect.catalog.repository;

import com.pao.proiect.catalog.model.Profesor;
import com.pao.proiect.catalog.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProfesorRepository implements Repository<Profesor, Integer> {

    private Connection getConn() {
        return DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void save(Profesor p) {
        String sql = "INSERT INTO profesori (nume, prenume, email, departament, titlu) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setString(1, p.getNume());
            ps.setString(2, p.getPrenume());
            ps.setString(3, p.getEmail());
            ps.setString(4, p.getDepartament());
            ps.setString(5, p.getTitlu());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare save profesor: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Profesor> findById(Integer id) {
        String sql = "SELECT * FROM profesori WHERE id = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare findById profesor: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    public Optional<Profesor> findByEmail(String email) {
        String sql = "SELECT * FROM profesori WHERE email = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare findByEmail profesor: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<Profesor> findAll() {
        List<Profesor> list = new ArrayList<>();
        String sql = "SELECT * FROM profesori";
        try (PreparedStatement ps = getConn().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare findAll profesori: " + e.getMessage(), e);
        }
        return list;
    }

    @Override
    public void update(Profesor p) {
        String sql = "UPDATE profesori SET nume=?, prenume=?, departament=?, titlu=? WHERE email=?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setString(1, p.getNume());
            ps.setString(2, p.getPrenume());
            ps.setString(3, p.getDepartament());
            ps.setString(4, p.getTitlu());
            ps.setString(5, p.getEmail());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare update profesor: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM profesori WHERE id = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare delete profesor: " + e.getMessage(), e);
        }
    }

    private Profesor mapRow(ResultSet rs) throws SQLException {
        return new Profesor(
                rs.getString("nume"),
                rs.getString("prenume"),
                rs.getString("email"),
                rs.getString("departament"),
                rs.getString("titlu")
        );
    }
}