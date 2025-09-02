package booking.entity;

public class User {
    private String userId;
    private String name;
    private String phoneNumber;
    private String password;
    private String email;

    public User(String userId, String name, String phoneNumber, String password, String email)
    {
        this.userId = userId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.email = email;
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
}
