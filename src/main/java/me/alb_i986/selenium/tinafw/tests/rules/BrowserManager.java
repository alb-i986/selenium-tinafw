package me.alb_i986.selenium.tinafw.tests.rules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.alb_i986.selenium.tinafw.domain.Browser;
import me.alb_i986.selenium.tinafw.utils.PropertyLoader;

import org.apache.log4j.Logger;
import org.junit.rules.ExternalResource;

/**
 * JUnit Rule responsible for closing all registered browsers
 * as soon as a test finishes.
 * <p>
 * It is recommended NOT to have tests explicitly close their browsers,
 * otherwise some other rules may be prevented from doing their job, e.g.
 * {@link HtmlReporter}.
 * 
 */
public class BrowserManager extends ExternalResource {
	
	public static final boolean KEEP_BROWSERS_OPEN_ON_EXIT = Boolean.parseBoolean(
			PropertyLoader.getTinaFwConfig("keep_browsers_open"));

	protected static final Logger logger = Logger.getLogger(BrowserManager.class);
	
	private List<Browser> registeredBrowsers = new ArrayList<>();
	
	@Override
	protected void after() {
		if(!KEEP_BROWSERS_OPEN_ON_EXIT) {
			logger.debug("Closing all registered browsers (" + registeredBrowsers.size() + ")");
			for (Browser browser : registeredBrowsers) {
				browser.close();
			}
		}
	}

	public void registerBrowsers(Browser... browsers) {
		this.registeredBrowsers.addAll(Arrays.asList(browsers));
	}

	public void registerBrowser(Browser browser) {
		this.registeredBrowsers.add(browser);
	}
	
}
