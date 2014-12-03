package me.alb_i986.selenium.tinafw.utils;

public class SettingNotFoundException extends ConfigException {

	private static final long serialVersionUID = -3944591840473180100L;

	public SettingNotFoundException() {
		super();
	}

	public SettingNotFoundException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SettingNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public SettingNotFoundException(String message) {
		super(message);
	}

	public SettingNotFoundException(Throwable cause) {
		super(cause);
	}

}
