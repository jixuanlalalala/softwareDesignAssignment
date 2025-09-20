package booking.controller;

import booking.entity.Equipment;
import booking.service.*;
import java.util.*;
import java.io.*;

public class EquipmentController implements IEquipment {
	
	private ArrayList<Equipment> equipments;
	
	public EquipmentController() {
        this.equipments = new ArrayList<>();
        getDataFromFile();
    }


	public EquipmentController(ArrayList<Equipment> equipments) {
		super();
		this.equipments = equipments;
	}
	
	@Override
	public void getDataFromFile() {
		String filePath = "equipment.txt";
		equipments.clear();

		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;

			while ((line = br.readLine()) != null) {
				String[] data = line.split(",");
				if (data.length == 4) {
					Equipment eq = new Equipment(data[0].trim(), data[1].trim(), data[2].trim(), data[3].trim());
					equipments.add(eq);
				} else {
					System.out.println("Skipped invalid lines");
				}
			}
		} catch (IOException e) {
			System.out.println("Error reading file: " + e.getMessage());
		}
	}
	
	@Override
	public void writeDataToFile() {
		String filePath = "equipment.txt";
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))){
			for (Equipment eq : equipments) {
				bw.write(eq.getEquipmentId() + "," + eq.getName() + "," + eq.getCondition() + "," + eq.getStatus());
				bw.newLine();
			}
		} catch (IOException e) {
			System.out.println("Error writing file: " + e.getMessage());
		}
	}

	@Override
	public void viewEquipment() {
		if (equipments.isEmpty())
			System.out.println("No equipment found.");
		else {
			System.out.println(String.format("%-13s %-21s %-13s %-15s", 
	                "Equipment Id", "Name", "Condition", "Status"));
	        for (Equipment eq : equipments) {
	            System.out.println(eq);
	        }
		}
	}

	@Override
	public Equipment getEquipmentById(String equipmentId) {
		Equipment found = null;
		for (Equipment e: equipments) {
			if (e.getEquipmentId().equals(equipmentId)) {
				found = e;
			}
		}
		return found;
	}

	@Override
	public void addEquipment(String name, String condition) {
		String newId = generateNewEquipmentId();
		
		Equipment eq = new Equipment(newId, name, condition, "available");
		equipments.add(eq);
		writeDataToFile();
		
	}
	
	// Automatic generate Equipment ID for new equipment
	public String generateNewEquipmentId() {
		int maxId = 0;
		
		for (Equipment e: equipments) {
			String currentId = e.getEquipmentId();
			try {
				int num = Integer.parseInt(currentId.replaceAll("[^0-9]", ""));
				if (num > maxId) {
					maxId = num;
				}
			} catch (NumberFormatException ex) {
				
			}
		}
		
		int nextId = maxId + 1;
		return String.format("E%03d", nextId);
	}

	// Only if got any changes on the details, the setter will be trigger
	// Else, the equipment details won't be change
	@Override
	public void updateEquipment(String id, String name, String condition, String status) {
		Equipment e = getEquipmentById(id);
		if (!name.isEmpty()) {
			e.setName(name);
		}
				
		if (!condition.isEmpty()) {
			e.setCondition(condition);
		}

		if (!status.isEmpty()) {
			e.setStatus(status);
		}


				
		System.out.println(e.toString());
		writeDataToFile();
	}

	@Override
	public void deleteEquipment(String id) {
		Equipment e = getEquipmentById(id);
		equipments.remove(e);
		writeDataToFile();
	}

}
