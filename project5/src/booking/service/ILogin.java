package booking.service;
import booking.entity.User;

public interface ILogin extends IFile {
    public void login(String qwerty, String password);

    public User getUser();

}
