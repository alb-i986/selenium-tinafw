package me.alb_i986.selenium.tinafw.domain;

import org.apache.log4j.Logger;

import me.alb_i986.selenium.tinafw.pages.Page;
import me.alb_i986.selenium.tinafw.tasks.CompositeWebTask;
import me.alb_i986.selenium.tinafw.tasks.WebTask;

/**
 * User encapsulates Browser, and has two main behaviors:
 * <ul>
 * <li>{@link #openBrowser()}: creates an instance of WebDriver, thus opening a real browser</li>
 * <li>{@link #doTask(WebTask)}: performs the given actions against the SUT</li>
 * </ul>
 * <p>
 * A typical use is as follows:
 * <pre>
 * {@code
 * new User()
 *   .openBrowser();
 *   .doTask(new CompositeWebTask( [..] ));
 *   .closeBrowser()
 * ;
 * }
 * </pre>
 * BTW, this is how tests will look like.
 * <p>
 * Typically the navigation will be performed by tasks.
 * Using {@link #browseTo(String)} instead is not recommended,
 * and it will probably be deprecated/removed.
 * <p>
 * A User may have a username and a password.
 * User may be extended in case more information were required (e.g.: a role).
 * Subclasses should just add fields, setters, and getters,
 * and possibly override {@link #equals(User)}.
 * 
 */
public class User {

	protected static final Logger logger = Logger.getLogger(User.class);
	
	private Browser browser;
	private Page currentPage;
	
	private String username = "";
	private String password = "";

	/**
	 * Create a User with a closed {@link Browser}, and no initial page.
	 */
	public User() {
		this(new Browser());
	}

	/**
	 * Create a User with the given Browser (which may be open or closed)
	 * and no initial page.
	 * 
	 * @param browser
	 */
	public User(Browser browser) {
		this(browser, null);
	}
	
	/**
	 * 
	 * Create a User with the given Browser (which may be open or closed)
	 * and the given initial page.
	 * 
	 * @param browser
	 * @param initialPage
	 */
	public User(Browser browser, Page initialPage) {
		this.browser = browser;
		this.currentPage = initialPage;
	}


	/**
	 * Open the browser but do not navigate to any page yet.
	 * If the browser was already open, does nothing.
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
	 * Run the given task (or task<i>s</i>,
	 * if it is a {@link CompositeWebTask}),
	 * starting from the known current page
	 * (which is part of the state of this object).
	 * Finally update the current page with the one
	 * the given task visited last.
	 * 
	 * @param task
	 * @return this
	 * 
	 * @see WebTask#doTask(Page)
	 */
	public User doTask(WebTask task) {
		task.setUser(this);
		this.currentPage = task.doTask(currentPage);
		return this;
	}


	/**
	 * @return the page that the last performed task was visiting;
	 *         or the initial page (which may be null), 
	 *         if no tasks have been performed yet.
	 */
	public Page getCurrentPage() {
		return currentPage;
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
