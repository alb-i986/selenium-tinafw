package me.alb_i986.selenium.tinafw.tests.rules;

import me.alb_i986.selenium.tinafw.domain.Browser;
import me.alb_i986.selenium.tinafw.tests.TestReport;
import me.alb_i986.selenium.tinafw.tests.TestReportBuilder;
import me.alb_i986.selenium.tinafw.tests.TestHelper;

import me.alb_i986.selenium.tinafw.tests.TestReportWriter;
import org.apache.log4j.Logger;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

/**
 * TestRule for generating an HTML report with embedded screenshots
 * and page sources for each failure.
 * 
 * @see TestReportBuilder
 *
 */
public class TestReporter extends TestWatcher {

	protected static final Logger logger = Logger.getLogger(TestReporter.class);

	private Browser browser;
	private final TestReportWriter reportWriter;
	private final TestReportBuilder reportBuilder;

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
					.withScreenshot(TestHelper.getScreenshotAsBase64(this.browser.getWebDriver()))
					.withPageSource(TestHelper.getPageSource(this.browser.getWebDriver()));
		}
		String testName = description.getClassName() + "_" + description.getMethodName();
		reportWriter.write(reportBuilder.build(testName));
	}

	public void setBrowser(Browser browser) {
		this.browser = browser;
	}

}
