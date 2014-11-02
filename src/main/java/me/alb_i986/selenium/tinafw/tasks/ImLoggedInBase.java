package me.alb_i986.selenium.tinafw.tasks;

import me.alb_i986.selenium.tinafw.pages.LoginPage;
import me.alb_i986.selenium.tinafw.pages.Page;

/**
 * Concrete subclasses must define the method `getPageClassToLoad`
 * which needs to return a concrete {@link LoginPage}.
 *
 */
public abstract class ImLoggedInBase extends NavigationWebTask {

	/**
	 * Browse to the login page and do the login.
	 * 
	 * @param noPage this param won't be considered: may be null
	 */
	@SuppressWarnings("rawtypes")
	public Page run(Page noPage) {
		Page loginPage = super.run(noPage);
		return 
			((LoginPage) loginPage)
				.loginAs(user.getUsername(), user.getPassword())
			;
	}
	
}
