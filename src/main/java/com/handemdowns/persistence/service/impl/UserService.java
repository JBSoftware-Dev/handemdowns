package com.handemdowns.persistence.service.impl;

import com.handemdowns.persistence.dao.PasswordResetTokenRepository;
import com.handemdowns.persistence.dao.RoleRepository;
import com.handemdowns.persistence.dao.UserRepository;
import com.handemdowns.persistence.dao.VerificationTokenRepository;
import com.handemdowns.persistence.dto.UserRegistrationDto;
import com.handemdowns.persistence.model.*;
import com.handemdowns.persistence.service.IUserService;
import com.handemdowns.validation.EmailExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UserService implements IUserService {
    private final UserRepository repository;
	private final RoleRepository roleRepository;
    private final VerificationTokenRepository tokenRepository;
    private final PasswordResetTokenRepository passwordTokenRepository;
    private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserService(UserRepository repository, RoleRepository roleRepository, VerificationTokenRepository tokenRepository,
					   PasswordResetTokenRepository passwordTokenRepository, PasswordEncoder passwordEncoder) {
		this.repository = repository;
		this.roleRepository = roleRepository;
		this.tokenRepository = tokenRepository;
		this.passwordTokenRepository = passwordTokenRepository;
		this.passwordEncoder = passwordEncoder;
	}

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User registerNewUserAccount(UserRegistrationDto accountDto) throws EmailExistsException {
        if (emailExist(accountDto.getEmail())) {
            throw new EmailExistsException("There is an account with that email address: " + accountDto.getEmail());
        }
        User user = User.builder(accountDto.getEmail().toLowerCase().trim(), accountDto.getName().trim(), passwordEncoder.encode(accountDto.getPassword()))
				.roles(Collections.singletonList(roleRepository.findByName("ROLE_USER")))
				.enabled(false)
				.build();
        return repository.save(user);
    }

    @Override
    public User getUser(String verificationToken) {
        return tokenRepository.findByToken(verificationToken).getUser();
    }

    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }

    @Override
    public void saveRegisteredUser(User user) {
		user.setEnabled(true);
        repository.save(user);
    }

    @Override
    public void disableRegisteredUser(User user) {
        user.setEnabled(false);
        repository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        repository.delete(user);
    }

    @Override
    public void addRoleToUser(User user, Role role) {
	    user.getRoles().add(role);
        repository.save(user);
    }

    @Override
    public void promteToAdmin(User user) {
        addRoleToUser(user, roleRepository.findByName("ROLE_ADMIN"));
    }

    @Override
    public void createVerificationTokenForUser(User user, String token) {
        VerificationToken myToken = VerificationToken.builder(token, user).build();
        tokenRepository.save(myToken);
    }

    @Override
    public VerificationToken generateNewVerificationToken(String existingVerificationToken) {
        VerificationToken vToken = tokenRepository.findByToken(existingVerificationToken);
        vToken.updateToken(UUID.randomUUID().toString());
        vToken = tokenRepository.save(vToken);
        return vToken;
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken myToken = PasswordResetToken.builder(token, user).build();
        passwordTokenRepository.save(myToken);
    }

    @Override
    public User findUserByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public PasswordResetToken getPasswordResetToken(String token) {
        return passwordTokenRepository.findByToken(token);
    }

    @Override
    public User getUserByPasswordResetToken(String token) {
        return passwordTokenRepository.findByToken(token).getUser();
    }

    @Override
    public User getUserByID(long id) {
        return repository.findOne(id);
    }

    @Override
    public void changeUserPassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        repository.save(user);
    }

    @Override
    public boolean checkIfValidOldPassword(User user, String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    @Override
    public void watchAd(User user, Ad ad) {
        user.getWatchlist().add(ad);
        repository.save(user);
    }

    @Override
    public void unwatchAd(User user, Ad ad) {
        user.getWatchlist().remove(ad);
        repository.save(user);
    }

    @Override
    public Long count() {
        return repository.count();
    }

    private boolean emailExist(String email) {
        User user = repository.findByEmail(email);
        return user != null;
    }
}