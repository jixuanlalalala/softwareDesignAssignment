package booking.UI;

import booking.controller.*;
import booking.service.*;

import java.time.LocalDate;
import java.util.*;

public class AdminUI {
	
	private IEquipment equipmentController;
	private IReport reportController;
	private ILogin usercontroller;
	private Scanner sc;
	
	public AdminUI() {
		this.equipmentController = new EquipmentController();
		this.reportController = new ReportController(new BookingController());
		this.usercontroller = new UserController();
	}
	
	
	public void showMenu() {
		System.out.println("\nWelcome to Admin Page");
		sc = new Scanner(System.in);
		
		int choice;
		
		do {
			
			System.out.println("================================================================");
			//equipmentController.viewEquipment();
			//System.out.println("================================================================");
			System.out.println("1. View Equipment");
			System.out.println("2. Add New Equipment");
			System.out.println("3. Update Equipment");
			System.out.println("4. Delete Equipment");
			System.out.println("5. Generate Report");
			System.out.println("6. Logout");
			System.out.println("================================================================");
			
			System.out.print("Enter your choice (1-6): ");
			choice = sc.nextInt();
			String skip = sc.nextLine();
			
			while (choice < 1 || choice > 6) {
				System.out.println("Invalid choice.");
				System.out.print("Enter your choice (1-6): ");
				choice = sc.nextInt();
				skip = sc.nextLine();
			}
			
			switch (choice) {
				case 1: viewEquipment();break;
				case 2: addEquipment();break;
				case 3: updateEquipment();break;
				case 4: deleteEquipment();break;
				case 5: generateReport();break;
				case 6:break; //add your method here
			}
			System.out.println();
			
		} while (choice != 6);
	}
	
	public void viewEquipment() {
		System.out.println();
		equipmentController.viewEquipment();
	}

	public void addEquipment() {
		String eName;
		
		while (true) {
			System.out.println("Enter equipment name or E to exit: ");
			eName = sc.nextLine();
			
			if (eName.equalsIgnoreCase("E")) {
				System.out.println("Exiting add equipment...");
				return;
			} else if (eName.isEmpty()) {
				System.out.println("Invalid input.");
				continue;
			} else {
				break;
			}
		}
		
		System.out.println("Enter equipment condition [new/used/broken]: ");
		String eCondition = sc.nextLine();
		while (!eCondition.equals("new") && !eCondition.equals("used") && !eCondition.equals("broken")) {
			System.out.println("Invalid condition");
			System.out.println("Enter equipment condition [new/used/broken]: ");
			eCondition = sc.nextLine();
		}
		
		equipmentController.addEquipment(eName, eCondition);
		System.out.println("Equipment added.");
		
	}
	
	
	public void updateEquipment() {
		
		while (true) {
			System.out.println("Enter equipment ID or E to exit: ");
			String eID = sc.nextLine();
			
			if (eID.equalsIgnoreCase("E")) {
				System.out.println("Exiting update equipment...");
				return;
			} else if (eID.isEmpty()) {
				System.out.println("Invalid input.");
				continue;
			}
			
			if (equipmentController.getEquipmentById(eID) != null) {
				System.out.println("Enter equipment name (Press enter if nothing change): ");
				String eName = sc.nextLine();
				System.out.println("Enter equipment condition [new/used/broken] (Press enter if nothing change): ");
				String eCondition = sc.nextLine();
				while (!eCondition.equals("new") && !eCondition.equals("used") && !eCondition.equals("broken") && !eCondition.isEmpty()) {
					System.out.println("Invalid condition");
					System.out.println("Enter equipment condition [new/used/broken] (Press enter if nothing change): ");
					eCondition = sc.nextLine();
				}
				
				equipmentController.updateEquipment(eID, eName, eCondition);
				System.out.println("Equipment updated.");
	
			}
			else 
				System.out.println("Equipment not found. Please try again.");
		}
		
	}
	
	public void deleteEquipment() {
		while (true) {
			System.out.println("Enter equipment ID or E to exit: ");
			String eID = sc.nextLine();
			
			if (eID.equalsIgnoreCase("E")) {
				System.out.println("Exiting delete equipment...");
				return;
			} else if (eID.isEmpty()) {
				System.out.println("Invalid input.");
				continue;
			}
			
			if (equipmentController.getEquipmentById(eID) != null) {
				equipmentController.deleteEquipment(eID);
				System.out.println("Equipment deleted.");
			}
			else
				System.out.println("Equipment not found. Please try again.");
		}
		
		
		
	}

	public void generateReport() {
        Scanner scanner = new Scanner(System.in);

        // Input: Start date
        System.out.println("Please enter the start date [DD/MM/YYYY]: ");
        String startStrDate = scanner.nextLine();
        LocalDate startDate = this.reportController.toLocalDate(startStrDate);

        while (startDate == null)
        {
            System.out.println("Invalid date format. Please re-enter the start date [DD/MM/YYYY]: ");

            startStrDate = scanner.nextLine();
            startDate = this.reportController.toLocalDate(startStrDate);
        }

        // Input: End date
        System.out.println("Please enter the end date [DD/MM/YYYY]: ");
        String endStrDate = scanner.nextLine();
        LocalDate endDate = this.reportController.toLocalDate(endStrDate);

        while (endDate == null)
        {
            System.out.println("Invalid date format. Please re-enter the end date [DD/MM/YYYY]: ");

            endStrDate = scanner.nextLine();
            endDate = this.reportController.toLocalDate(endStrDate);
        }

		this.reportController.generateReport(startDate, endDate);

        scanner.close();
    }
	
	//this one for testing purpose, will remove after completed
	//must remember remove hahhh
	public static void main(String[] args) {
		AdminUI adminUI = new AdminUI();
		adminUI.showMenu();
	}

	

}