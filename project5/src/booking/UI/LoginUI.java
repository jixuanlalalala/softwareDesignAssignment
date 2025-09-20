package booking.UI;

import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;
import java.util.regex.Pattern;

import booking.controller.BookingController;
import booking.controller.EquipmentController;
import booking.controller.ReportController;
import booking.controller.UserController;
import booking.entity.Borrower;
import booking.entity.User;
import booking.service.ILogin;
import booking.service.IProfile;

public class LoginUI {

    private final int MIN_PASSWORD_LENGTH = 6;
    private final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$");
    private Random random = new Random();

    private Scanner sc;
    private ILogin usercontroller;

    public LoginUI(ILogin usercontroller) {
		this.usercontroller = usercontroller;
	}

	public LoginUI() {
        this.usercontroller = new UserController();
	}

    public void startApplication() {
        sc = new Scanner(System.in);

        while (true) {
            System.out.println("===================== LOGIN MENU =======================");
            System.out.println("1. Login");
            System.out.println("2. Sign Up");
            System.out.println("3. Forgot Password");
            System.out.println("4. Forgot ID");
            System.out.println("5. Exit");
            System.out.print("Select option: ");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    login();
                    break;
                case "2":
                    signUp();
                    break;
                case "3":
                    forgotPasswordProcess();
                    break;
                case "4":
                    forgotIdProcess();
                    break;
                case "5":
                    System.out.println("Exiting system. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void login() {
        System.out.print("Enter User ID: ");
        String userId = sc.nextLine().trim();
        System.out.print("Enter Password: ");
        String password = sc.nextLine().trim();

        if (!isValidUserId(userId) || !isValidPassword(password)) {
            System.out.println("Invalid User ID or Password format.");
            return;
        }

        User user = usercontroller.findUserById(userId);
        if (user == null) {
            System.out.println("Invalid User ID or Password.");
            return;
        }
        if ("Locked".equalsIgnoreCase(user.getStatus())) {
            System.out.println("Account is locked due to too many failed login attempts. Please contact admin.");
            return;
        }
        if (!user.getPassword().equals(password)) {
            int attempts = user.getFailedAttempts() + 1;
            usercontroller.updateFailedAttempts(userId, attempts);
            if (attempts >= 5) {
                usercontroller.updateStatus(userId, "Locked");
                System.out.println("Account locked after 5 failed attempts.");
            } else {
                System.out.println("Invalid User ID or Password. Attempt " + attempts + " of 5.");
            }
            return;
        }
        if (!"Active".equalsIgnoreCase(user.getStatus())) {
            System.out.println("Account is not active. Please verify your account.");
            return;
        }

        usercontroller.setUser(user);
        usercontroller.updateFailedAttempts(userId, 0);
        String lastLogin = user.getLastLogin();
        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        usercontroller.updateLastLogin(userId, now);

        System.out.println("\nWelcome, " + userId + "!");
        System.out.println("Last login: " + (lastLogin == null || lastLogin.isEmpty() ? "First time login" : lastLogin));
        System.out.println("Terms and Conditions: ...");

        if ("Admin".equalsIgnoreCase(user.getRole())) {
            System.out.println("Redirecting to Administration Interface...");
            
            AdminUI adminUI = new AdminUI(new EquipmentController(), new ReportController(new BookingController()), usercontroller);
            adminUI.showMenu();
        } else {
            System.out.println("Redirecting to Borrowing Interface...");

            BorrowerUI borrowerUI = new BorrowerUI(new BookingController(), (IProfile)usercontroller, new EquipmentController());
            borrowerUI.showMenu();
        }
    }

    private void signUp() {
        User user = collectUserInformation();
        if (user == null) {
            System.out.println("Registration cancelled.");
            return;
        }

        if (!confirmUserInformation(user)) {
            System.out.println("Registration cancelled.");
            return;
        }

        System.out.println("\n✓ Inactive borrower account created successfully!");
        System.out.println("User ID: " + user.getUserId());

        String verificationCode = generateVerificationCode();
        System.out.println("\n=== ACCOUNT VERIFICATION ===");
        System.out.println("Verification Code: " + verificationCode);

        if (verifyCode(verificationCode)) {
            user.setStatus("Active");
            System.out.println("\n✓ Account activated successfully!");
            System.out.println("✓ System access rights granted!");
            System.out.println("✓ You can now log in to the system.");
        } else {
            System.out.println("\nAccount verification failed. Account remains inactive.");
            System.out.println("Please contact system administrator for assistance.");
        }
    }

    private User collectUserInformation() {
        System.out.println("Please complete the registration form:\n");
        String name = getValidInput("Full Name: ", this::isValidName);
        if (name == null) return null;
        String email = getValidInput("Email Address: ", this::isValidEmail);
        if (email == null) return null;
        if (usercontroller.emailExists(email)) {
            System.out.println("✗ Email address already registered. Please use a different email.");
            return null;
        }
        String password = getValidInput("Password (minimum " + MIN_PASSWORD_LENGTH + " characters): ", this::isValidPassword);
        if (password == null) return null;
        String contactInfo = getValidInput("Contact Information (Phone Number): ", this::isValidContact);
        if (contactInfo == null) return null;
        String userId = generateUserId();

        User user = usercontroller.signUp(userId, name, email, password, contactInfo, "Inactive", "Borrower", "", 0);

        return user;
    }

    private String getValidInput(String prompt, InputValidator validator) {
        String input;
        int attempts = 0;
        final int MAX_ATTEMPTS = 3;
        while (attempts < MAX_ATTEMPTS) {
            System.out.print(prompt);
            input = sc.nextLine().trim();
            if (input.equalsIgnoreCase("cancel")) {
                return null;
            }
            if (validator.isValid(input)) {
                return input;
            }
            attempts++;
            if (attempts < MAX_ATTEMPTS) {
                System.out.println("Invalid input. Please try again. (Type 'cancel' to exit)");
            }
        }
        System.out.println("Maximum attempts exceeded.");
        return null;
    }

    private boolean isValidName(String name) {
        if (name.isEmpty()) {
            System.out.println("✗ Name cannot be empty.");
            return false;
        }
        if (name.length() < 2) {
            System.out.println("✗ Name must be at least 2 characters long.");
            return false;
        }
        return true;
    }

    private boolean isValidEmail(String email) {
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            System.out.println("✗ Invalid email format. Please enter a valid email address.");
            return false;
        }
        return true;
    }

    private boolean isValidPassword(String password) {
        if (password.length() < MIN_PASSWORD_LENGTH) {
            System.out.println("✗ Password must be at least " + MIN_PASSWORD_LENGTH + " characters long.");
            return false;
        }
        return true;
    }

    private boolean isValidContact(String contact) {
        if (contact.isEmpty()) {
            System.out.println("✗ Contact information cannot be empty.");
            return false;
        }
        if (!contact.matches("\\d{8,15}")) {
            System.out.println("✗ Please enter a valid phone number (8-15 digits).");
            return false;
        }
        return true;
    }

    private boolean confirmUserInformation(User user) {
        System.out.println("\n=== CONFIRM YOUR INFORMATION ===");
        System.out.println("User ID: " + user.getUserId());
        System.out.println("Name: " + user.getName());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Password: " + new String(new char[user.getPassword().length()]).replace("\0", "*"));
        System.out.println("Contact: " + user.getPhoneNumber());
        System.out.print("\nIs this information correct? (y/n): ");
        String confirmation = sc.nextLine().trim().toLowerCase();
        return confirmation.equals("y") || confirmation.equals("yes");
    }

    private String generateUserId() {
        String prefix = "U";
        int number = random.nextInt(900) + 100;
        return prefix + number;
    }

    private String generateVerificationCode() {
        return String.format("%06d", random.nextInt(1000000));
    }

    private boolean verifyCode(String correctCode) {
        int attempts = 0;
        final int MAX_ATTEMPTS = 3;
        while (attempts < MAX_ATTEMPTS) {
            System.out.print("Please enter the verification code: ");
            String enteredCode = sc.nextLine().trim();
            if (enteredCode.equals(correctCode)) {
                System.out.println("✓ Verification code correct!");
                return true;
            }
            attempts++;
            if (attempts < MAX_ATTEMPTS) {
                System.out.println("✗ Incorrect verification code. " + (MAX_ATTEMPTS - attempts) + " attempts remaining.");
            }
        }
        System.out.println("✗ Maximum verification attempts exceeded.");
        return false;
    }

    private void forgotPasswordProcess() {
        System.out.print("Enter your registered Email: ");
        String email = sc.nextLine().trim();
        User user = usercontroller.findUserByEmail(email);
        if (user == null) {
            System.out.println("Email not found. Please try again.");
            return;
        }
        String code = String.format("%06d", new Random().nextInt(1000000));
        System.out.println("Verification Code: " + code);
        System.out.print("Enter the verification code: ");
        String inputCode = sc.nextLine().trim();
        if (!code.equals(inputCode)) {
            System.out.println("Incorrect verification code.");
            return;
        }
        System.out.print("Enter new password (min 6 chars): ");
        String newPassword = sc.nextLine().trim();
        if (!isValidPassword(newPassword)) {
            System.out.println("Password format invalid.");
            return;
        }
        usercontroller.updatePassword(user.getUserId(), newPassword);
        System.out.println("Password reset successful. Please login again.");
    }

    private void forgotIdProcess() {
        System.out.print("Enter your registered Email: ");
        String email = sc.nextLine().trim();
        User user = usercontroller.findUserByEmail(email);
        if (user == null) {
            System.out.println("Email not found. Please try again.");
            return;
        }
        System.out.println("Your User ID is: " + user.getUserId());
        System.out.println("Please login again.");
    }

    private boolean isValidUserId(String userId) {
        return userId.matches("U\\d{3}");
    }

    public static void main(String[] args) {
        LoginUI app = new LoginUI();

        app.startApplication();
    }
}

@FunctionalInterface
interface InputValidator {
    boolean isValid(String input);
}