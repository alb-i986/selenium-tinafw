package me.alb_i986.selenium.tinafw.tests;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import me.alb_i986.selenium.tinafw.config.Config;
import me.alb_i986.selenium.tinafw.config.TinafwGuiceModule;
import me.alb_i986.selenium.tinafw.domain.SupportedBrowser;
import me.alb_i986.selenium.tinafw.domain.WebUser;
import me.alb_i986.selenium.tinafw.tests.rules.BrowserManager;
import me.alb_i986.selenium.tinafw.tests.rules.HtmlReporter;
import me.alb_i986.selenium.tinafw.tests.rules.TestLogger;
import me.alb_i986.selenium.tinafw.tests.rules.TestRetrier;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import static me.alb_i986.selenium.tinafw.tests.rules.BrowserManager.*;

/**
 * JunitWebTest is supposed to be the base class for client's test classes.
 * It provides some services allowing clients to focus on the business,
 * i.e. writing test logic.
 *
 * <h2>Services provided</h2>
 *
 * <h3>JUnit rules</h3>
 * <ul>
 * <li>{@link TestRetrier}: retries a failing test</li>
 * <li>{@link BrowserManager}: closes the registered browsers as soon as a test finishes</li>
 * <li>{@link HtmlReporter}: generates an HTML report with screenshots</li>
 * <li>{@link TestWatcher}: logs the state of the running test (e.g.: "passed", "failed")</li>
 * </ul>
 *
 * <h3>Parametrized</h3>
 * JunitWebTest leverages JUnit's {@link Parameterized} runner to provide the ability
 * to run each test on many different browsers.
 * These parameters are taken from the config (see {@link Config#getBrowsers()}).
 *
 * <h2>Usage</h2>
 *
 * <h3>Register browsers to BrowserManager</h3>
 * Concrete test classes need to register to BrowserManager every Browser
 * that is used by a test.
 * For example, in a Before method:
 * <pre>{@code
 * &#064;Before
 * public void before() {
 * 	user = newUser();
 * 	browserManager.registerBrowsers(user.getBrowser());
 * }
 * }</pre>
 * Here, we are assuming that each test needs only one user.
 *
 * <h3>Creating users</h3>
 * In order to create a WebUser, you should use {@link #newUser()},
 * unless you want a user with a custom Browser injected.
 * <p>
 * {@link #newUser()} relies on Guice to inject a Browser.
 * It's a nicer form than writing {@code INJECTOR.createInstance(...)}.
 */
@RunWith(Parameterized.class)
public abstract class JunitWebTest implements WebTest {
	
	/**
	 * @see Config#getMaxExecutions()
	 */
	public static final int MAX_EXECUTIONS = Config.getMaxExecutions();
	
	protected static final Logger logger = Logger.getLogger(JunitWebTest.class);
	private static final Injector INJECTOR = Guice.createInjector(Stage.PRODUCTION,
			new TinafwGuiceModule());
    
    protected TestRetrier retryRule = new TestRetrier(MAX_EXECUTIONS);
    protected BrowserManager browserManager = new BrowserManager(Config.getBrowserManagerMode());
    protected HtmlReporter htmlReporter = new HtmlReporter(new TableHtmlReportBuilder());
	protected TestWatcher testLogger = new TestLogger();

	@Rule
	public TestRule ruleChain =
		RuleChain
			.outerRule(testLogger)
			.around(browserManager)
			.around(htmlReporter)
			.around(retryRule)
		;
	
	@Parameter
	public SupportedBrowser browserType;

	/**
	 * Provides the test parameters,
	 * i.e. the browsers to run the tests with.
	 * 
	 * @see Config#getBrowsers()
	 */
	@Parameters(name = "{0}")
	public static Iterable<? extends Object> parameters() {
		return Config.getBrowsers();
	}

	/**
	 * @return a new instance of WebUser, as returned by Guice's {@link Injector}.
	 */
	protected static WebUser newUser() {
		return INJECTOR.getInstance(WebUser.class);
	}
    
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
