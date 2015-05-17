package me.alb_i986.selenium.tinafw.tests;

/**
 * An object capable of generating the HTML report for a single test.
 * <p>
 * It is designed with a fluent interface allowing clients
 * to build a report with the information they want.
 * <p>
 * A typical usage may be e.g.:
 * <pre>{@code
 * String htmlReport =
 *   htmlReporterBuilder
 *     .reset()
 *     .withTitle("Login Test")
 *     .withProperty("Browser", "Chrome")
 *     .withScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64))
 *     .withPageSource(driver.getPageSource())
 *     .build();
 * }</pre>
 */
public interface HtmlReportBuilder {

    /**
     * Re-initialize this builder, preparing it to build a new report.
     *
     * @return this
     */
    HtmlReportBuilder reset();

	/**
	 * Finalize the report and return the String with the whole HTML built.
	 * <p>
	 * Any subsequent call to this method should return the same String previously built,
     * until this builder is {@link #reset()}.
	 *
	 * @return the HTML report built
	 */
	String build();

	/**
     * Add the given title to the report.
     * Typically, it will be the name of the test.
	 *
	 * @param title
	 * @return this
	 */
	HtmlReportBuilder withTitle(String title);

	/**
	 * Add the given page source to the report.
	 *
	 * @param pageSource
	 * @return this
	 */
	HtmlReportBuilder withPageSource(String pageSource);

	/**
     * Add the given screenshot (encoded in base64 format) to the report.
	 *
	 * @param screenshotAsBase64 the screenshot as returned by
	 *        {@link org.openqa.selenium.TakesScreenshot#getScreenshotAs(org.openqa.selenium.OutputType)}
	 *
	 * @see <a href="http://en.wikipedia.org/wiki/Data_URI_scheme#HTML">Data_URI_scheme @ Wikipedia</a>
	 */
	HtmlReportBuilder withScreenshot(String screenshotAsBase64);

	/**
     * Add the given &lt;key, value&gt; pair to the report.
	 *
	 * @param key
	 * @param value
	 * @return this
	 */
    HtmlReportBuilder withProperty(String key, String value);

    /**
     * @param e the exception that caused the test to fail.
     * @return this
     */
    HtmlReportBuilder withStackTrace(Throwable e);

    /**
     * Add the given text to the report.
     *
     * @param text
     * @return this
     */
    HtmlReportBuilder withText(String text);

}
