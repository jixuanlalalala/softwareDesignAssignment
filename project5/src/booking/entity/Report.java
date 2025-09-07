package booking.entity;

import java.time.LocalDate;
import java.util.Map;
import java.util.HashMap;

public class Report {
    private String reportId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Map<String, Integer> equipments;
    private Map<String, Integer> borrowers;

    public Report(String reportId, LocalDate startDate, LocalDate endDate)
    {
        this.reportId = reportId;
        this.startDate = startDate;
        this.endDate = endDate;

        this.equipments = new HashMap<>();
        this.borrowers = new HashMap<>();
    }

    // Getters
    public String getReportId()
    {
        return this.reportId;
    }

    public LocalDate getStartDate()
    {
        return this.startDate;
    }

    public LocalDate getEndDate()
    {
        return this.endDate;
    }

    // Setters
    public void setReportId(String reportId)
    {
        this.reportId = reportId;
    }

    public void setStartDate(LocalDate startDate)
    {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate)
    {
        this.endDate = endDate;
    }


    public void addEquipment(String equipmentId)
    {
        equipments.put(equipmentId, equipments.getOrDefault(equipmentId, 0) + 1);
    }

    public void addBorrower(String borrowerId)
    {
        borrowers.put(borrowerId, borrowers.getOrDefault(borrowerId, 0) + 1);
    }

    @Override
    public String toString()
    {
        return "Report ID: " + this.reportId +
            "\nStart Date: " + this.startDate.toString() +
            "\nEnd Date: " + this.endDate.toString() +
            "\nTotal bookings: " + this.equipments.values().stream().mapToInt(Integer::intValue).sum() +
            "\nNumber of Equipments borrowed: " + this.equipments.size() +
            "\nBorrow Frequencies of Equipments: " + this.equipments.toString() +
            "\nNumber of Borrowers: " + this.borrowers.size() +
            "\nBorrow Frequencies of Borrowers: " + this.borrowers.toString();
    }
}
