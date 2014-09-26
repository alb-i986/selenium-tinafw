package me.alb_i986.selenium.tinafw.utils;

public class ConfigException extends RuntimeException {

	private static final long serialVersionUID = -6017026647523692805L;

	public ConfigException() {
		super();
	}

	public ConfigException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ConfigException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConfigException(String message) {
		super(message);
	}

	public ConfigException(Throwable cause) {
		super(cause);
	}

}
