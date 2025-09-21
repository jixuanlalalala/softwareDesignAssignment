package booking.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import booking.entity.Admin;
import booking.entity.Borrower;
import booking.entity.User;
import booking.service.ILogin;
import booking.service.IProfile;

public class UserController implements IProfile, ILogin{

    private List<User> users;
    private User user;

    public UserController() {
        this.users = new ArrayList<>();
        getDataFromFile();

        // Dummy user for testing, later this should be overwritten by actual login data via the login method
        // this.user = new Borrower("U001", "John Doe", "0123456789", "password123", "john@gmail.com", 5);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

	@Override
	public void login(String qwerty, String password) {
		// TODO Auto-generated method stub
		
	}

    @Override
    public void logout() {
        this.user = null;
    }

    @Override
    public void getDataFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                User user = User.fromString(line);
                if (user != null) {
                    users.add(user);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Database file not found. Creating new database.");
        } catch (IOException e) {
            System.out.println("Error reading database file: " + e.getMessage());
        }
    }
    
    @Override
    public void writeDataToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("users.txt"))) {
            for (User user : users) {
                if (user instanceof Borrower) {
                    pw.println(((Borrower) user).toString());
                } else if (user instanceof Admin) {
                    pw.println(((Admin) user).toString());
                } else {
                    pw.println(user.toString());
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving to database: " + e.getMessage());
        }
    }
    
    public User signUp(String userId, String name, String email, String password,
                String phoneNumber, String status, String role, String lastLogin, int failedAttempts) {
        User user = new Borrower(userId, name, email, password, phoneNumber, status, role, lastLogin, failedAttempts, 5);
        
        if (users.add(user)) {
            writeDataToFile();

            return user;
        }

        return null;
    }
    
    public User findUserById(String userId) {
        return users.stream()
                   .filter(u -> u.getUserId().equals(userId))
                   .findFirst()
                   .orElse(null);
    }
    
    public User findUserByEmail(String email) {
        return users.stream()
                   .filter(u -> u.getEmail().equalsIgnoreCase(email))
                   .findFirst()
                   .orElse(null);
    }
    
    public boolean emailExists(String email) {
        return users.stream()
                   .anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
    }
    
    public void updateUser(User user) {

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId().equals(user.getUserId())) {
                users.set(i, user);
                writeDataToFile();
            }
        }
    }
    
    public void updateFailedAttempts(String userId, int attempts) {
        User user = findUserById(userId);
        if (user != null) {
            user.setLoginAttempts(attempts);
            updateUser(user);
        }
    }

    public void updateStatus(String userId, String status) {
        User user = findUserById(userId);
        if (user != null) {
            user.setStatus(status);
            updateUser(user);
        }
    }

    public void updateLastLogin(String userId, String lastLogin) {
        User user = findUserById(userId);
        if (user != null) {
            user.setLastLogin(lastLogin);
            updateUser(user);
        }
    }

    public void updatePassword(String userId, String newPassword) {
        User user = findUserById(userId);
        if (user != null) {
            user.setPassword(newPassword);
            updateUser(user);
        }
    }

    public void updateCurrentBookingNo(String userId, int newBookingNo) {
        User user = findUserById(userId);
        if (user != null && user instanceof Borrower) {
            ((Borrower) user).setCurrentBookingNo(newBookingNo);
            updateUser(user);
        }
    }

    public void updateName(String userId, String newName) {
        User user = findUserById(userId);
        if (user != null) {
            user.setName(newName);
            updateUser(user);
        }
    }

    public void updatePhoneNumber(String userId, String newPhoneNumber) {
        User user = findUserById(userId);
        if (user != null) {
            user.setPhoneNumber(newPhoneNumber);
            updateUser(user);
        }
    }

    public void updateEmail(String userId, String newEmail) {
        User user = findUserById(userId);
        if (user != null) {
            user.setEmail(newEmail);
            updateUser(user);
        }
    }
}
