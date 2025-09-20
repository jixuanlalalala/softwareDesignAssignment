package booking.UI;

import booking.service.IBooking;
import booking.service.IProfile;
import booking.service.ILogin;
import booking.service.IEquipment;
import booking.controller.BookingController;
import booking.controller.UserController;
import booking.controller.EquipmentController;
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

    public BorrowerUI(IBooking bookingController, ILogin usercontroller, IEquipment equipmentController) {
        this.bookingController = bookingController;
        this.usercontroller =  usercontroller;
        this.equipmentController =  equipmentController;
    }
    

    public BorrowerUI() {
        this.bookingController = new BookingController();
        this.usercontroller =  new UserController();
        this.equipmentController =  new EquipmentController();
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
        String equipmentID;
        Equipment anEquipment;
        Borrower aBorrower = (Borrower) usercontroller.getUser();
        
        // while(anEquipment == null && !equipmentID.equalsIgnoreCase("E")){
        //      System.out.println("Invalid choice.");
        //      equipmentID = scanner.nextLine();
        // }
              //&& !equipmentID.equalsIgnoreCase("E")


    do{
        System.out.print("Enter Equipment ID or E to exit >>>");
        equipmentID = scanner.nextLine();
        anEquipment = equipmentController.getEquipmentById(equipmentID);
        

        if (equipmentID.equalsIgnoreCase("E")) {
            break;
        } else if ((anEquipment == null && !equipmentID.equalsIgnoreCase("E"))) {
            System.out.println("Invalid choice.");
        }
        
        else{
            String status = anEquipment.getStatus();

            if(status.equals("booked"))
                System.out.println("Equipment booked. ");

            else{
                 bookingController.createBooking(anEquipment, aBorrower);
            equipmentController.updateEquipment(anEquipment.getEquipmentId(), anEquipment.getName(), anEquipment.getCondition(), "booked");
            usercontroller.updateCurrentBookingNo(aBorrower.getUserId(), aBorrower.getCurrentBookingNo() - 1);
            System.out.println("Create booking successfully");
            scanner.nextLine();
            break;

            }

        }
           
        
        }while (true);
    } 



    public void updateBooking() {
        bookingController.viewBooking();
        
        
        do{
        System.out.println("Enter Booking ID to extend or E to Exit>>>");
        String bookingID = scanner.nextLine();

        Booking toUpdate= bookingController.getBookingById(bookingID);

        if (bookingID.equalsIgnoreCase("E")) {
            break;
        } else if ((toUpdate == null && !bookingID.equalsIgnoreCase("E"))) {
            System.out.println("Invalid choice.");

        }else if(toUpdate.getUpdateCounter() == 0){
            System.out.println("Update reached limit. Please return the equipment. Thanks");
        }
        else{
            bookingController.updateBooking(toUpdate.getBookingId());
            System.out.println("Update booking successfully");
            scanner.nextLine();
            break;
        }
        }while (true);
        

      



    }

    public void returnBooking() {
        bookingController.viewBooking();
        String equipmentID;
        Equipment anEquipment;

        do{
            System.out.println("Enter Booking ID to return or E to Exit>>>");
            String bookingID = scanner.nextLine();

            Booking toReturn = bookingController.getBookingById(bookingID);
            //System.out.println(toReturn);
        

            if (bookingID.equalsIgnoreCase("E")) {
                break;
            }
            if ((toReturn == null && !bookingID.equalsIgnoreCase("E"))) {
                System.out.println("Invalid choice.");

            }

            else{
                equipmentID = toReturn.getEquipmentId();
                anEquipment = equipmentController.getEquipmentById(equipmentID);
                LocalDate returnDate =  LocalDate.now();

                if (returnDate.isAfter(toReturn.getEndDate())) {
                    System.out.println("Yoo please return the equipment earlier.");
                    bookingController.returnBooking(toReturn.getBookingId(), returnDate);
                } else {
                    bookingController.returnBooking(toReturn.getBookingId(), returnDate);
                    System.out.println("Return booking successfully");
                    scanner.nextLine();

                }
                equipmentController.updateEquipment(anEquipment.getEquipmentId(), anEquipment.getName(),
                        anEquipment.getCondition(), "available");
                break;
           
            
                }
            }while (true);
       
        
        

    }

    // Edit Profile Menu
    public void editProfile() {

    }

    // Logout
    public void logout() {
        usercontroller.setUser(null);
    }

    public static void main(String[] args) {
        BorrowerUI bw = new BorrowerUI();
        bw.showMenu();
    }
}
