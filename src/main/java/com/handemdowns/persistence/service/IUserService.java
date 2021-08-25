package com.handemdowns.persistence.service;

import com.handemdowns.persistence.dto.UserRegistrationDto;
import com.handemdowns.persistence.model.*;
import com.handemdowns.validation.EmailExistsException;

import java.util.List;

public interface IUserService {
	List<User> findAll();
	User registerNewUserAccount(UserRegistrationDto accountDto) throws EmailExistsException;
	User getUser(String verificationToken);
	void saveRegisteredUser(User user);
	void disableRegisteredUser(User user);
	void deleteUser(User user);
	void addRoleToUser(User user, Role role);
	void promteToAdmin(User user);
	void createVerificationTokenForUser(User user, String token);
	VerificationToken getVerificationToken(String VerificationToken);
	VerificationToken generateNewVerificationToken(String token);
	void createPasswordResetTokenForUser(User user, String token);
	User findUserByEmail(String email);
	PasswordResetToken getPasswordResetToken(String token);
	User getUserByPasswordResetToken(String token);
	User getUserByID(long id);
	void changeUserPassword(User user, String password);
	boolean checkIfValidOldPassword(User user, String password);
	void watchAd(User user, Ad ad);
	void unwatchAd(User user, Ad ad);
	Long count();
}