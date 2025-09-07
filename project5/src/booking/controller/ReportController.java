package booking.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import booking.entity.*;
import booking.service.*;

public class ReportController implements IReport {
    IBooking bookingService;

    public ReportController(IBooking bookingService)
    {
        this.bookingService = bookingService;
    }

    public void generateReport(LocalDate startDate, LocalDate endDate)
    {
        ArrayList<Booking> bookings = bookingService.getBookings();

        Report report = new Report(generateNewId(), startDate, endDate);
        
        for (Booking booking: bookings)
        {
            // Only include bookings that fall within the date range
            if (booking.getStartDate().isAfter(startDate) && booking.getEndDate().isBefore(endDate))
            {
                report.addEquipment(booking.getEquipmentId());
                report.addBorrower(booking.getUserId());
            }
        }

        // Write report
        System.out.println("Generating report from " + startDate.toString() + " to " + endDate.toString());

        String filePath = report.getReportId() + ".txt";
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))){
            bw.write(report.toString());
            bw.newLine();

            System.out.println("Report generated");
		} catch (IOException e) {
			System.out.println("Error writing file: " + e.getMessage());
		}
    }

    public LocalDate toLocalDate(String strDate)
    {
        // Set up pattern
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        LocalDate localDate;
        
        // Attempt to parse string to date
        try
        {
            localDate = LocalDate.parse(strDate, formatter);
        }
        catch(DateTimeParseException e)
        {
            return null;
        }

        return localDate;
    }

    public String generateNewId() {
		return "R" + System.currentTimeMillis();
	}
}
