package com.handemdowns.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="handemdowns")
public class HandemdownsProperties {
	@Getter
	@Setter
	private Security security;

	private static class Security {
		@Getter
		@Setter
		private String adminAccount;

		@Getter
		@Setter
		private String adminPassword;
	}
}