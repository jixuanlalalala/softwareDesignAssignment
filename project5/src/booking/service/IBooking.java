package booking.service;

import java.time.LocalDate;
import java.util.ArrayList;

import booking.entity.Booking;

public interface IBooking extends IFile {


    public void viewBooking();

    public void createBooking();

    public void updateBooking(String bookingId);

    public void returnBooking(String bookingId, LocalDate returnDate);

    public ArrayList<Booking> getBookings();

    public Booking getBookingById(String bookingId);
    
    public String generateNewBookingId();


}
