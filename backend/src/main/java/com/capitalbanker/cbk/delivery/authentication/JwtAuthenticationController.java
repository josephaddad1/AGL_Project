package com.capitalbanker.cbk.delivery.authentication;

import java.util.Date;

import javax.persistence.EntityExistsException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capitalbanker.cbk.delivery.authentication.config.JwtTokenUtil;
import com.capitalbanker.cbk.delivery.shared.model.TokenObject;
import com.capitalbanker.cbk.delivery.shared.model.UserObject;
import com.capitalbanker.cbk.delivery.shared.response.ErrorDetailsResponse;
import com.capitalbanker.cbk.delivery.shared.response.ServiceResponseBase;
import com.capitalbanker.cbk.delivery.shared.response.UserLoginResponse;
import com.capitalbanker.cbk.delivery.shared.service.UserService;
import com.jayway.jsonpath.EvaluationListener.FoundResult;

@RestController
@CrossOrigin
@RequestMapping(value = "/authentication")
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ServiceResponseBase createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
			throws Exception {
		try {
			HttpServletResponse response;
			UserObject user = userDetailsService.loadUser(authenticationRequest.getUsername(), true);

			if ("D".equals(user.getStatus_code())) {
				throw new DisabledException(" ");
			}
			authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

			final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

			if (userDetails == null) {
				return new ErrorDetailsResponse("CBK-AUTH-003", new Date());
			}
			final String token = jwtTokenUtil.generateToken(userDetails, false);
			final String refreshToken = jwtTokenUtil.generateToken(userDetails, true);

			TokenObject tokens = new TokenObject(token, refreshToken);

			UserLoginResponse userLoginResponse = new UserLoginResponse(tokens, user);
			return new ServiceResponseBase<UserLoginResponse>(userLoginResponse, "User logged in successfully");

		} catch (BadCredentialsException e) {
			throw new Exception("CBK-AUTH-001");
		} catch (DisabledException e) {
			throw new Exception("CBK-AUTH-002");
		}
		catch(UsernameNotFoundException e) {
			throw new UsernameNotFoundException("CBK-AUTH-001");
			
			
		}

		catch (Exception e) {

			throw new Exception(e);
		}

	}

	@RequestMapping(value = "/newToken", method = RequestMethod.GET)
	public ServiceResponseBase<TokenObject> createAuthenticationToken(HttpServletRequest req) throws Exception {

		final String requestTokenHeader = req.getHeader("Authorization");
		TokenObject newTokenObject = null;
		String jwtToken = null;
		// JWT Token is in the form "Bearer token". Remove Bearer word and get
		// only the Token
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);

			newTokenObject = tokenService.generateNewToken(jwtToken);
		}
		if (newTokenObject != null) {
			return new ServiceResponseBase<TokenObject>(newTokenObject, "New Token genration Successed!");
		} else {
			throw new Exception("New Token Generation Failed");
		}

	}

	@RequestMapping(value = "/requestResetPassword", method = RequestMethod.GET)
	public void requestResetPassword(@RequestParam String username) throws Exception {

		this.userService.requestResetPasswod(username);
	}

	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	public ServiceResponseBase<String> resetPassword(HttpServletRequest req, @RequestParam String password,
			@RequestParam String confPassword) throws Exception {
		final String requestTokenHeader = req.getHeader("Authorization");
		TokenObject newTokenObject = null;
		String jwtToken = null;
		// JWT Token is in the form "Bearer token". Remove Bearer word and get
		// only the Token
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
		}

		String username = null;
		if (jwtToken != null) {
			username = this.jwtTokenUtil.getUsernameFromToken(jwtToken);
		}
		this.userService.resetPassword(username, password, confPassword);

		return new ServiceResponseBase<String>("Password successfully changed", null);

	}

	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public ServiceResponseBase<String> changePassword(HttpServletRequest req, @RequestParam String oldPassword,
			@RequestParam String password, @RequestParam String confPassword) throws Exception {
		final String requestTokenHeader = req.getHeader("Authorization");
		TokenObject newTokenObject = null;
		String jwtToken = null;
		// JWT Token is in the form "Bearer token". Remove Bearer word and get
		// only the Token
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
		}

		String username = null;
		if (jwtToken != null) {
			username = this.jwtTokenUtil.getUsernameFromToken(jwtToken);
		}
		this.userService.changePassword(username, oldPassword, password, confPassword);

		return new ServiceResponseBase<String>("Password successfully changed", null);
	}

	private void authenticate(String username, String password) throws Exception {

		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

	}
}