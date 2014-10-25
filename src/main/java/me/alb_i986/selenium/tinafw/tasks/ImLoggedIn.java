package me.alb_i986.selenium.tinafw.tasks;

import me.alb_i986.selenium.tinafw.pages.LoginPage;
import me.alb_i986.selenium.tinafw.pages.Page;
import me.alb_i986.selenium.tinafw.tasks.BaseWebTask;

public class ImLoggedIn extends BaseWebTask {

	/**
	 * Browse to the login page and do the login.
	 * 
	 * @param noPage this param won't be considered: may be null
	 */
	public Page doTask(Page noPage) {
		return
			user.getBrowser().browseTo(LoginPage.class)
				.loginAs(user.getUsername(), user.getPassword())
			;
	}

}
