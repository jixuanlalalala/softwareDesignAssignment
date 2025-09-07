package booking.controller;

import booking.entity.Booking;
import booking.service.*;

import java.util.*;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.time.LocalDate;

public class BookingController implements IBooking{

	private ArrayList<Booking> bookings;
	private IViewEquipment equipmentService;

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
		
		
	}

	@Override
	public void createBooking() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void returnBooking() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBooking() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void viewBooking() {
		// TODO Auto-generated method stub
		
	}

	

}
