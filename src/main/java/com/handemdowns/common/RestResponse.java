package com.handemdowns.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RestResponse<T> {
	public static final String SUCCESS = "success";
	public static final String FAIL = "fail";
	public static final String ERROR = "error";

	private String status;
	private String message;
	private T data;
	private int code;

	public RestResponse(String status, T data) {
		this.status = status;
		this.data = data;
	}

	public RestResponse(String status, String message, int code, T data) {
		this.status = status;
		this.message = message;
		this.code = code;
		this.data = data;
	}
}