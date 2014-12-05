package me.alb_i986.selenium.tinafw.tests.rules;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import me.alb_i986.selenium.tinafw.config.TinafwPropLoader;
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
	 * @see TinafwPropLoader#getReportsDir()
	 */
	private static final String REPORTS_DIR = TinafwPropLoader.getReportsDir();

	protected static final Logger logger = Logger.getLogger(HtmlReporter.class);
	
	private Browser browser;
	private PrintWriter writer;
	
	@Override
	protected void failed(Throwable e, Description description) {
		if(browser == null) {
			logger.warn("Cannot generate the HTML report: no Browser has been set. "
					+ "Please set it in your test by calling "
					+ "`htmlReporter.setBrowser(user.getBrowser())`");
			return;
		}
		if(!browser.isOpen()) {
			logger.warn("Cannot generate the HTML report: the browser has already been closed");
			return;
		}
		try {
			writer = createHtmlFile(description.getClassName(), description.getMethodName());
			new HtmlReportBuilder(writer)
				.beginReport()
				.title(description.getDisplayName())
				.info("Browser", browser.getType().toString())
				.screenshot(TestHelper.getScreenshotAsBase64(browser.getWebDriver()))
				.pageSource(getPageSource(browser.getWebDriver()))
				.endReport()
			;
		} catch (IOException ioEx) {
			logger.error("Cannot generate the HTML report", ioEx);
		}
	}

	public void setBrowser(Browser browser) {
		this.browser = browser;
	}

	/**
	 * @param driver
	 * @return a String with the page source;
	 *         null if a WebDriverException were thrown, or the arg is null
	 */
	private String getPageSource(WebDriver driver) {
		if(driver == null)
			return null;
		try {
			return driver.getPageSource();
		} catch(WebDriverException e) {
			logger.warn("WebDriver#getPageSource failed: " + e.getMessage());
			return null;
		}
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
