package me.alb_i986.selenium.tinafw.sample.tasks;

import me.alb_i986.selenium.tinafw.pages.LoadablePage;
import me.alb_i986.selenium.tinafw.sample.pages.MyAboutMePage;
import me.alb_i986.selenium.tinafw.tasks.NavigationWebTask;

/**
 * Navigate to my about.me page.
 */
public class OnMyAboutMePage extends NavigationWebTask {

	@SuppressWarnings("unchecked")
	@Override
	public <T extends LoadablePage> Class<T> getPageClassToLoad() {
		return (Class<T>) MyAboutMePage.class;
	}

}
