package me.alb_i986.selenium.tinafw.tests;

import me.alb_i986.selenium.tinafw.tests.rules.BrowserManager;
import me.alb_i986.selenium.tinafw.tests.rules.TestRetrier;
import me.alb_i986.selenium.tinafw.utils.PropertyLoader;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.internal.AssumptionViolatedException;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

/**
 * JunitWebTest provides a few useful JUnit Rules out of the box:
 * <ul>
 * <li>{@link TestRetrier}: retries a failing test</li>
 * <li>{@link BrowserManager}: closes the browsers when a test finishes</li>
 * <li>{@link TestWatcher}: logs the state of the running test (e.g.: "passed", "failed")</li>
 * </ul>
 * <p>
 * In order for the BrowserManager to do its job, each test must
 * register every Browser it opens to the BrowserManager. E.g.:
 * <pre>
 * {@code
 * @Override
 * public void before() {
 * 	super.before();
 * 	user = new User();
 * 	browserManager.registerBrowsers(user.getBrowser());
 * }
 * }
 * <pre>
 */
public abstract class JunitWebTest implements WebTest {
	
	public static final int MAX_EXECUTIONS =
			Integer.parseInt(PropertyLoader.getTinaFwConfig("max_executions"));
	
	protected static final Logger logger = Logger.getLogger(WebTest.class);
	
    @Rule
    public TestRetrier retryRule = new TestRetrier(MAX_EXECUTIONS);

	@Rule
	public BrowserManager browserManager = new BrowserManager();
		
	@Rule
	public TestWatcher watchman = new TestWatcher() {
		
		@Override
		protected void skipped(AssumptionViolatedException e, Description description) {
			super.skipped(e, description);
			logger.error("END test: SKIPPED > " + description.getDisplayName() + " -- cause: " + e);
		}

		@Override
		protected void starting(Description description) {
			super.starting(description);
			logger.info("BEGIN test " + description.getDisplayName());
		}

		@Override
		protected void failed(Throwable e, Description description) {
			super.failed(e, description);
			logger.error("END test: FAILED > " + description.getDisplayName());
		}

		@Override
		protected void succeeded(Description description) {
			super.succeeded(description);
			logger.info("END test: PASSED > " + description.getDisplayName());
		}
	};

	/**
	 * Empty after method.
	 */
	@After
	public void after() {
	}

	/**
	 * Empty before method.
	 */
	@Before
	public void before() {
	}
	
}
