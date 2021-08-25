package com.handemdowns.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {
	private LoginAttemptService loginAttemptService;

	@Autowired
	public AuthenticationSuccessEventListener(LoginAttemptService loginAttemptService) {
		this.loginAttemptService = loginAttemptService;
	}

	@Override
	public void onApplicationEvent(AuthenticationSuccessEvent e) {
		WebAuthenticationDetails auth = (WebAuthenticationDetails) e.getAuthentication().getDetails();
		if (auth != null) {
			loginAttemptService.loginSucceeded(auth.getRemoteAddress());
		}
	}
}