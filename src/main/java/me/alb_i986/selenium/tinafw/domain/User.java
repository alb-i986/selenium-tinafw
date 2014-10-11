package me.alb_i986.selenium.tinafw.domain;

import org.apache.log4j.Logger;

import me.alb_i986.selenium.tinafw.pages.Page;
import me.alb_i986.selenium.tinafw.tasks.CompositeWebTask;
import me.alb_i986.selenium.tinafw.tasks.WebTask;

/**
 * A User has a Browser, which he/she can open and close;
 * can browse to a relative URL; can do tasks.
 * Finally, a User may have a username and a password.
 * If your user requires more information (e.g. a role),
 * you may extend this class and add as many fields as needed.
 * <p>
 * Typically, User will be the only object directly referenced in your
 * test code (apart from WebTask's).
 * In fact, from User you can reach Browser, and then WebDriver
 * (which may be useful for debugging purposes).
 * <p>
 * A typical use is as follows:
 * <pre>
 * {@code
 * new User()
 *   .openBrowser();
 *   .doTask(new XXXCompositeWebTask( [..] ));
 *   .closeBrowser()
 * ;
 * }
 * </pre>
 * This is how tests should look like.
 * 
 */
public class User {

	protected static final Logger logger = Logger.getLogger(User.class);
	
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

	/**
	 * Any method chain ends with this method.
	 * @see Browser#close()
	 */
	public void closeBrowser() {
		browser.close();
	}

	/**
	 * @param relativeUrl a relative URL (relative to {@link Page#BASE_URL})
	 * @see Browser#browseTo(String)
	 */
	public User browseTo(String relativeUrl) {
		browser.browseTo(relativeUrl);
		return this;
	}
	
	/**
	 * Run the given task (or task<i>s</i>, if it is a {@link CompositeWebTask}),
	 * with no initial page.
	 * This means that the given task (or the <i>first</> subtask if it is a
	 * {@link CompositeWebTask}) must do the navigation itself.
	 * 
	 * @param task
	 * @return this
	 * 
	 * @see #doTask(Page, WebTask)
	 */
	public User doTask(WebTask task) {
		return doTask(null, task);
	}

	/**
	 * Run the given task (or task<i>s</i>, if it is a {@link CompositeWebTask}),
	 * starting from the given initial page.
	 * 
	 * @param initialPage
	 * @param task
	 * @return this
	 */
	public User doTask(Page initialPage, WebTask task) {
		task.doTask(initialPage);
		return this;
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
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof User))
			return false;
		return equals((User)o);
	}
	
	/**
	 * @param user
	 * @return true if the two users have the same username 
	 */
	public <T extends User> boolean equals(T user) {
		if(username == null || user == null)
			return false;
		return username.equals(user.getUsername());
	}

}
