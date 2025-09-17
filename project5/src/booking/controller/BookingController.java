package booking.controller;

import booking.entity.Booking;
import booking.entity.Borrower;
import booking.entity.Equipment;
import booking.service.*;

import java.util.*;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;


public class BookingController implements IBooking{

	private ArrayList<Booking> bookings;
	public IEquipment equipmentService;
	
	public BookingController() {
		this.bookings = new ArrayList<Booking>();
		this.equipmentService = new EquipmentController();
		getDataFromFile();
		//writeDataToFile();
	}
	
	

	@Override
	public void getDataFromFile() {
	String file = "booking-record.txt";
	bookings.clear();
		try (BufferedReader bufferedReader = new BufferedReader( new FileReader(file))){
	
			String data;

			while((data = bufferedReader.readLine()) != null){
				String[] aRecord = data.split(",");
				
				if(aRecord.length == 7){
					bookings.add(new Booking(aRecord[0].trim(),
											 aRecord[1].trim(),
											 aRecord[2].trim(),
											 LocalDate.parse(aRecord[3].trim()),
											 LocalDate.parse(aRecord[4].trim()),
											 (aRecord[5].trim().equals("")? null : LocalDate.parse(aRecord[5].trim())),
											 Integer.valueOf(aRecord[6].trim())));
					
				}else{
					System.out.println("Invalid booking record!");
				}
				
				//data = bufferedReader.readLine();
			}
			//bufferedReader.close();
			
		} catch (Exception e) {
			System.err.println("Read booking-record.txt error message:"+e.getMessage());
		}
		
	}

	@Override
	public void writeDataToFile() {
		String file = "booking-record.txt";
		try (BufferedWriter writer = new BufferedWriter( new FileWriter(file))){
			
			
			for (Booking booking : bookings) {
				writer.write(booking.getBookingId() + "," +
						     booking.getUserId() + "," +
						     booking.getEquipmentId() + "," +
						     booking.getStartDate() + "," +
						     booking.getEndDate() + "," +
							 (booking.getReturnDate() == null ? "" : booking.getReturnDate()) + "," +
						     booking.getUpdateCounter());
				writer.newLine();
			}
			
			//writer.close();

			//System.out.println("Booking data successfully saved to file.");
			
		} catch (Exception e) {
			System.err.println("Write booking-record.txt error message:" + e.getMessage());
		}
	}

	@Override
	public void createBooking(Equipment anEquipment, Borrower aBorrower) {
		String id = generateNewBookingId();
		LocalDate currentDate = LocalDate.now();
		LocalDate endDate = currentDate.plusWeeks(1);
		Booking newBooking = new Booking(id,aBorrower.getUserId(),anEquipment.getEquipmentId(),currentDate, endDate,null, 2);
		bookings.add(newBooking);
		writeDataToFile();
		
	}

	@Override
	public void returnBooking(String bookingId, LocalDate returnDate) {
		Booking toReturn = getBookingById(bookingId);
		toReturn.setReturnDate(returnDate);
		writeDataToFile();
	}

	@Override
	public void updateBooking(String bookingId) {
		Booking update = getBookingById(bookingId);
		update.setEndDate(update.getEndDate().plusWeeks(1));
		update.setUpdateCounter(update.getUpdateCounter()-1);
		writeDataToFile();
	}

	@Override
	public void viewBooking() {
		if (bookings.isEmpty()) {
			System.out.println("No bookings found.");
			
		}
		else{
			System.out.println("Current Booking(s):");
		System.out.println("------------------------------------------------------------");
		System.out.println(String.format("%-10s %-10s %-10s %-12s %-12s %-12s %-8s", 
				"Booking ID", "User ID", "Equipment", "Start Date", "End Date","Return Date", "Updates"));
		
		for (Booking booking : bookings) {
			System.out.println(String.format("%-10s %-10s %-10s %-12s %-12s %-12s %-8d", 
					booking.getBookingId(),
					booking.getUserId(),
					booking.getEquipmentId(),
					booking.getStartDate(),
					booking.getEndDate(),
					booking.getReturnDate(),
					booking.getUpdateCounter()));
		}
		System.out.println("------------------------------------------------------------");

		}
		
	}

	@Override
	public ArrayList<Booking> getBookings(){
	
		if (bookings.isEmpty()) {
			System.out.println("No bookings found.");
			
			return new ArrayList<>();
		}
		else {
			return new ArrayList<>(bookings);
		}
	}

	@Override 
	public Booking getBookingById(String bookingId){
		for(Booking b:bookings){
			if(b.getBookingId().equals(bookingId)){
				return b;
			}
		}
		return null;

	}

	@Override
	public String generateNewBookingId() {
		int maxId = 0;
		
		for (Booking b: bookings) {
			String currentId = b.getBookingId();
			try {
				int num = Integer.parseInt(currentId);
				if (num > maxId) {
					maxId = num;
				}
			} catch (NumberFormatException ex) {
				
			}
		}
		
		return String.valueOf(maxId + 1);
	}

	

	

}
