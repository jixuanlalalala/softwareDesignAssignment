package booking.entity;

public class Admin extends User {
    private String department;

    public Admin(String userId, String name, String phoneNumber, String password, String email, String department)
    {
        super(userId, name, phoneNumber, password, email);

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
}
