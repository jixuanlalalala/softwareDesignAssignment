package booking.UI;

import booking.service.IBooking;
import booking.service.IProfile;
import booking.service.ILogin;
import booking.service.IEquipment;
import booking.entity.Booking;
import booking.entity.Borrower;
import booking.entity.Equipment;

import java.time.LocalDate;
import java.util.Scanner;

public class BorrowerUI {
    private IBooking bookingController;
    private ILogin usercontroller;
    private IProfile profileController;
    private IEquipment equipmentController;
    private Scanner scanner;

    public BorrowerUI(IBooking bookingController, ILogin usercontroller, IProfile profileController) {
        this.bookingController = bookingController;
        this.usercontroller = usercontroller;
        this.profileController = profileController;
    }

    public void showMenu() {
        scanner = new Scanner(System.in);
        int borrowerMainPageOption;

        do {

            System.out.println("Welcome, " + usercontroller.getUser().getName());// GET username here
            System.out.println("=======================================================");
            System.out.println("1. Manage Booking");
            System.out.println("2. Edit Profile");
            System.out.println("3. Logout");
            System.out.println("=======================================================");

            System.out.print("Enter your choice (1, 2, 3) >>");
            borrowerMainPageOption = scanner.nextInt();
            String escape = scanner.nextLine();

            while (borrowerMainPageOption < 1 || borrowerMainPageOption > 3) {
                System.out.println("Invalid choice. Please choose again.");
                System.out.print("Enter your choice (1, 2, 3) >>");
                borrowerMainPageOption = scanner.nextInt();
                escape = scanner.nextLine();
            }

            switch (borrowerMainPageOption) {
                case 1:
                    manageBooking();
                    break;
                case 2:
                    editProfile();
                    break;
                case 3:
                    logout();
                default:
                    break;
            }

        } while (borrowerMainPageOption != 3);

    }

    // Manage Booking Menu
    public void manageBooking() {
        scanner = new Scanner(System.in);
        String bookingServiceOption;

        do {
            System.out.println("Manage Booking Services");
            System.out.println("=======================================================");
            System.out.println("1. Create Booking");
            System.out.println("2. Update Existing Booking");
            System.out.println("3. Return Booked Equipment");
            System.out.println("E. Exit");
            System.out.println("=======================================================");

            System.out.print("Enter your choice (1, 2, 3, E) >>");
            bookingServiceOption = scanner.nextLine();

            while (!bookingServiceOption.equalsIgnoreCase("1") && !bookingServiceOption.equalsIgnoreCase("2")
                    && !bookingServiceOption.equalsIgnoreCase("3") && !bookingServiceOption.equalsIgnoreCase("E")) {
                System.out.println("Invalid choice. Please choose again.");
                System.out.print("Enter your choice (1, 2, 3, E) >>");
                bookingServiceOption = scanner.nextLine();

            }

            switch (bookingServiceOption) {
                case "1": {
                    Borrower aBorrower = (Borrower) usercontroller.getUser();
                    int bookingNo = aBorrower.getCurrentBookingNo();
                    if (bookingNo == 0) {
                        bookingServiceOption = "E";
                        
                        System.out.println("Booking limit is reached, kindly return borrowed equipment first! Thanks.");
                        scanner.next();
                        bookingServiceOption = "E";
                    } else {
                        createBooking();
                    }

                }
                    break;
                case "2":
                    updateBooking();
                    break;
                case "3":
                    returnBooking();
                    break;
                case "E":
                    break;
                default:
                    break;
            }

        } while (!bookingServiceOption.equalsIgnoreCase("E"));

    }

    public void createBooking(){
        equipmentController.viewEquipment();
        System.out.print("Enter Equipment ID or E to exit >>>");
        String equipmentID = scanner.nextLine();
        Equipment anEquipment = equipmentController.getEquipmentById(equipmentID);

        do{

            if(anEquipment == null && !equipmentID.equalsIgnoreCase("E")){
                System.out.println("Invalid choice.");
            } 
            else if(anEquipment == null){
                System.out.println("Equipment not found. ");
            }

        } while (equipmentID.equalsIgnoreCase("E"));

        if(anEquipment!=null){
        
            equipmentController.updateEquipment(anEquipment.getEquipmentId(), anEquipment.getName(), "booked");
            bookingController.createBooking();
            System.out.println("Create booking successfully");
            scanner.nextLine();

        }

    }

    public void updateBooking() {
        bookingController.viewBooking();
        System.out.println("Enter Booking ID to extend >>>");
        String bookingID = scanner.nextLine();

        Booking toUpdate= bookingController.getBookingById(bookingID);

        do{

            if(toUpdate == null && !bookingID.equalsIgnoreCase("E")){
                System.out.println("Invalid choice.");
            } 
            else if(bookingID == null){
                System.out.println("Booking not found. ");
            }

        } while (bookingID.equalsIgnoreCase("E"));

        if(toUpdate!=null){
        
            bookingController.updateBooking(toUpdate.getBookingId());
            System.out.println("Update booking successfully");
            scanner.nextLine();

        }



    }

    public void returnBooking() {
        bookingController.viewBooking();
        System.out.println("Enter Booking ID to return >>>");
        String bookingID = scanner.nextLine();

        Booking toReturn= bookingController.getBookingById(bookingID);

        do{

            if(toReturn == null && !bookingID.equalsIgnoreCase("E")){
                System.out.println("Invalid choice.");
            } 
            else if(bookingID == null){
                System.out.println("Booking not found. ");
            }

        } while (bookingID.equalsIgnoreCase("E"));

        if(toReturn!=null){
            LocalDate returnDate =  LocalDate.now();

            if(returnDate.isAfter(toReturn.getEndDate())){
                System.out.println("Yoo please return the equipment earlier.");
		    }else{

            }
        
            bookingController.returnBooking(toReturn.getBookingId(), returnDate);
            System.out.println("Return booking successfully");
            scanner.nextLine();

        }
        		
		

    }

    // Edit Profile Menu
    public void editProfile() {

    }

    // Logout
    public void logout() {

    }

}
