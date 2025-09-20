package booking.service;

import booking.entity.User;

public interface IRegister {
    public User signUp(String userId, String name, String email, String password,
                String phoneNumber, String status, String role, String lastLogin, int failedAttempts);
}
