package me.alb_i986.selenium.tinafw.tests.rules;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import me.alb_i986.selenium.tinafw.config.Config;
import me.alb_i986.selenium.tinafw.domain.Browser;
import me.alb_i986.selenium.tinafw.tests.HtmlReportBuilder;
import me.alb_i986.selenium.tinafw.tests.TestHelper;

import org.apache.log4j.Logger;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

/**
 * TestRule for generating an HTML report with embedded screenshots
 * and page sources for each failure.
 * 
 * @see HtmlReportBuilder
 *
 */
public class HtmlReporter extends TestWatcher {

	/**
	 * @see Config#getReportsDir()
	 */
	private static final String REPORTS_DIR = Config.getReportsDir();

	protected static final Logger logger = Logger.getLogger(HtmlReporter.class);
	
	private Browser browser;
	private HtmlReportBuilder htmlReportBuilder;

	public HtmlReporter(HtmlReportBuilder htmlReportBuilder) {
		this.htmlReportBuilder = htmlReportBuilder;
	}

	@Override
	protected void failed(Throwable e, Description description) {
		String htmlReport;
		if(browser == null) {
			logger.warn("Cannot generate the HTML report: no Browser has been set. "
					+ "Please set it in your test by calling "
					+ "`htmlReporter.setBrowser(user.getBrowser())`");

			htmlReport =
					htmlReportBuilder
							.reset()
							.withTitle(description.getDisplayName())
							.withProperty("Browser", "N/A")
							.withText("ERROR: the Browser has not been set in HtmlReporter." +
									" Please make sure that your test sets it." +
									" See SampleWebTest#before.")
							.build();
		} else {
			htmlReport =
					htmlReportBuilder
							.reset()
							.withTitle(description.getDisplayName())
							.withStackTrace(e)
							.withProperty("Browser",
									this.browser.isOpen() ? this.browser.getType().toString() : "N/A")
							.withScreenshot(TestHelper.getScreenshotAsBase64(this.browser.getWebDriver()))
							.withPageSource(TestHelper.getPageSource(this.browser.getWebDriver()))
							.build();
		}
		try(PrintWriter writer = createHtmlFile(description.getClassName(), description.getMethodName())) {
			writer.println(htmlReport);
		} catch (IOException ioEx) {
			logger.error("Cannot generate the HTML report", ioEx);
			return;
		}
	}

	public void setBrowser(Browser browser) {
		this.browser = browser;
	}

	private PrintWriter createHtmlFile(String testClassName, String testMethodName) throws IOException {
		Path file = Paths.get(REPORTS_DIR, testClassName + "_" + testMethodName + ".html");
		try {
			Files.createDirectory(Paths.get(REPORTS_DIR));
		} catch (FileAlreadyExistsException alreadyEx) {
		}
		return new PrintWriter(Files.newBufferedWriter(file, Charset.forName("UTF-8")));
	}

}
