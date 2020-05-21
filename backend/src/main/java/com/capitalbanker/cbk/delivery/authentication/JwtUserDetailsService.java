package com.capitalbanker.cbk.delivery.authentication;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.capitalbanker.cbk.delivery.shared.ProjectConstats;
import com.capitalbanker.cbk.delivery.shared.model.UserObject;
import com.capitalbanker.cbk.delivery.shared.repository.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepo;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserObject user = null;
		user = userRepo.findByUsername(username);
		if (user != null) {

			return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
		}
		ProjectConstats.Error = "CBK-Auth-003";
		throw new UsernameNotFoundException("Username not found");

	}

	public UserObject loadUser(String username, boolean throwException) throws UsernameNotFoundException {

		UserObject user = null;
		user = userRepo.findByUsername(username);
		if (user != null) {

			return user;
		}
		ProjectConstats.Error = "CBK-Auth-003";
		if (throwException)
			throw new UsernameNotFoundException("Username not found");

		return null;

	}

	public UserDetails loadUserByUsernameWithoutException(String username) {

		UserObject user = null;
		user = userRepo.findByUsername(username);
		if (user != null) {

			return new User(user.getUsername(), "", new ArrayList<>());
		}
		return null;

	}

}