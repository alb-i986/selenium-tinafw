package me.alb_i986.selenium.tinafw.tasks;

import me.alb_i986.selenium.tinafw.ui.LoadablePage;
import me.alb_i986.selenium.tinafw.ui.WebPage;

/**
 * A WebTask encapsulating a manual navigation step.
 * Manual means that the page cannot be loaded automatically by
 * {@link LoadablePage#load(Class, org.openqa.selenium.WebDriver)}
 * because the target URL requires some parameters (e.g. "/item?id=999"),
 * which are not known at compile-time.
 * <p>
 * Those parameters should be modeled as fields.
 * Concrete subclasses only need to define {@link #targetUrl()}
 * and {@link #landingPage()}. That's it.
 * <p>
 * If needed, they can also specialize {@link #run(WebPage)}.
 * For example, they may need to first validate the fields:
 * <pre>
 * {@code
 * private String articleId;
 * 
 * public WebPage run(WebPage previousPage) {
 *    if(articleId == null || articleId.equals(""))
 *       throw new IllegalArgumentException();
 *       return super.run(previousPage);
 *    }
 * }
 * }
 * </pre>
 * 
 */
public abstract class ManualNavigationWebTask extends BaseWebTask {
	
	/**
	 * Navigate to the relative URL {@link #targetUrl()}
	 * and return the page {@link #landingPage()}.
	 * 
	 * @return {@link #landingPage()}
	 */
	@Override
	public WebPage run(WebPage previousPage) {
		browser().browseTo(targetUrl());
		return landingPage();
	}

	/**
	 * @return the <b>relative</b> URL to navigate to
	 */
	protected abstract String targetUrl();

	/**
	 * @return the page found at {@link #targetUrl()}
	 */
	protected abstract WebPage landingPage();

}
