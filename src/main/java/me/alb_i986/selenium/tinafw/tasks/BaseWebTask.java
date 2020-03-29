package me.alb_i986.selenium.tinafw.tasks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import me.alb_i986.selenium.tinafw.domain.Browser;
import me.alb_i986.selenium.tinafw.domain.WebUser;

/**
 * Abstract WebTask responsible for knowing the 'who',
 * i.e. what user is going to perform the task.
 */
public abstract class BaseWebTask implements WebTask {
	
	protected static final Logger logger = LogManager.getLogger(WebTask.class);
	
	protected WebUser user;
	
	public BaseWebTask() {
	}

	@Override
	public WebTask setUser(WebUser user) {
		if(user == null)
			throw new IllegalArgumentException("user is null");
		this.user = user;
		return this;
	}

	@Override
	public WebUser getUser() {
		return user;
	}


	/**
	 * Convenience method for retrieving the underlying {@link Browser}
	 * associated to the user this task runs as.
	 * 
	 * @return the underlying {@link Browser}
	 */
	protected Browser browser() {
		if(user == null)
			throw new IllegalStateException("User is null");
		return user.getBrowser();
	}

	/**
	 * Convenience method for retrieving the underlying {@link WebDriver}
	 * associated to the user this task runs as.
	 * 
	 * @return the underlying {@link WebDriver}
	 */
	protected WebDriver driver() {
		if(browser() == null)
			throw new IllegalStateException("Browser is null");
		return browser().getWebDriver();
	}

}
