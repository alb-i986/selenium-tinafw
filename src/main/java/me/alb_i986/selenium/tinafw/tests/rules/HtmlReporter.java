package me.alb_i986.selenium.tinafw.tests.rules;

import me.alb_i986.selenium.tinafw.domain.Browser;
import me.alb_i986.selenium.tinafw.tests.HtmlReportBuilder;
import me.alb_i986.selenium.tinafw.tests.TestHelper;

import org.apache.log4j.Logger;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;

/**
 * TestRule for generating an HTML report with embedded screenshots
 * and page sources for each failure.
 *
 */
public class HtmlReporter extends TestWatcher {

	protected static final Logger logger = Logger.getLogger(HtmlReporter.class);
	
	private Browser browser;
	
	@Override
	protected void failed(Throwable e, Description description) {
		if(browser == null) {
			logger.warn("Cannot generate HTML report: no Browser has been set. "
					+ "Please set it in your test with "
					+ "`htmlReporter.setBrowser(user.getBrowser)`");
			return;
		}
		if(browser.getWebDriver() == null) {
			logger.warn("Cannot generate HTML report: WebDriver is null");
			return;
		}
		new HtmlReportBuilder(description.getMethodName())
			.beginReport()
			.testName()
			.screenshot(TestHelper.getScreenshotAs(OutputType.BASE64, browser.getWebDriver()))
			.pageSource(browser.getWebDriver().getPageSource())
			.endReport()
		;
	}


	public void setBrowser(Browser browser) {
		this.browser = browser;
	}
}
