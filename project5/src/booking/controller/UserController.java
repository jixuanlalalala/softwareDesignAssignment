package booking.controller;

import booking.entity.*;
import booking.service.*;

public class UserController implements IProfile, IRegister, ILogin{

    private User user;

    public UserController() {
        // Dummy user for testing, later this should be overwritten by actual login data via the login method
        this.user = new Borrower("U001", "John Doe", "0123456789", "password123", "john@gmail.com", 5);
    }

    public User getUser() {
        return user;
    }

    public User setUser(User user) {
        return this.user = user;
    }

    @Override
    public boolean editProfile() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'editProfile'");
    }

	@Override
	public void getDataFromFile() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeDataToFile() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void login(String qwerty, String password) {
		// TODO Auto-generated method stub
		
	}
}
