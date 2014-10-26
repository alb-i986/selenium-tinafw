package me.alb_i986.selenium.tinafw.tasks;

import me.alb_i986.selenium.tinafw.domain.User;

import org.apache.log4j.Logger;
import org.junit.Assert;

/**
 * Abstract WebTask responsible for knowing the 'who',
 * i.e. what user is going to perform the task.
 */
public abstract class BaseWebTask implements WebTask {
	
	protected static final Logger logger = Logger.getLogger(WebTask.class);
	
	protected User user;
	
	public WebTask setUser(User user) {
		Assert.assertNotNull(user);
		this.user = user;
		return this;
	}

	public User getUser() {
		return user;
	}

}
