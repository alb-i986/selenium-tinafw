package me.alb_i986.selenium.tinafw.tests;

/**
 * An object capable of generating a test report.
 * <p>
 * It is designed with a fluent interface allowing clients
 * to build a report with the information they want.
 * It can build a report for just one test or for many.
 *
 * <h3>Example</h3>
 * <pre>{@code
 * String testReport =
 *   testReportBuilder
 *     .reset()
 *     .withTitle("Login Test")
 *     .withProperty("Browser", "Chrome")
 *     .withScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64))
 *     .withPageSource(driver.getPageSource())
 *     .build();
 * }</pre>
 *
 * <h3>Implementation notes</h3>
 * Be careful while implementing the {@code with} methods.
 * Make sure that null parameters are handled explicitly, so that NPEs
 * do not leak. For example, by converting the nulls to empty strings.
 */
public interface TestReportBuilder {

    /**
     * Re-initialize this builder, preparing it to build a new report.
     *
     * @return this
     */
    TestReportBuilder reset();

	/**
	 * Finalize the report and return the String with the whole report built.
	 * <p>
	 * Any subsequent call to this method should return the same String previously built,
     * until this builder is {@link #reset()}.
	 *
	 * @return the report built
	 */
	String build();

	/**
     * Add the given title to the report.
     * Typically, it will be the name of the test.
	 *
	 * @param title
	 * @return this
	 */
	TestReportBuilder withTitle(String title);

	/**
	 * Add the given page source to the report.
	 *
	 * @param pageSource
	 * @return this
	 */
	TestReportBuilder withPageSource(String pageSource);

	/**
     * Add the given screenshot (encoded in base64 format) to the report.
	 *
	 * @param screenshotAsBase64 the screenshot as returned by
	 *        {@link org.openqa.selenium.TakesScreenshot#getScreenshotAs(org.openqa.selenium.OutputType)}
	 *
	 * @see <a href="http://en.wikipedia.org/wiki/Data_URI_scheme#HTML">Data_URI_scheme @ Wikipedia</a>
	 */
	TestReportBuilder withScreenshot(String screenshotAsBase64);

	/**
     * Add the given &lt;key, value&gt; pair to the report.
	 *
	 * @param key
	 * @param value
	 * @return this
	 */
    TestReportBuilder withProperty(String key, String value);

    /**
     * @param e the exception that caused the test to fail.
     * @return this
     */
    TestReportBuilder withStackTrace(Throwable e);

    /**
     * Add the given text to the report.
     *
     * @param text
     * @return this
     */
    TestReportBuilder withText(String text);

}
