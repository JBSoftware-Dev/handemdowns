package com.handemdowns.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="api")
@Data
public class ApiProperties {
	@Getter
	@Setter
	private Google google;

	private static class Google {
		@Getter
		@Setter
		private Maps maps;

		@Getter
		@Setter
		private Recaptcha recaptcha;
	}

	private static class Maps {
		@Getter
		@Setter
		private String key;
	}

	private static class Recaptcha {
		@Getter
		@Setter
		private String key;
	}
}