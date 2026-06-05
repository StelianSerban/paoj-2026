package com.pao.proiect.catalog.repository;

import com.pao.proiect.catalog.model.Grupa;
import com.pao.proiect.catalog.model.Specializare;
import com.pao.proiect.catalog.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GrupaRepository implements Repository<Grupa, Integer> {

    private Connection getConn() {
        return DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void save(Grupa g) {
        String sql = "INSERT OR IGNORE INTO grupe (nume, specializare_id) VALUES (?, (SELECT id FROM specializari WHERE nume = ?))";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setString(1, g.getNume());
            ps.setString(2, g.getSpecializare().getNume());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare save grupa: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Grupa> findById(Integer id) {
        String sql = """
            SELECT g.*, s.nume AS spec_nume, s.domeniu, s.durata
            FROM grupe g
            JOIN specializari s ON g.specializare_id = s.id
            WHERE g.id = ?
        """;
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare findById grupa: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<Grupa> findAll() {
        List<Grupa> list = new ArrayList<>();
        String sql = """
            SELECT g.*, s.nume AS spec_nume, s.domeniu, s.durata
            FROM grupe g
            JOIN specializari s ON g.specializare_id = s.id
        """;
        try (PreparedStatement ps = getConn().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Eroare findAll grupe: " + e.getMessage(), e);
        }
        return list;
    }

    @Override
    public void update(Grupa g) {
        String sql = "UPDATE grupe SET specializare_id=(SELECT id FROM specializari WHERE nume=?) WHERE nume=?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setString(1, g.getSpecializare().getNume());
            ps.setString(2, g.getNume());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare update grupa: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM grupe WHERE id = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare delete grupa: " + e.getMessage(), e);
        }
    }

    private Grupa mapRow(ResultSet rs) throws SQLException {
        Specializare spec = new Specializare(
                rs.getString("spec_nume"),
                rs.getString("domeniu"),
                rs.getInt("durata")
        );
        return new Grupa(rs.getString("nume"), spec);
    }
}