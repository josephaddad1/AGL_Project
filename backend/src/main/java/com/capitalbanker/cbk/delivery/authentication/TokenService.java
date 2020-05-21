package com.capitalbanker.cbk.delivery.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.capitalbanker.cbk.delivery.authentication.config.JwtTokenUtil;
import com.capitalbanker.cbk.delivery.shared.model.TokenObject;

@Service
public class TokenService {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	public TokenObject generateNewToken(String refreshToken) {

		String username = jwtTokenUtil.getUsernameFromToken(refreshToken);

		System.out.print(username);

		UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);

		String newToken = jwtTokenUtil.generateToken(userDetails, false);
		String newRefreshToken = jwtTokenUtil.generateToken(userDetails, true);

		return new TokenObject(newToken, newRefreshToken);

	}
}
