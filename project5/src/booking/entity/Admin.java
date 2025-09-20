package booking.entity;

public class Admin extends User {
    private String department;

    public Admin(String userId, String name, String phoneNumber, String password,
                String email, String status, String role, String lastLogin, int failedAttempts, String department)
    {
        super(userId, name, phoneNumber, password, email, status, role, lastLogin, failedAttempts);

        this.department = department;
    }

    // Getters
    public String getDepartment()
    {
        return this.department;
    }

    // Setters
    public void setDepartment(String department)
    {
        this.department = department;
    }

    @Override
    public String toString() {
        return super.toString() + "," + department;
    }
}
