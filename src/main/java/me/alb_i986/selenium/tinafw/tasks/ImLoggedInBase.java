package me.alb_i986.selenium.tinafw.tasks;

import me.alb_i986.selenium.tinafw.ui.LoadablePage;
import me.alb_i986.selenium.tinafw.ui.LoginPage;
import me.alb_i986.selenium.tinafw.ui.WebPage;

/**
 * Task implementing a step for navigating to the {@link LoginPage}
 * and logging in by entering username and password.
 * <p>
 * Concrete subclasses need to define the method {@link #loginPageClass()},
 * which is supposed to return a concrete {@link LoginPage} class.
 *
 */
public abstract class ImLoggedInBase extends NavigationWebTask {

	/**
	 * Browse to the login page and do the login.
	 * 
	 * @param noPage this param won't be considered: may be null
	 */
	@Override
	public WebPage run(WebPage noPage) {
		LoginPage<?> loginPage = (LoginPage<?>) super.run(noPage);
		return loginPage.loginAs(user.getUsername(), user.getPassword());
	}
	
	@Override
	@SuppressWarnings("unchecked")
	protected <P extends LoadablePage> Class<P> pageToLoadClass() {
		return (Class<P>) loginPageClass();
	}

	/**
	 * @return the concrete {@link LoginPage} class to browse to
	 */
	protected abstract <P extends LoginPage<?>> Class<P> loginPageClass();

}
