package me.alb_i986.selenium.tinafw.tasks;

import me.alb_i986.selenium.tinafw.pages.LoadablePage;
import me.alb_i986.selenium.tinafw.pages.Page;

/**
 * A WebTask encapsulating a navigation step.
 */
public abstract class NavigationWebTask extends BaseWebTask {
	
	/**
	 * Navigate to {@link #getPageClassToLoad()} and return its instance.
	 * 
	 * @return an instance of {@link #getPageClassToLoad()}
	 */
	@Override
	public Page run(Page previousPage) {
		return user.getBrowser().browseTo(getPageClassToLoad());
	}

	public abstract <T extends LoadablePage> Class<T> getPageClassToLoad();

}
