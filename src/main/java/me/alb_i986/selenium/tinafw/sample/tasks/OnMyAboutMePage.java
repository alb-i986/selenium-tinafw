package me.alb_i986.selenium.tinafw.sample.tasks;

import me.alb_i986.selenium.tinafw.sample.ui.MyAboutMePage;
import me.alb_i986.selenium.tinafw.tasks.NavigationWebTask;
import me.alb_i986.selenium.tinafw.ui.LoadablePage;

/**
 * Navigate to my about.me page.
 */
public class OnMyAboutMePage extends NavigationWebTask {

	@SuppressWarnings("unchecked")
	@Override
	public <T extends LoadablePage> Class<T> pageToLoadClass() {
		return (Class<T>) MyAboutMePage.class;
	}

}
