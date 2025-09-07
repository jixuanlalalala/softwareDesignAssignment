package booking.UI;

import booking.controller.BookingController;
import booking.service.IBooking;
import booking.service.IProfile;
import booking.service.ILogin;
import java.util.Scanner;

public class BorrowerUI {
    private IBooking bookingController;
    private ILogin usercontroller;
    private IProfile profileController;
    private Scanner scanner;

    public BorrowerUI(IBooking bookingController, ILogin usercontroller, IProfile profileController) {
        this.bookingController = bookingController;
        this.usercontroller = usercontroller;
        this.profileController = profileController;
    }

    public void showMenu(){
        scanner = new Scanner(System.in);
        int borrowerMainPageOption;
          
        do {

            System.out.println("Welcome, ");//GET username here
            System.out.println("=======================================================");
            System.out.println("1. Manage Booking");
            System.out.println("2. Edit Profile");
            System.out.println("3. Logout");
            System.out.println("=======================================================");
            

            
            System.out.print("Enter your choice (1, 2, 3) >>");
            borrowerMainPageOption = scanner.nextInt();
            String escape = scanner.nextLine();

            while(borrowerMainPageOption<1 || borrowerMainPageOption>3){
                System.out.println("Invalid choice. Please choose again.");
                System.out.print("Enter your choice (1, 2, 3) >>");
                borrowerMainPageOption = scanner.nextInt();
                escape = scanner.nextLine();
            }
            
           

            switch (borrowerMainPageOption) {
                case 1:manageBooking();
                break;
                case 2:editProfile();
                break;
                case 3:logout();
                default:
                break;
            }
          
            
        } while (borrowerMainPageOption != 3);

    }

    // Manage Booking Menu
    public void manageBooking(){
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

            while(!bookingServiceOption.equals("1") && !bookingServiceOption.equals("2")
            && !bookingServiceOption.equals("3") && !bookingServiceOption.equals("E")){
                System.out.println("Invalid choice. Please choose again.");
                System.out.print("Enter your choice (1, 2, 3, E) >>");
                bookingServiceOption = scanner.nextLine();
 
            }
        
            switch (bookingServiceOption) {
                case "1":createBooking();
                break;
                case "2":updateBooking();
                break;
                case "3":returnBooking();
                break;
                case "E": 
                break;
                default:
                break;
            }
            
        } while ( !bookingServiceOption.equals("E"));


    }
    public void createBooking(){
        bookingController.viewBooking();
        //tbc


    }

    public void updateBooking(){

    }

    public void returnBooking(){

    }


    // Edit Profile Menu
    public void editProfile(){

    }

    //Logout
    public void logout(){

    }

    
   


}
