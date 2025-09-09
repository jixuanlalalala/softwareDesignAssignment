package booking.entity;

import java.time.LocalDate;

public class Booking {

private String bookingId;
private String userId;
private String EquipmentId;
private LocalDate startDate;
private LocalDate endDate;
private LocalDate returnDate;
private int updateCounter;




public Booking (String bookingId, String userId, String EquipmentId, LocalDate startDate, LocalDate endDate,LocalDate returnDate, int updateCounter){
    this.bookingId = bookingId;
    this.userId = userId;
    this.EquipmentId = EquipmentId;
    this.startDate = startDate;
    this.endDate = endDate;
    this.returnDate = returnDate;
    this.updateCounter = updateCounter;
}

public String getBookingId() {
    return bookingId;
}


public String getUserId() {
    return userId;
}


public LocalDate getStartDate() {
    return startDate;
}


public LocalDate getEndDate() {
    return endDate;
}


public int getUpdateCounter() {
    return updateCounter;
}


public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
}


public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
}


public void setUpdateCounter(int updateCounter) {
    this.updateCounter = updateCounter;
}

public String getEquipmentId() {
    return EquipmentId;
}

public LocalDate getReturnDate() {
    return returnDate;
}

public void setReturnDate(LocalDate returnDate) {
    this.returnDate = returnDate;
}




}
