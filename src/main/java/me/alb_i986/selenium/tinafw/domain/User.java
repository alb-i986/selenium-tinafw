package me.alb_i986.selenium.tinafw.domain;

import me.alb_i986.selenium.tinafw.pages.HomePage;

/**
 * A User has a Browser.
 * A User may also have an email, and a password.
 */
public class User {

	private Browser browser;
	private String email = "";
	private String password = "";

	/**
	 * Create a User with nothing but a (closed) Browser.
	 * Then, you may set the other fields using
	 * the <i>with</i> methods, e.g. {@link #withEmail(String)}.
	 */
	public User() {
		this.browser = new Browser();
	}

	public User(Browser browser) {
		this.browser = browser;
	}


	/**
	 * Open the browser and browse to the homepge.
	 * Please bear in mind that it is illegal to open
	 * the browser more than once without closing it
	 * first.
	 * 
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

	public User withEmail(String email) {
		if(email == null)
			this.email = "";
		else
			this.email = email;
		return this;
	}
	
	public String getPassword() {
		return password;
	}
	
	public User withPassword(String password) {
		this.password = password;
		return this;
	}


	public Browser getBrowser() {
		return browser;
	}

}
