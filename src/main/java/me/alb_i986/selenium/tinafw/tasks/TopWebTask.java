package me.alb_i986.selenium.tinafw.tasks;

import org.junit.Assert;

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
public abstract class TopWebTask extends WebTask {
	
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
		Assert.assertNull(previousPage);
		return null;
	}

	/**
	 * @return always true, as this task does not require any previous page.
	 */
	@Override
	public boolean requirePage(Page previousPage) {
		return true;
	}

}
