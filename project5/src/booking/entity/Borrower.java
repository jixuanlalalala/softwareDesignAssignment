package booking.entity;

public class Borrower extends User {
    private int currentBookingNo;

    public Borrower(String userId, String name, String phoneNumber, String password, String email, int currentBookingNo)
    {
        super(userId, name, phoneNumber, password, email);

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
}
