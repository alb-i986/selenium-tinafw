package me.alb_i986.selenium.tinafw.tasks;

import me.alb_i986.selenium.tinafw.domain.Browser;
import me.alb_i986.selenium.tinafw.domain.WebUser;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

/**
 * Abstract WebTask responsible for knowing the 'who',
 * i.e. what user is going to perform the task.
 */
public abstract class BaseWebTask implements WebTask {
	
	protected static final Logger logger = Logger.getLogger(WebTask.class);
	
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
	 * Convenience method for retrieving the underlying {@link Browser}.
	 * associated to the user this task runs as.
	 * 
	 * @return the underlying {@link Browser}
	 */
	protected Browser browser() {
		return user.getBrowser();
	}

	/**
	 * Convenience method for retrieving the underlying {@link WebDriver}
	 * associated to the user this task runs as.
	 * 
	 * @return the underlying {@link WebDriver}
	 */
	protected WebDriver driver() {
		return browser().getWebDriver();
	}

}
