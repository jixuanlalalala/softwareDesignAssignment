package booking.service;

import java.time.LocalDate;
import java.util.ArrayList;

import booking.entity.Booking;
import booking.entity.Borrower;
import booking.entity.Equipment;

public interface IBooking extends IFile {


    public void viewBooking();

    public void createBooking(Equipment anEquipment, Borrower aBorrower);

    public void updateBooking(String bookingId);

    public void returnBooking(String bookingId, LocalDate returnDate);

    public ArrayList<Booking> getBookings();

    public Booking getBookingById(String bookingId);
    
    public String generateNewBookingId();


}
