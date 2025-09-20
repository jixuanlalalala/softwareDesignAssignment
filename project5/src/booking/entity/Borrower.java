package booking.entity;

public class Borrower extends User {
    private int currentBookingNo;

    public Borrower(String userId, String name, String phoneNumber, String password,
                String email, String status, String role, String lastLogin, int failedAttempts, int currentBookingNo)
    {
        super(userId, name, phoneNumber, password, email, status, role, lastLogin, failedAttempts);

        this.currentBookingNo = currentBookingNo;
    }

    // Getters
    public int getCurrentBookingNo()
    {
        return this.currentBookingNo;
    }

    // Setters
    public void setCurrentBookingNo(int currentBookingNo)
    {
        this.currentBookingNo = currentBookingNo;
    }

    @Override
    public String toString() {
        return super.toString() + "," + currentBookingNo;
    }
}
