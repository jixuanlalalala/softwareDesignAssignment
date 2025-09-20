package booking.service;
import booking.entity.User;

public interface ILogin extends IFile {
    public void login(String qwerty, String password);

    public User setUser(User user);

    public User getUser();

    public void addUser(User user);

    public User findUserById(String userId);

    public User findUserByEmail(String email);

    public boolean emailExists(String email);

    public void updateFailedAttempts(String userId, int attempts);

    public void updateStatus(String userId, String status);

    public void updateLastLogin(String userId, String lastLogin);

    public void updatePassword(String userId, String newPassword);

    public void updateCurrentBookingNo(String userId, int currentBookingNo);
}
