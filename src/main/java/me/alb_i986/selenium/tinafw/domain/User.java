package me.alb_i986.selenium.tinafw.domain;

import me.alb_i986.selenium.tinafw.pages.HomePage;

/**
 * A User has a Browser.
 * A User may have an email, a role.
 */
public class User {

	private Browser browser;
	private String email = "";
	private String role = "";

	/**
	 * Create a User with nothing but a Browser.
	 * Then, you may set the other fields using
	 * the with methods, e.g. {@link #withEmail(String)}.
	 */
	public User() {
		this.browser = new Browser();
	}

	public User(Browser browser) {
		this.browser = browser;
	}


	/**
	 * Open the browser and browse to the homepge.
	 * @return {@link HomePage}
	 * 
	 * @see Browser#open()
	 * @see Browser#startSession()
	 */
	public HomePage openBrowser() {
		browser.open();
		return browser.startSession();
	}
	
	/**
	 * @see Browser#close()
	 */
	public void closeBrowser() {
		browser.close();
	}
	

	public String getEmail() {
		return email;
	}

	public String getRole() {
		return role;
	}

	public User withEmail(String email) {
		if(email == null)
			this.email = "";
		else
			this.email = email;
		return this;
	}

	public User withRole(String role) {
		if(role == null)
			this.role = "";
		else
			this.role = role;
		return this;
	}


	public Browser getBrowser() {
		return browser;
	}

}
