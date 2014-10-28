package me.alb_i986.selenium.tinafw.tests;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import me.alb_i986.selenium.tinafw.domain.SupportedBrowser;
import me.alb_i986.selenium.tinafw.tests.rules.BrowserManager;
import me.alb_i986.selenium.tinafw.tests.rules.HtmlReporter;
import me.alb_i986.selenium.tinafw.tests.rules.TestLogger;
import me.alb_i986.selenium.tinafw.tests.rules.TestRetrier;
import me.alb_i986.selenium.tinafw.utils.PropertyLoader;

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

/**
 * JunitWebTest provides a few useful JUnit Rules out of the box:
 * <ul>
 * <li>{@link TestRetrier}: retries a failing test</li>
 * <li>{@link BrowserManager}: closes the registered browsers as soon as a test finishes</li>
 * <li>{@link HtmlReporter}: generated an HTML report with screenshots</li>
 * <li>{@link TestWatcher}: logs the state of the running test (e.g.: "passed", "failed")</li>
 * </ul>
 * <p>
 * In order for the BrowserManager to do its job, each test must
 * register every Browser it opens to the BrowserManager. E.g.:
 * <pre>
 * {@code
 * public void before() {
 * 	super.before();
 * 	user = new User();
 * 	browserManager.registerBrowsers(user.getBrowser());
 * }
 * }
 * </pre>
 */
@RunWith(Parameterized.class)
public abstract class JunitWebTest implements WebTest {
	
	public static final int MAX_EXECUTIONS =
			Integer.parseInt(PropertyLoader.getTinaFwConfig("max_executions"));
	
	protected static final Logger logger = Logger.getLogger(JunitWebTest.class);
    
    protected TestRetrier retryRule = new TestRetrier(MAX_EXECUTIONS);
    protected BrowserManager browserManager = new BrowserManager();
    protected HtmlReporter htmlReporter = new HtmlReporter();
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
	 * Configurable via the property "tinafw.browsers".
	 * 
	 * @throws IllegalArgumentException if the property 'tinafw.browsers' is not valid,
	 *         i.e. its value cannot be converted to {@link SupportedBrowser},
	 *         or there are no browsers specified 
	 */
	@Parameters
	public static Collection<Object[]> browsers() {
		String[] browserNames = PropertyLoader.getTinaFwConfig("browsers").split("([,; ]+)");
		if(browserNames.length == 0)
			throw new IllegalArgumentException("no browsers specified in the property 'tinafw.browsers'");
		List<SupportedBrowser> browsers = new ArrayList<>();
		for (String browserName : browserNames) {
			browsers.add(SupportedBrowser.valueOf(browserName.toUpperCase()));
		}
		// convert to a set => no duplicates
		Set<SupportedBrowser> browsersSet = EnumSet.copyOf(browsers);
		
		List<Object[]> data = new ArrayList<>();
		for (SupportedBrowser browser : browsersSet) {
			data.add(new Object[] {browser});
		}
		return data;
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
