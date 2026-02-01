package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

public class PasswordUtil {

    // Regex patterns for validation
    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])" +           // at least 1 digit
            "(?=.*[a-z])" +            // at least 1 lowercase letter
            "(?=.*[A-Z])" +            // at least 1 uppercase letter
            "(?=.*[@#$%^&+=])" +       // at least 1 special character
            "(?=\\S+$).{8,20}$";       // no whitespace, length 8-20

    private static final String EMAIL_PATTERN =
            "^[A-Za-z0-9+_.-]+@(.+)$"; // simple email pattern

    // Method to hash a password using SHA-256
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b)); // convert byte to hex
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    // Method to validate password strength
    public static boolean isValidPassword(String password) {
        return Pattern.matches(PASSWORD_PATTERN, password);
    }

    // Method to validate email format
    public static boolean isValidEmail(String email) {
        return Pattern.matches(EMAIL_PATTERN, email);
    }

    // Optional: compare raw password with hashed password
    public static boolean verifyPassword(String rawPassword, String hashedPassword) {
        return hashPassword(rawPassword).equals(hashedPassword);
    }

    // Test main method (optional)
    public static void main(String[] args) {
        String email = "test@example.com";
        String password = "Abc@1234";

        System.out.println("Email valid? " + isValidEmail(email));
        System.out.println("Password valid? " + isValidPassword(password));
        System.out.println("Hashed Password: " + hashPassword(password));
        System.out.println("Verify Password: " + verifyPassword("Abc@1234", hashPassword(password)));
    }
}
