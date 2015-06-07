package me.alb_i986.selenium.tinafw.tests.rules;

import com.google.inject.Inject;
import me.alb_i986.selenium.tinafw.domain.Browser;
import me.alb_i986.selenium.tinafw.tests.TestReportBuilder;

import me.alb_i986.selenium.tinafw.tests.TestReportWriter;
import org.apache.log4j.Logger;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

/**
 * {@link org.junit.rules.TestRule} for generating a test report with embedded screenshots
 * and page sources for each failure.
 * <p>
 * It relies on {@link TestReportBuilder} to generate a test report,
 * and on {@link TestReportWriter} to actually write it.
 */
public class TestReporter extends TestWatcher {

	protected static final Logger logger = Logger.getLogger(TestReporter.class);

	private Browser browser;
	private final TestReportWriter reportWriter;
	private final TestReportBuilder reportBuilder;

	@Inject
	public TestReporter(TestReportBuilder reportBuilder, TestReportWriter reportWriter) {
		this.reportBuilder = reportBuilder;
		this.reportWriter = reportWriter;
	}

	@Override
	protected void failed(Throwable e, Description description) {
		reportBuilder
				.reset()
				.withTitle(description.getDisplayName())
				.withStackTrace(e);
		if(browser == null) {
			logger.warn("Cannot generate the HTML report: no Browser has been set. "
					+ "Please check that your test sets it by calling "
					+ "`testReporter.setBrowser(user.getBrowser())`");
			reportBuilder
					.withText("ERROR. The Browser has not been set in TestReporter." +
							" Please make sure that your test sets it." +
							" See SampleWebTest#before.");
		} else {
			reportBuilder
					.withProperty("Browser",
							this.browser.isOpen() ? this.browser.getType().toString() : "N/A")
					.withScreenshot(getScreenshotAsBase64())
					.withPageSource(getPageSource());
		}
		String testName = description.getClassName() + "_" + description.getMethodName();
		reportWriter.write(reportBuilder.build(testName));
	}

	public void setBrowser(Browser browser) {
		this.browser = browser;
	}


	/**
	 * @return the screenshot;
	 *         if WebDriverException is thrown, return the error message;
	 *         or null, if the driver is null
	 * @see TakesScreenshot#getScreenshotAs(OutputType)
	 */
	protected String getScreenshotAsBase64() {
		WebDriver driver = browser.getWebDriver();
		if(driver == null) {
			return null;
		}
		try {
			return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
		} catch(WebDriverException e) {
			return e.getMessage();
		}
	}

	/**
	 * @return a String with the page source;
	 *         if WebDriverException is thrown, return the error message;
	 *         or null, if the driver is null
	 */
	protected String getPageSource() {
		WebDriver driver = browser.getWebDriver();
		if(driver == null)
			return null;
		try {
			return driver.getPageSource();
		} catch(WebDriverException e) {
			return e.getMessage();
		}
	}
}
