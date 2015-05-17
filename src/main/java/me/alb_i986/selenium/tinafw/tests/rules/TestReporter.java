package me.alb_i986.selenium.tinafw.tests.rules;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import me.alb_i986.selenium.tinafw.config.Config;
import me.alb_i986.selenium.tinafw.domain.Browser;
import me.alb_i986.selenium.tinafw.tests.TestReportBuilder;
import me.alb_i986.selenium.tinafw.tests.TestHelper;

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

	/**
	 * @see Config#getReportsDir()
	 */
	private static final String REPORTS_DIR = Config.getReportsDir();

	protected static final Logger logger = Logger.getLogger(TestReporter.class);
	
	private Browser browser;
	private TestReportBuilder testReportBuilder;

	public TestReporter(TestReportBuilder testReportBuilder) {
		this.testReportBuilder = testReportBuilder;
	}

	@Override
	protected void failed(Throwable e, Description description) {
		testReportBuilder
				.reset()
				.withTitle(description.getDisplayName())
				.withStackTrace(e);
		if(browser == null) {
			logger.warn("Cannot generate the HTML report: no Browser has been set. "
					+ "Please check that your test sets it by calling "
					+ "`testReporter.setBrowser(user.getBrowser())`");
			testReportBuilder
					.withText("ERROR. The Browser has not been set in TestReporter." +
							" Please make sure that your test sets it." +
							" See SampleWebTest#before.");
		} else {
			testReportBuilder
					.withProperty("Browser",
							this.browser.isOpen() ? this.browser.getType().toString() : "N/A")
					.withScreenshot(TestHelper.getScreenshotAsBase64(this.browser.getWebDriver()))
					.withPageSource(TestHelper.getPageSource(this.browser.getWebDriver()));
		}
		try(PrintWriter writer = createHtmlFile(description)) {
			writer.println(testReportBuilder.build());
		} catch (IOException ioEx) {
			logger.error("Cannot generate the HTML report", ioEx);
			return;
		}
	}

	public void setBrowser(Browser browser) {
		this.browser = browser;
	}

	private PrintWriter createHtmlFile(Description description) throws IOException {
		String testClassName = description.getClassName();
		String testMethodName = description.getMethodName();
		try {
			Files.createDirectory(Paths.get(REPORTS_DIR));
		} catch (FileAlreadyExistsException ignored) {
			// ignored
		}
		Path file = Paths.get(REPORTS_DIR,
				testClassName + "_" + testMethodName + "_" + new SimpleDateFormat("yyyy-MM-dd_HHmmss").format(new Date()) + ".html");
		return new PrintWriter(Files.newBufferedWriter(file, Charset.forName("UTF-8")));
	}

}
