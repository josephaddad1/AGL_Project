package com.capitalbanker.cbk.delivery.shared.service;

import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.capitalbanker.cbk.delivery.authentication.JwtUserDetailsService;
import com.capitalbanker.cbk.delivery.authentication.config.JwtTokenUtil;
import com.capitalbanker.cbk.delivery.shared.model.CustomIdFullName;
import com.capitalbanker.cbk.delivery.shared.model.UserObject;
import com.capitalbanker.cbk.delivery.shared.model.UsersRoles;
import com.capitalbanker.cbk.delivery.shared.repository.UserRepository;
import com.capitalbanker.cbk.delivery.shared.repository.UsersRolesRepo;

@Service
public class UserService {

	@Value("${frontend_reset_password_url}")
	String url;

	@Autowired
	private UserRepository userDAO;

	@Autowired
	private UsersRolesRepo usersRolesRepo;

	@Autowired
	JwtTokenUtil jwtTokenUtil;

	@Autowired
	JwtUserDetailsService userDetailsService;

	@Autowired
	public JavaMailSender emailSender;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	public List<UserObject> getAllUsers() {
		return this.userDAO.findAll();
	}

	public Page<UserObject> findUser(String username, int page, int pageSize) {
		return this.userDAO.getUser(username, PageRequest.of(page, pageSize));
	}

	public List<CustomIdFullName> getUsersList(String username) {
		return this.userDAO.getUsersList(username);
	}

	public void requestResetPasswod(String username) throws AddressException, MessagingException {

		UserDetails userDetails = userDetailsService.loadUserByUsernameWithoutException(username);

		if (userDetails != null) {

			String token = this.jwtTokenUtil.generateToken(userDetails, true);

			Properties props = new Properties();
			// TODO set the host in properties
			props.put("mail.smtp.host", "10.1.222.30");
			props.put("mail.smtp.port", "25");
			props.put("mail.debug", "true");
			Session session = Session.getDefaultInstance(props);
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("no-reply-cbkci@capital-banking.com"));
			message.setRecipient(RecipientType.TO, new InternetAddress(username + "@capital-banking.com"));
			message.setSubject("CBK-CI-Reset Password");

			String mail = "Hello \n Click on this link to reset your password for Delivery tool account\n";

			message.setText(mail + this.url + "?expire=" + token);

			message.setSentDate(new Date());
			Transport.send(message);

		}

	}

	public void insertNewUser(UserObject user, String roleId) throws EntityExistsException {
	
			UserObject oldUser = userDetailsService.loadUser(user.getUsername(), false);

			// new
			if (user.getId() == null) {
				if (oldUser == null) {
					String userId = UUID.randomUUID().toString();
					user.setId(userId);
					user.setPassword(bcryptEncoder.encode("Pass123"));
				} else {
					throw new EntityExistsException("Username already exists");
				}

			}
			// update
			else {
				if (oldUser != null) {
					if (oldUser.getId().equals(user.getId())) {
						user.setPassword(oldUser.getPassword());
					} else {
						throw new EntityExistsException("Username already exists");
					}
				} else {
					UserObject user1 = userDAO.findById(user.getId()).get();
					user.setPassword(user1.getPassword());
				}
			}

			this.userDAO.save(user);
			this.usersRolesRepo.save(new UsersRoles(user.getId(), roleId));
		

	}

	public void resetPassword(String username, String password, String confPass) throws Exception {

		if (!password.equals(confPass)) {
			throw new Exception("Passwords does not match!");
		}
		UserObject user = userDetailsService.loadUser(username, true);

		if (user != null) {
			user.setPassword((bcryptEncoder.encode(password)));
			this.userDAO.save(user);
		}

	}

	public void changePassword(String username, String oldPassword, String password, String confPass) throws Exception {

		if (!password.equals(confPass)) {
			throw new Exception("Passwords does not match!");
		}
		UserObject user = userDetailsService.loadUser(username, true);

		if (user != null) {
			if (bcryptEncoder.matches(oldPassword, user.getPassword())) {

				user.setPassword((bcryptEncoder.encode(password)));
			} else {
				throw new Exception("Incorrect Password");
			}

			this.userDAO.save(user);
		}

	}
}