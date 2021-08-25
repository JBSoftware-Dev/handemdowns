package com.handemdowns.web.util;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.web.util.matcher.ELRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RequestUtil {
	public static final String JSON_VALUE = "{\"%s\": %s}";

	private static final RequestMatcher REQUEST_MATCHER = new ELRequestMatcher("hasHeader('X-Requested-With','XMLHttpRequest')");

	public static Boolean isAjaxRequest(HttpServletRequest request) {
		return REQUEST_MATCHER.matches(request);
	}

	public static void sendJsonResponse(HttpServletResponse response, String key, String message) {
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		try {
			response.getWriter().write(String.format(JSON_VALUE, key, message));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ClientHttpRequestFactory getClientHttpRequestFactory(int timeout) {
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		clientHttpRequestFactory.setConnectTimeout(timeout);
		return clientHttpRequestFactory;
	}
}