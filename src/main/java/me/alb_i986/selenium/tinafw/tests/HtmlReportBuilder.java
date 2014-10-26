package me.alb_i986.selenium.tinafw.tests;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.TakesScreenshot;

/**
 * Encapsulates the logic for generating the HTML of a report.
 * <p>
 * The syntax highlighting for the page sources is provided by
 * <a href="https://code.google.com/p/google-code-prettify/" target="new">
 * google-code-prettify</a>.
 *
 */
public class HtmlReportBuilder {

	public static final String HTML_REPORT_FILENAME = "target/failing-tests-report_%s.html";

	protected static final Logger logger = Logger.getLogger(HtmlReportBuilder.class);
	
	private String testMethodName;
	private PrintWriter writer;

	public HtmlReportBuilder(String methodName) {
		this.testMethodName = methodName;
	}

	public HtmlReportBuilder beginReport() {
		Path file = Paths.get(String.format(HTML_REPORT_FILENAME, testMethodName));
		try {
			writer = new PrintWriter(Files.newBufferedWriter(file, Charset.forName("UTF-8")));
		    writer.println("<html><head>");
		    writer.println("<script src=\"https://google-code-prettify.googlecode.com/svn/loader/run_prettify.js\"></script>");
		    writer.println("</head><body>");
		    writer.println("<table><tbody>");
		} catch (IOException e) {
			logger.error("Can't create file", e);
		}
		return this;
	}

	public void endReport() {
		assertBeginHasRun();
		writer.println("</tbody></table></body></html>");
		writer.close();
	}

	public HtmlReportBuilder testName() {
		assertBeginHasRun();
		writer.println("<tr><td>");
		writer.println("Test " + testMethodName);
		writer.println("</td></tr>");
		return this;
	}

	public HtmlReportBuilder pageSource(String pageSource) {
		assertBeginHasRun();
		writer.println("<tr><td>");
		writer.println("<pre class=\"prettyprint\">");
		writer.println(
			StringUtils.replaceEach(pageSource,
		        new String[]{"&", "<", ">", "\"", "'", "/"},
		        new String[]{"&amp;", "&lt;", "&gt;", "&quot;", "&#x27;", "&#x2F;"})
		);
		writer.println("</pre>");
		writer.println("</td></tr>");
		return this;
	}
	
	/**
	 * @param screenshotAsBase64 the screenshot as returned by {@link TakesScreenshot#getScreenshotAs(org.openqa.selenium.OutputType)}
	 * @return the HTML with an img with the screenshot embedded in base64 format
	 * @see <a href="http://en.wikipedia.org/wiki/Data_URI_scheme#HTML">Data_URI_scheme @ Wikipedia</a>
	 */
	public HtmlReportBuilder screenshot(String screenshotAsBase64) {
		assertBeginHasRun();
		writer.println("<tr><td>");
		writer.println("<img width=\"400\" height=\"300\" src=\"data:image/png;base64,"
				+ screenshotAsBase64 + "\" />");
		writer.println("</td></tr>");
		return this;
	}
	
	private void assertBeginHasRun() {
		if(writer == null)
			throw new IllegalStateException("begin() must be called first.");
	}
	
}
