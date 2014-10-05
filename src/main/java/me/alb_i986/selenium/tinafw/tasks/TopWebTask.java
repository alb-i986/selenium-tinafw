package me.alb_i986.selenium.tinafw.tasks;

import static org.junit.Assert.*;

import me.alb_i986.selenium.tinafw.domain.Browser;
import me.alb_i986.selenium.tinafw.pages.Page;

/**
 * The first WebTask in a chain.
 * As such, FirstWebTask has a Browser for starting the navigation,
 * and does not accept a previous page.
 * <p>
 * WARNING.
 * Concrete subclasses need to specialize {@link #doTask(Page)}
 * (i.e. override it and call super first).
 */
public abstract class TopWebTask extends BaseWebTask {
	
	protected Browser browser;

	public TopWebTask(Browser browser) {
		super();
		this.browser = browser;
	}
	
	/**
	 * Assert that previous page is null.
	 * 
	 * @return null
	 */
	@Override
	public Page doTask(Page previousPage) {
		assertNull(previousPage);
		return null;
	}

}
