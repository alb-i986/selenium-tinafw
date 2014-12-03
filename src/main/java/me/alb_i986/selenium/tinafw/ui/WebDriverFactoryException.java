package me.alb_i986.selenium.tinafw.ui;

import org.openqa.selenium.WebDriverException;

public class WebDriverFactoryException extends WebDriverException {

	private static final long serialVersionUID = 6376298174858831795L;

	public WebDriverFactoryException() {
	}

	public WebDriverFactoryException(String message) {
		super(message);
	}

	public WebDriverFactoryException(Throwable cause) {
		super(cause);
	}

	public WebDriverFactoryException(String message, Throwable cause) {
		super(message, cause);
	}

}
