package booking.UI;

import booking.controller.*;
import booking.service.*;
import java.util.*;

public class AdminUI {
	
	private IEquipment equipmentController;
	private IReport reportController;
	private ILogin usercontroller;
	private Scanner sc;
	
	public AdminUI() {
		this.equipmentController = new EquipmentController();
		this.reportController = new ReportController();
		this.usercontroller = new UserController();
	}
	
	
	public void showMenu() {
		sc = new Scanner(System.in);
		
		int choice;
		
		do {
			System.out.println("Welcome to Admin Page");
			System.out.println("================================================================");
			equipmentController.viewEquipment();
			System.out.println("================================================================");
			System.out.println("1. Add New Equipment");
			System.out.println("2. Update Equipment");
			System.out.println("3. Delete Equipment");
			System.out.println("4. Generate Report");
			System.out.println("5. Logout");
			
			System.out.print("Enter your choice (1-5): ");
			choice = sc.nextInt();
			String skip = sc.nextLine();
			
			while (choice < 1 || choice > 5) {
				System.out.println("Invalid choice.");
				System.out.print("Enter your choice (1-5): ");
				choice = sc.nextInt();
				skip = sc.nextLine();
			}
			
			switch (choice) {
				case 1: addEquipment();break;
				case 2: updateEquipment();break;
				case 3: deleteEquipment();break;
				case 4:break; //add your method here
			}
			System.out.println();
			
		} while (choice != 5);
	}

	

	public void addEquipment() {
		System.out.println("Enter equipment name: ");
		String eName = sc.nextLine();
		System.out.println("Enter equipment condition [new/used/broken]: ");
		String eCondition = sc.nextLine();
		
		equipmentController.addEquipment(eName, eCondition);
		System.out.println("Equipment added.");
		
	}
	
	
	public void updateEquipment() {
		System.out.println("Enter equipment ID: ");
		String eID = sc.nextLine();
		System.out.println("Enter equipment name: ");
		String eName = sc.nextLine();
		System.out.println("Enter equipment condition [new/used/broken]: ");
		String eCondition = sc.nextLine();
		
		equipmentController.updateEquipment(eID, eName, eCondition);
		System.out.println("Equipment updated.");
		
	}
	
	public void deleteEquipment() {
		System.out.println("Enter equipment ID: ");
		String eID = sc.nextLine();
		
		equipmentController.deleteEquipment(eID);
		System.out.println("Equipment deleted.");
		
	}
	
	//this one for testing purpose, will remove after completed
	//must remember remove hahhh
	public static void main(String[] args) {
		AdminUI adminUI = new AdminUI();
		adminUI.showMenu();
	}

	

}
