package me.alb_i986.selenium.tinafw.tasks;

import org.junit.Assert;

import me.alb_i986.selenium.tinafw.pages.Page;

/**
 * A WebTask mid chain must be given a previous page.
 * <p>
 * WARNING.
 * Concrete subclasses need to specialize {@link #doTask(Page)}
 * (i.e. override it and call super first).
 */
public abstract class MidChainWT extends BaseWebTask {

	/**
	 * Assert that previous page is not null.
	 * 
	 * @return null
	 */
	@Override
	public Page doTask(Page previousPage) {
		Assert.assertNotNull(previousPage);
		return null;
	}

}
