package me.alb_i986.selenium.tinafw.domain;

import org.apache.log4j.Logger;

import me.alb_i986.selenium.tinafw.tasks.CompositeWebTask;
import me.alb_i986.selenium.tinafw.tasks.WebTask;
import me.alb_i986.selenium.tinafw.ui.Page;

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
 *   .doTasks(
 *      new XYTask(),
 *      new YZTask(),
 *      [..]
 *   ).closeBrowser()
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
@SuppressWarnings("rawtypes")
public class User<T extends User> {

	protected static final Logger logger = Logger.getLogger(User.class);
	
	private SupportedBrowser browserType;
	
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
	 * @see Browser#open(SupportedBrowser)
	 */
	@SuppressWarnings("unchecked")
	public T openBrowser(SupportedBrowser browserType) {
		browser.open(browserType);
		return (T) this;
	}

	/**
	 * Open the browser previously specified with
	 * {@link #withBrowserType(SupportedBrowser)}.
	 * 
	 * @return this
	 * @throws IllegalStateException if the browser type has not been specified,
	 *         i.e. withBrowserType has not been called
	 * 
	 * @see #openBrowser(SupportedBrowser)
	 */
	@SuppressWarnings("unchecked")
	public T openBrowser() {
		if(browserType == null)
			throw new IllegalStateException("browserType has not been set. "
					+ "Please set it with #withBrowserType");
		openBrowser(browserType);
		return (T) this;
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
	@SuppressWarnings("unchecked")
	public T browseTo(String relativeUrl) {
		browser.browseTo(relativeUrl);
		return (T) this;
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
	 * @see WebTask#run(Page)
	 */
	@SuppressWarnings("unchecked")
	public T doTask(WebTask task) {
		task.setUser(this);
		this.currentPage = task.run(currentPage);
		return (T) this;
	}

	/**
	 * Instantiate a {@link CompositeWebTask} made up of all
	 * of the given tasks, and run it.
	 * <p>
	 * Convenience method for running multiple tasks at once
	 * without wrapping them around a {@link CompositeWebTask}
	 * by hand.
	 * 
	 * @param tasks
	 * @return this
	 * 
	 * @see #doTask(WebTask)
	 */
	public T doTasks(WebTask... tasks) {
		return doTask(new CompositeWebTask(tasks));
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

	@SuppressWarnings("unchecked")
	public T withUsername(String username) {
		if(username == null)
			this.username = "";
		else
			this.username = username;
		return (T) this;
	}
	
	public String getPassword() {
		return password;
	}
	
	@SuppressWarnings("unchecked")
	public T withPassword(String password) {
		this.password = password;
		return (T) this;
	}
	
	@SuppressWarnings("unchecked")
	public T withBrowserType(SupportedBrowser browserType) {
		if(browserType == null)
			throw new IllegalArgumentException("browser type is null");
		this.browserType = browserType;
		return (T) this;
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
	public boolean equals(T user) {
		if(username == null || user == null)
			return false;
		return username.equals(user.getUsername());
	}

}
