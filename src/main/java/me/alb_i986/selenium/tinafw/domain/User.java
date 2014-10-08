package me.alb_i986.selenium.tinafw.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import me.alb_i986.selenium.tinafw.pages.Page;
import me.alb_i986.selenium.tinafw.tasks.CompositeWebTask;
import me.alb_i986.selenium.tinafw.tasks.Given;
import me.alb_i986.selenium.tinafw.tasks.Then;
import me.alb_i986.selenium.tinafw.tasks.WebTask;
import me.alb_i986.selenium.tinafw.tasks.When;

/**
 * A User has a Browser.
 * Also, a User may have a username and a password.
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

	/**
	 * If any arg is null, that step will be simply skipped.
	 * So, if your test case happens not to have one of the steps,
	 * you can just pass null for the missing step.
	 * 
	 * @param given
	 * @param when
	 * @param then
	 * @return this
	 */
	public User runSteps(Given given, When when, Then then) {
		// check args and discard those null
		List<WebTask> tasks = new ArrayList<>();
		if(given != null)
			tasks.add(given);
		if(when != null)
			tasks.add(when);
		if(then != null)
			tasks.add(then);
		doTask(new CompositeWebTask(tasks));
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
