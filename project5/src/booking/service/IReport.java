package booking.service;

import java.time.LocalDate;

public interface IReport {
    void generateReport(LocalDate startDate, LocalDate endDate);
    LocalDate toLocalDate(String strDate);
}
