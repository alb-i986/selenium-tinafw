package me.alb_i986.selenium.tinafw.tests.rules;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.rules.ExternalResource;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import me.alb_i986.selenium.tinafw.domain.Browser;

/**
 * JUnit Rule responsible for closing all registered browsers
 * as soon as a test terminates.
 * <p>
 * It can be configured <i>not</i> to close the browsers, which is
 * supposed to be a "debug mode".
 * Although it may sound silly, here is a use case.
 * You have your suite of tests that runs in production.
 * If some tests fail you will want to run them locally,
 * and you don't want the browser to close at the end, in order to
 * inspect the state of the SUT at the moment of the failures.
 * But possibly you don't want to touch any lines of code.
 * Since you can customize the behavior of a BrowserManager by injecting a boolean,
 * you can store a setting in a config file to act like a switch
 * of the
 * <pre>{@code
 * public class MyJUnitWebTest &#123;
 *
 *     &#064;Rule
 *     public BrowserManager bm = new BrowserManager(Config.getBMMode());
 *
 *     ...
 * &#125;
 * }</pre>
 * With this, you just need to change the value of the setting, in order to
 * debug any test.
 */
public class BrowserManager extends ExternalResource {

	protected static final Logger logger = LogManager.getLogger(BrowserManager.class);
	
	protected Set<Browser> registeredBrowsers = new HashSet<>();
	private Mode mode;

	/**
	 * Default constructor: create a BrowserManager that <i>does</i>
	 * close the registered browsers.
	 */
	public BrowserManager() {
		this(Mode.DEFAULT);
	}

	/**
	 * Allows customizing the behavior.
	 *
	 * @param mode if true, the browsers will <i>not</i> be closed
	 */
	public BrowserManager(Mode mode) {
		if(mode == null) {
			throw new IllegalArgumentException("null mode");
		}
		this.mode = mode;
	}

	@Override
	protected void after() {
		if(mode == Mode.DO_NOT_CLOSE_BROWSERS) {
			return;
		}
		logger.debug("Closing all registered browsers (" + registeredBrowsers.size() + ")");
		for (Browser browser : registeredBrowsers) {
			browser.close();
		}
	}

	public void registerBrowsers(Browser... browsers) {
		this.registeredBrowsers.addAll(Arrays.asList(browsers));
	}

	public void registerBrowser(Browser browser) {
		this.registeredBrowsers.add(browser);
	}

	public enum Mode {
		DEFAULT,
		DO_NOT_CLOSE_BROWSERS,
	}
}
