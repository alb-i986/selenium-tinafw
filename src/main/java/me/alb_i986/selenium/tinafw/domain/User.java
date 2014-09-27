package me.alb_i986.selenium.tinafw.domain;

/**
 * A User has a Browser.
 * Also, a User may have a username and a password.
 */
public class User {

	private Browser browser;
	private String username = "";
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
	
	public User doTasks(WebTask... tasks) {
		WebTask previousTask = null;
		for(WebTask task : tasks) {
			task.doTask(previousTask.lastVisitedPage());
			previousTask = task;
		}
		return this;
	}
	
	/**
	 * Any method chain ends with this method.
	 * @see Browser#close()
	 */
	public void closeBrowser() {
		browser.close();
	}
	

	public String getUsername() {
		return username;
	}

	public User withUsername(String username) {
		if(username == null)
			this.username = "";
		else
			this.username = username;
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
