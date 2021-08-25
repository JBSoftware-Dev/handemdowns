package com.handemdowns.security;

import com.handemdowns.web.util.RequestUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

@Component("authenticationSuccessHandler")
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	@Getter
	@Setter
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
		if(RequestUtil.isAjaxRequest(request)) {
			RequestUtil.sendJsonResponse(response, "success", "true");
		} else {
			handle(request, response, authentication);
			HttpSession session = request.getSession(false);
			if (session != null) {
				session.setMaxInactiveInterval(30 * 60);
			}
			clearAuthenticationAttributes(request);
		}
    }

    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        SavedRequest savedRequest = new HttpSessionRequestCache().getRequest(request, response);
        String targetUrl;
        if (savedRequest != null) {
            targetUrl = savedRequest.getRedirectUrl();
        } else {
            targetUrl = determineTargetUrl(authentication);
        }

        if (response.isCommitted()) {
            return;
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(Authentication authentication) {
        boolean isUser = false;
        boolean isAdmin = false;
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("PERMISSION_USER")) {
                isUser = true;
            } else if (grantedAuthority.getAuthority().equals("PERMISSION_ADMIN")) {
                isAdmin = true;
                isUser = false;
                break;
            }
        }
        if (isUser) {
            return "/my-ads";
        } else if (isAdmin) {
            return "/admin/home";
        } else {
            throw new IllegalStateException();
        }
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}