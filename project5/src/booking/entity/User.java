package booking.entity;

public class User {
    private String userId;
    private String name;
    private String phoneNumber;
    private String password;
    private String email;
    private String status;
    private String role;   
    private String lastLogin;
    private int failedAttempts;

    public User(String userId, String name, String phoneNumber, String password,
                String email, String status, String role, String lastLogin, int failedAttempts)
    {
        this.userId = userId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.email = email;
        this.status = status;
        this.role = role;
        this.lastLogin = lastLogin;
        this.failedAttempts = failedAttempts;
    }

    public User(String userId, String name, String email, String password, String phoneNumber) {
        this(userId, name, phoneNumber, password, email, "Inactive", "Borrower", "", 0);
    }

    // Getters
    public String getUserId()
    {
        return this.userId;
    }

    public String getName()
    {
        return this.name;
    }

    public String getPhoneNumber()
    {
        return this.phoneNumber;
    }

    public String getPassword()
    {
        return this.password;
    }

    public String getEmail()
    {
        return this.email;
    }

    public String getStatus()
    {
        return this.status;
    }

    public String getRole()
    {
        return this.role;
    }

    public String getLastLogin()
    {
        return this.lastLogin;
    }

    public int getFailedAttempts()
    {
        return this.failedAttempts;
    }

    // Setters
    public void setName(String name)
    {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public void setRole(String role)
    {
        this.role = role;
    }

    public void setLastLogin(String lastLogin)
    {
        this.lastLogin = lastLogin;
    }

    public void setLoginAttempts(int attempts)
    {
        this.failedAttempts = attempts;
    }

    @Override
    public String toString() {
        return userId + "," + name + "," + email + "," + password + "," + phoneNumber + "," 
                + status + "," + role + "," + (lastLogin == null ? "" : lastLogin) + "," + failedAttempts;
    }

    public static User fromString(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length >= 9) {
            int failedAttempts = 0;
            try {
                failedAttempts = Integer.parseInt(parts[8]);
            } catch (NumberFormatException e) {
               
            }
            String lastLogin = parts[7].isEmpty() ? "" : parts[7];

            User user = new User(parts[0], parts[1], parts[2], parts[3],
                            parts[4], parts[5], parts[6], lastLogin, failedAttempts);
            
            if ("Borrower".equalsIgnoreCase(parts[6]) && parts.length == 10) {
                user = new Borrower(parts[0], parts[1], parts[2], parts[3],
                            parts[4], parts[5], parts[6], lastLogin, failedAttempts, Integer.parseInt(parts[9]));
            } else if ("Admin".equalsIgnoreCase(parts[6]) && parts.length == 10) {
                user = new Admin(parts[0], parts[1], parts[2], parts[3],
                            parts[4], parts[5], parts[6], lastLogin, failedAttempts, parts[9]);
            }

            return user;
        }
        return null;
    }
}
