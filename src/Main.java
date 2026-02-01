import dao.UserDAO;
import model.User;
import util.PasswordUtil;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        UserDAO dao = new UserDAO();

        int choice;

        do {
            System.out.println("\n===== USER LOGIN SYSTEM =====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1: // Registration
                    System.out.print("Name: ");
                    String name = sc.nextLine();

                    System.out.print("Email: ");
                    String email = sc.nextLine();

                    // Validate email
                    if (!PasswordUtil.isValidEmail(email)) {
                        System.out.println("❌ Invalid email format");
                        break;
                    }

                    System.out.print("Password: ");
                    String password = sc.nextLine();

                    // Validate password
                    if (!PasswordUtil.isValidPassword(password)) {
                        System.out.println("❌ Password must be 8-20 chars, include uppercase, lowercase, digit & special char");
                        break;
                    }

                    User user = new User(name, email, password);
                    System.out.println(
                        dao.registerUser(user) ? "✅ Registered successfully" : "❌ Registration failed"
                    );
                    break;

                case 2: // Login
                    System.out.print("Email: ");
                    email = sc.nextLine();

                    if (!PasswordUtil.isValidEmail(email)) {
                        System.out.println("❌ Invalid email format");
                        break;
                    }

                    System.out.print("Password: ");
                    password = sc.nextLine();

                    System.out.println(
                        dao.loginUser(email, password) ? "✅ Login successful" : "❌ Invalid credentials"
                    );
                    break;

                case 0:
                    System.out.println("👋 Exiting... Goodbye!");
                    break;

                default:
                    System.out.println("❌ Invalid choice, try again");
            }

        } while (choice != 0);

        sc.close();
    }
}
