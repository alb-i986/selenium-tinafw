package me.alb_i986.selenium.tinafw.domain;

/**
 * A User has a Browser.
 * A User may also have an email and a password.
 */
public class User {

	private Browser browser;
	private String email = "";
	private String password = "";

	/**
	 * Create a User with nothing but a (closed) Browser.
	 */
	public User() {
		this.browser = new Browser();
	}

	public User(Browser browser) {
		this.browser = browser;
	}


	/**
	 * Open the browser but do not navigate to any page yet.
	 * Please bear in mind that it is illegal to open
	 * the browser more than once without closing it
	 * first.
	 * 
	 * @return this
	 * @see Browser#open()
	 */
	public User openBrowser() {
		browser.open();
		return this;
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
