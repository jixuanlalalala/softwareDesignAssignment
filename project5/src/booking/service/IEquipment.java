package booking.service;

import booking.entity.Equipment;

public interface IEquipment extends IViewEquipment, IFile {
	
	public void addEquipment(String name, String condition);
	
	public boolean updateEquipment(String id, String name, String condition);
	
	public boolean deleteEquipment(String id);

}
