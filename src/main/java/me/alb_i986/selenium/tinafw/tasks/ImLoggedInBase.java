package me.alb_i986.selenium.tinafw.tasks;

import me.alb_i986.selenium.tinafw.pages.LoginPage;
import me.alb_i986.selenium.tinafw.pages.Page;
import me.alb_i986.selenium.tinafw.tasks.BaseWebTask;

public abstract class ImLoggedInBase extends BaseWebTask {

	/**
	 * Browse to the login page and do the login.
	 * 
	 * @param noPage this param won't be considered: may be null
	 */
	public Page run(Page noPage) {
		return
			user.getBrowser().browseTo(getLoginPageClass())
				.loginAs(user.getUsername(), user.getPassword())
			;
	}
	
	public abstract Class<? extends LoginPage> getLoginPageClass();

}
