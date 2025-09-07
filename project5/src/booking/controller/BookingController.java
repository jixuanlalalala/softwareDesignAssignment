package booking.controller;

import booking.entity.Booking;
import booking.service.*;

import java.util.*;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;

public class BookingController implements IBooking{

	private ArrayList<Booking> bookings;
	private IViewEquipment equipmentService;
	
	public BookingController() {
		this.bookings = new ArrayList<Booking>();
		this.equipmentService = new EquipmentController();
		getDataFromFile();
		writeDataToFile();
	}
	
	public BookingController(IViewEquipment equipmentService) {
		this.bookings = new ArrayList<>();
		this.equipmentService = equipmentService; 
		getDataFromFile();
		writeDataToFile();
	}

	@Override
	public void getDataFromFile() {
		try {
			String file = "booking-record.txt";
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			String data = bufferedReader.readLine();

			while(data != null){
				String[] aRecord = data.split(",");
				
				if(aRecord.length == 6){
					bookings.add(new Booking(aRecord[0].trim(),
											 aRecord[1].trim(),
											 aRecord[2].trim(),
											 LocalDate.parse(aRecord[3].trim()),
											 LocalDate.parse(aRecord[4].trim()),
											 Integer.valueOf(aRecord[5].trim())));
					
				}else{
					System.out.println("Invalid booking record!");
				}
				
				data = bufferedReader.readLine();
			}
			bufferedReader.close();
			
		} catch (Exception e) {
			System.err.println("Read booking-record.txt error message:"+e.getMessage());
		}
		
	}

	@Override
	public void writeDataToFile() {
		try {
			String file = "booking-record.txt";
			FileWriter fileWriter = new FileWriter(file);
			BufferedWriter writer = new BufferedWriter(fileWriter);
			
			for (Booking booking : bookings) {
				writer.write(booking.getBookingId() + "," +
						     booking.getUserId() + "," +
						     booking.getEquipmentId() + "," +
						     booking.getStartDate() + "," +
						     booking.getEndDate() + "," +
						     booking.getUpdateCounter());
				writer.newLine();
			}
			
			writer.close();

			System.out.println("Booking data successfully saved to file.");
			
		} catch (Exception e) {
			System.err.println("Write booking-record.txt error message:" + e.getMessage());
		}
	}

	@Override
	public void createBooking() {
		//tbc
		
	}

	@Override
	public void returnBooking() {
	//tbc
		
	}

	@Override
	public void updateBooking() {
		//tbc
		
	}

	@Override
	public void viewBooking() {
		if (bookings.isEmpty()) {
			System.out.println("No bookings found.");
			
		}
		
		System.out.println("Current Booking(s):");
		System.out.println("------------------------------------------------------------");
		System.out.println(String.format("%-10s %-10s %-10s %-12s %-12s %-8s", 
				"Booking ID", "User ID", "Equipment", "Start Date", "End Date", "Updates"));
		
		for (Booking booking : bookings) {
			System.out.println(String.format("%-10s %-10s %-10s %-12s %-12s %-8d", 
					booking.getBookingId(),
					booking.getUserId(),
					booking.getEquipmentId(),
					booking.getStartDate(),
					booking.getEndDate(),
					booking.getUpdateCounter()));
		}
		System.out.println("------------------------------------------------------------");
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

	

}
