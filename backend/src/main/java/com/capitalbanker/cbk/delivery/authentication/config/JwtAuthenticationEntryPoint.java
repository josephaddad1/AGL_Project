package com.capitalbanker.cbk.delivery.authentication.config;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

	private static final long serialVersionUID = -7858869558953243875L;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {
		String serviceErrorCode = (String) request.getAttribute("SERVICE_ERROR_CODE");
		if (serviceErrorCode != null) {

			// unauthorized access
			if (serviceErrorCode.equals("CBK-AUTH-004") || serviceErrorCode.equals("CBK-AUTH-007")
					|| serviceErrorCode.equals("CBK-AUTH-008")) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, serviceErrorCode);
			} else
				response.sendError(response.getStatus(), serviceErrorCode);
		} else {

			response.sendError(response.getStatus(), authException.getMessage());
		}

		/*
		 * String error = ProjectConstats.Error; if (error == null)
		 * response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
		 * authException.getMessage());
		 * 
		 * response.sendError(HttpServletResponse.SC_UNAUTHORIZED, error);
		 * ProjectConstats.Error="";
		 */

	}
}