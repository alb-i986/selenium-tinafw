package me.alb_i986.selenium.tinafw.tasks;

import me.alb_i986.selenium.tinafw.domain.WebUser;

import org.apache.log4j.Logger;

/**
 * Abstract WebTask responsible for knowing the 'who',
 * i.e. what user is going to perform the task.
 */
public abstract class BaseWebTask implements WebTask {
	
	protected static final Logger logger = Logger.getLogger(WebTask.class);
	
	protected WebUser user;
	
	public WebTask setUser(WebUser user) {
		if(user == null)
			throw new IllegalArgumentException("user is null");
		this.user = user;
		return this;
	}

	public WebUser getUser() {
		return user;
	}

}
