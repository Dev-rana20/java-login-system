package dao;

import db.DBConnection;
import model.User;
import util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    // Register user with hashed password
    public boolean registerUser(User user) {
        // Optional: validate email & password before inserting
        if (!PasswordUtil.isValidEmail(user.getEmail())) {
            System.out.println("❌ Invalid email format");
            return false;
        }
        if (!PasswordUtil.isValidPassword(user.getPassword())) {
            System.out.println("❌ Password must be 8-20 chars, include uppercase, lowercase, digit & special char");
            return false;
        }

        String sql = "INSERT INTO users(name, email, password) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, PasswordUtil.hashPassword(user.getPassword())); // HASH the password

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("❌ Registration failed: " + e.getMessage());
        }
        return false;
    }

    // Login user by verifying hashed password
    public boolean loginUser(String email, String password) {
        String sql = "SELECT password FROM users WHERE email=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String storedHashedPassword = rs.getString("password");
                // Verify password using PasswordUtil
                return PasswordUtil.verifyPassword(password, storedHashedPassword);
            } else {
                System.out.println("❌ Email not registered");
            }

        } catch (Exception e) {
            System.out.println("❌ Login failed: " + e.getMessage());
        }
        return false;
    }
}
