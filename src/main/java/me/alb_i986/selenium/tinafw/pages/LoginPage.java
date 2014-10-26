package me.alb_i986.selenium.tinafw.pages;


/**
 * Represents the page for logging in which will typically offer
 * a form with two text fields, one for the username, and one
 * for the password.
 */
public interface LoginPage extends LoadablePage {
	
	/**
	 * Fill in the login form, and submit it.
	 * @return the landing page after logging in
	 */
	public Page loginAs(String username, String password);

}