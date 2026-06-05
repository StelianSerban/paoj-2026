package com.pao.proiect.catalog.repository;

import com.pao.proiect.catalog.model.Specializare;
import com.pao.proiect.catalog.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SpecializareRepository implements Repository<Specializare, Integer> {

    private Connection getConn() {
        return DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void save(Specializare s) {
        String sql = "INSERT OR IGNORE INTO specializari (nume, domeniu, durata) VALUES (?, ?, ?)";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setString(1, s.getNume());
            ps.setString(2, s.getDomeniu());
            ps.setInt(3, s.getDurata());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare save specializare: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Specializare> findById(Integer id) {
        String sql = "SELECT * FROM specializari WHERE id = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare findById specializare: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<Specializare> findAll() {
        List<Specializare> list = new ArrayList<>();
        String sql = "SELECT * FROM specializari";
        try (PreparedStatement ps = getConn().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Eroare findAll specializari: " + e.getMessage(), e);
        }
        return list;
    }

    @Override
    public void update(Specializare s) {
        String sql = "UPDATE specializari SET domeniu=?, durata=? WHERE nume=?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setString(1, s.getDomeniu());
            ps.setInt(2, s.getDurata());
            ps.setString(3, s.getNume());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare update specializare: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM specializari WHERE id = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare delete specializare: " + e.getMessage(), e);
        }
    }

    private Specializare mapRow(ResultSet rs) throws SQLException {
        return new Specializare(
                rs.getString("nume"),
                rs.getString("domeniu"),
                rs.getInt("durata")
        );
    }
}