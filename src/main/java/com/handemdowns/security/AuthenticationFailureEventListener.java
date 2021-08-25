package com.handemdowns.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFailureEventListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
	private LoginAttemptService loginAttemptService;

	@Autowired
	public AuthenticationFailureEventListener(LoginAttemptService loginAttemptService) {
		this.loginAttemptService = loginAttemptService;
	}

	@Override
	public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent e) {
		WebAuthenticationDetails auth = (WebAuthenticationDetails) e.getAuthentication().getDetails();
		if (auth != null) {
			loginAttemptService.loginFailed(auth.getRemoteAddress());
		}
	}
}