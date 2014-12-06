package me.alb_i986.selenium.tinafw.tasks;

import me.alb_i986.selenium.tinafw.ui.LoadablePage;
import me.alb_i986.selenium.tinafw.ui.WebPage;

/**
 * A WebTask implementing a step for navigating to a {@link LoadablePage}.
 */
public abstract class NavigationWebTask extends BaseWebTask {
	
	/**
	 * Navigate to {@link #pageToLoadClass()} and return its instance.
	 * 
	 * @return an instance of {@link #pageToLoadClass()}
	 */
	@Override
	public WebPage run(WebPage previousPage) {
		return browser().browseTo(pageToLoadClass());
	}

	/**
	 * @return the class of the {@link LoadablePage} to load
	 */
	protected abstract <T extends LoadablePage> Class<T> pageToLoadClass();

}
