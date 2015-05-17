package me.alb_i986.selenium.tinafw.tests;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.TakesScreenshot;

/**
 * An HtmlReportBuilder that uses HTML tables.
 * Each {@code with} method appends a table row.
 * <p>
 * It also features syntax highlighting (e.g. for page sources), provided by
 * <a href="https://code.google.com/p/google-code-prettify/" target="new">google-code-prettify</a>.
 */
public class TableHtmlReportBuilder implements HtmlReportBuilder {

	protected StringBuilder builder;
	protected boolean built;

	public TableHtmlReportBuilder() {
		reset();
	}

	/**
	 * Reset the state of this builder, and start a new report.
	 *
	 * @return this
	 */
	@Override
	public HtmlReportBuilder reset() {
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
	public HtmlReportBuilder withTitle(String title) {
		tr(title != null ? title : "N/A");
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
	public HtmlReportBuilder withPageSource(String pageSource) {
		String html;
		if(pageSource == null) {
			html = "<span style=\"color: red\">" +
					"The page source is not available. Probably the driver was already closed." +
					"</span>";
		} else {
			html = "<pre class=\"prettyprint\">" +
					StringEscapeUtils.escapeHtml4(pageSource) +
					"</pre>";
		}
		tr(html);
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
	public HtmlReportBuilder withScreenshot(String screenshotAsBase64) {
		String html;
		if(screenshotAsBase64 == null) {
			html = "<span style=\"color: red\">" +
					"The screenshot is not available. Probably the driver was already closed." +
					"</span>";
		} else {
			html = "<img width=\"400\" height=\"300\" src=\"data:image/png;base64," +
					screenshotAsBase64 +
					"\" />";
		}
		tr(html);
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
	public HtmlReportBuilder withProperty(String key, String value) {
		tr(key != null ? key : "", value != null ? value : "");
		return this;
	}

	@Override
	public HtmlReportBuilder withStackTrace(Throwable e) {
		tr(StringEscapeUtils.escapeHtml4(ExceptionUtils.getStackTrace(e)));
		return this;
	}

	@Override
	public HtmlReportBuilder withText(String text) {
		tr(text);
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
