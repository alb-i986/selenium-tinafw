package me.alb_i986.selenium.tinafw.tests;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.TakesScreenshot;

/**
 * A {@link TestReportBuilder} that generates HTML reports.
 * Each {@code with} method appends a new line (a table row) to the report.
 * <p>
 * It features syntax highlighting (e.g. for page sources), provided by
 * <a href="https://code.google.com/p/google-code-prettify/" target="new">google-code-prettify</a>.
 */
public class HtmlTestReportBuilder implements TestReportBuilder {

	protected StringBuilder builder;
	protected boolean built;

	public HtmlTestReportBuilder() {
		reset();
	}

	/**
	 * Reset the state of this builder, and start a new report.
	 *
	 * @return this
	 */
	@Override
	public TestReportBuilder reset() {
		builder = new StringBuilder();
		built = false;
		appendHeader();
		return this;
	}

	/**
	 * End a report by closing the tags table, body, html,
	 * and finally return the String with the whole HTML built.
	 * <p>
	 * Any subsequent call to this method will return the same String previously built.
	 *
	 * @return the HTML report built
	 */
	@Override
	public String build() {
		if(!built) {
			appendFooter();
			built = true;
		}
		return builder.toString();
	}

	/**
	 * Add a table row with the given title to the report.
	 *
	 * @param title
	 * @return this
	 */
	@Override
	public TestReportBuilder withTitle(String title) {
		if(title == null) {
			trWithErrMsg("Title N/A");
		} else {
			tr("<h1>" + title + "</h1>");
		}
		return this;
	}

	/**
	 * Add a table row with the given page source to the report,
	 * with syntax highlighting.
	 *
	 * @param pageSource
	 * @return this
	 */
	@Override
	public TestReportBuilder withPageSource(String pageSource) {
		if(pageSource == null) {
			trWithErrMsg("Page source not available. Probably the browser was closed.");
		} else {
			tr("<pre class=\"prettyprint\">" + StringEscapeUtils.escapeHtml4(pageSource) +
					"</pre>");
		}
		return this;
	}

	/**
	 * Add a table row containing an img with the given screenshot embedded.
	 * If the argument is null, a {@code span} with an error message will be appended instead.
	 * 
	 * @param screenshotAsBase64 the screenshot as returned by
	 *        {@link TakesScreenshot#getScreenshotAs(org.openqa.selenium.OutputType)}
	 * 
	 * @see <a href="http://en.wikipedia.org/wiki/Data_URI_scheme#HTML">Data_URI_scheme @ Wikipedia</a>
	 */
	@Override
	public TestReportBuilder withScreenshot(String screenshotAsBase64) {
		if(screenshotAsBase64 == null) {
			trWithErrMsg("Screenshot not available. Probably the browser was closed.");
		} else {
			tr("<img width=\"400\" height=\"300\" src=\"data:image/png;base64," +
					screenshotAsBase64 + "\" />");
		}
		return this;
	}

	/**
	 * Add a table row with the given &lt;key, value&gt; pair.
	 * 
	 * @param key
	 * @param value
	 * @return this
	 */
	@Override
	public TestReportBuilder withProperty(String key, String value) {
		tr(key != null ? key : "", value != null ? value : "");
		return this;
	}

	@Override
	public TestReportBuilder withStackTrace(Throwable e) {
		if(e == null) {
			trWithErrMsg("Null throwable");
		} else {
			tr(StringEscapeUtils.escapeHtml4(ExceptionUtils.getStackTrace(e)));
		}
		return this;
	}

	@Override
	public TestReportBuilder withText(String text) {
		tr(text != null ? text : "");
		return this;
	}

	/**
	 * Append a table row with the args as columns.
	 */
	private void tr(String... html) {
		builder.append("<tr><td>");
		builder.append("<table><tr>");
		for (String column : html) {
			builder.append("<td>");
			builder.append(column);
			builder.append("</td>");
		}
		builder.append("</tr></table>");
		builder.append("</td></tr>");
		builder.append("\n");
	}

	private void trWithErrMsg(String msg) {
		tr("<span style=\"color: red\">" + msg + "</span>");
	}

	private void appendHeader() {
		builder.append("<html>\n<head>\n");
		builder.append("<script src=\"https://google-code-prettify.googlecode.com/svn/loader/run_prettify.js\"></script>\n");
		builder.append("</head>\n<body>\n");
		builder.append("<table border=\"1\"><tbody>\n");
	}

	/**
	 * Close the tags opened by {@link #appendHeader()}.
	 */
	private void appendFooter() {
		builder.append("</tbody></table></body></html>");
	}
}