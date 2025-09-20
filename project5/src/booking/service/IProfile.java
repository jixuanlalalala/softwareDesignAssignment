package booking.service;

import booking.entity.User;

public interface IProfile extends IFile {
    public User getUser();

    public void logout();

    public void updateCurrentBookingNo(String userId, int currentBookingNo);

    public void updateName(String userId, String newName);

    public void updatePhoneNumber(String userId, String newPhoneNumber);

    public void updateEmail(String userId, String newEmail);
}
