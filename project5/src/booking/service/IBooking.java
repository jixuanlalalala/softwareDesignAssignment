package booking.service;

import java.util.ArrayList;

import booking.entity.Booking;

public interface IBooking extends IFile {

    public void viewBooking();

    public void createBooking();

    public void updateBooking();

    public void returnBooking();

    public ArrayList<Booking> getBookings();


}
