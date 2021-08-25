package com.handemdowns.security;

import com.handemdowns.web.util.RequestUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("authenticationFailureHandler")
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		String errorMessage = "Invalid Email or Password";
		if (exception.getMessage().equalsIgnoreCase("User is disabled")) {
			errorMessage = "Your account is disabled, please contact help@handemdowns.com";
		} else if (exception.getMessage().equalsIgnoreCase("User account has expired")) {
			errorMessage = "Your account has expired. Please register again";
		} else if (exception.getMessage().equalsIgnoreCase("IP is blocked")) {
			errorMessage = "The maximum number of unsuccessful login attempts has been reached";
		}

		if(RequestUtil.isAjaxRequest(request)) {
			RequestUtil.sendJsonResponse(response, "failed", errorMessage);
		} else {
			setDefaultFailureUrl("/login?error");
			super.onAuthenticationFailure(request, response, exception);

			request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, errorMessage);
		}
    }
}