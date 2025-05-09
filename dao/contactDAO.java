package dao;

import db.DBConnection;
import model.contact;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class contactDAO {

    public static void ajouter(contact c) {
        String sql = 
        		"INSERT INTO contact (nom, telephone, email)"
        		+ " VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, c.getNom());
            stmt.setString(2, c.getTelephone());
            stmt.setString(3, c.getEmail());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void supprimer(contact c) {
        String sql = "DELETE FROM contact WHERE nom = ? AND telephone = ? AND email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, c.getNom());
            stmt.setString(2, c.getTelephone());
            stmt.setString(3, c.getEmail());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<contact> getAllContacts() {
        List<contact> contacts = new ArrayList<>();
        String sql = "SELECT * FROM contact";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                contact c = new contact(
                        rs.getString("nom"),
                        rs.getString("telephone"),
                        rs.getString("email")
                );
                contacts.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contacts;
    }
}

