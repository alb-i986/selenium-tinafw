package me.alb_i986.selenium.tinafw.tests;

import java.io.PrintWriter;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.TakesScreenshot;

/**
 * Encapsulates the logic for generating the HTML of a report.
 * HtmlReportBuilder is designed with a fluent interface to allow clients
 * to build a report by chaining the different parts of the report in
 * <i>any</i> order (e.g.: title, info, screenshot, page source).
 * There is one constrain, though:
 * <ul>
 * <li>{@link #beginReport()} must be called first (and never again)</li>
 * <li>{@link #endReport()} must be called last</li>
 * </ul>
 * <p>
 * It provides syntax highlighting (e.g. for page sources) thanks to
 * <a href="https://code.google.com/p/google-code-prettify/" target="new">
 * google-code-prettify</a>.
 *
 */
public class HtmlReportBuilder {

	protected static final Logger logger = Logger.getLogger(HtmlReportBuilder.class);
	
	private PrintWriter writer;
	
	public HtmlReportBuilder(PrintWriter writer) {
		if(writer == null)
			throw new IllegalArgumentException("writer cannot be null");
		this.writer = writer;
	}

	/**
	 * Begin the report by opening the tags html, head, body, table.
	 * 
	 * @return this
	 */
	public HtmlReportBuilder beginReport() {
	    writer.println("<html><head>");
	    writer.println("<script src=\"https://google-code-prettify.googlecode.com/svn/loader/run_prettify.js\"></script>");
	    writer.println("</head><body>");
	    writer.println("<table border=\"1\"><tbody>");
		return this;
	}

	/**
	 * End the report by closing the tags table, body, html.
	 */
	public void endReport() {
		writer.println("</tbody></table></body></html>");
		writer.close();
	}

	/**
	 * Print the title of the report.
	 * If the given title is null, write an error message in place of the title.
	 * 
	 * @param title
	 * @return this
	 */
	public HtmlReportBuilder title(String title) {
		writer.println("<tr><td>");
		writer.println(title != null ? title : "NO TITLE GIVEN");
		writer.println("</td></tr>");
		return this;
	}

	/**
	 * Print the given page source with pretty syntax highlighting.
	 * If the arg is null, then print an error message in the report.
	 * 
	 * @param pageSource
	 * @return this
	 */
	public HtmlReportBuilder pageSource(String pageSource) {
		writer.println("<tr><td>");
		if(pageSource == null) {
			writer.println("<span style=\"color: red\">The page source is not available."
					+ " Probably the driver was already closed.</span>");
		} else {
			writer.println("<pre class=\"prettyprint\">");
			writer.println(
					StringUtils.replaceEach(pageSource,
							new String[]{"&", "<", ">", "\"", "'", "/"},
							new String[]{"&amp;", "&lt;", "&gt;", "&quot;", "&#x27;", "&#x2F;"})
					);
			writer.println("</pre>");
		}
		writer.println("</td></tr>");
		return this;
	}
	
	/**
	 * Prints a table row containing an img with the screenshot embedded in base64 format.
	 * If the argument is null, an error message will be printed.
	 * 
	 * @param screenshotAsBase64 the screenshot as returned by
	 *        {@link TakesScreenshot#getScreenshotAs(org.openqa.selenium.OutputType)}
	 * 
	 * @see <a href="http://en.wikipedia.org/wiki/Data_URI_scheme#HTML">Data_URI_scheme @ Wikipedia</a>
	 */
	public HtmlReportBuilder screenshot(String screenshotAsBase64) {
		writer.println("<tr><td>");
		if(screenshotAsBase64 == null) {
			writer.println("<span style=\"color: red\">The screenshot is not available."
					+ " Probably the driver was already closed.</span>");
		} else {
			writer.println("<img width=\"400\" height=\"300\" src=\"data:image/png;base64,"
					+ screenshotAsBase64 + "\" />");
		}
		writer.println("</td></tr>");
		return this;
	}

	/**
	 * Print the given &lt;key, value&gt; pair in its own line.
	 * 
	 * @param key
	 * @param value
	 * @return this
	 */
	public HtmlReportBuilder info(String key, String value) {
		writer.println("<tr><td>");
		writer.println("<table>");
		writer.println("<tr>");
		writer.println("<td>" + key + ":</td>");
		writer.println("<td>" + value + "</td>");
		writer.println("</tr>");
		writer.println("</table>");
		writer.println("</td></tr>");
		return this;
	}
	
}
