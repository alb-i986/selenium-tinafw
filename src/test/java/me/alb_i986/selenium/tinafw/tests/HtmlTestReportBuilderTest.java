package me.alb_i986.selenium.tinafw.tests;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class HtmlTestReportBuilderTest {

    private static final String EXPECTED_HEADER = "<html>\n" +
            "<head>\n" +
            "<script src=\"https://google-code-prettify.googlecode.com/svn/loader/run_prettify.js\"></script>\n" +
            "</head>\n" +
            "<body>\n" +
            "<table border=\"1\"><tbody>\n";

    private static final String EXPECTED_FOOTER = "</tbody></table></body></html>";

    private HtmlTestReportBuilderForTest out;

    @Before
    public void before() {
        out = new HtmlTestReportBuilderForTest();
    }

    @Test
    public void givenBuiltWhenReset() {
        // given built
        out.setBuilt(true);

        // when reset
        out.reset();

        // then
        assertFalse(out.isBuilt());
        assertThat(out.getStringBuilt(), allOf(
                startsWith("<html>"), endsWith("<tbody>\n")));
    }

    @Test
    public void givenResetWhenBuild() {
        // given reset
        out.reset();

        // when build
        String testName = "SomeTest";
        TestReport report = out.build(testName);

        //then
        assertThat(report.getReport(), allOf(startsWith("<html>"), endsWith("</html>")));
        assertTrue(out.isBuilt());
        assertEquals(report.getReport(), out.build(null).getReport());
    }

    @Test
    public void withTitle() {
        // given reset
        out.reset();

        // when build with title
        TestReport report = out.withTitle("my test").build(null);
        String actualHtml = report.getReport();

        //then
        String expectedHtml = EXPECTED_HEADER +
                "<tr><td><table><tr><td><h1>my test</h1></td></tr></table></td></tr>\n" +
                EXPECTED_FOOTER;
        assertEquals(expectedHtml, actualHtml);
    }

    @Test
    public void withProperty() {
        // given reset
        out.reset();

        // when build with prop
        TestReport report = out.withProperty("Browser", "Chrome").build(null);
        String actualHtml = report.getReport();

        //then
        String expectedHtml = EXPECTED_HEADER +
                "<tr><td><table><tr><td>Browser</td><td>Chrome</td></tr></table></td></tr>\n" +
                EXPECTED_FOOTER;
        assertEquals(expectedHtml, actualHtml);
    }

    @Test
    public void withPageSource() {
        // given reset
        out.reset();

        // when build with page source
        TestReport report = out.withPageSource("<html><body><a href=\"http://www.google.com\">link to google</a></body>").build(null);
        String actualHtml = report.getReport();

        //then
        String expectedHtml = EXPECTED_HEADER +
                "<tr><td><table><tr><td><div style=\"overflow: auto; height: 500px\"><pre class=\"prettyprint\">&lt;html&gt;&lt;body&gt;&lt;a href=&quot;http://www.google.com&quot;&gt;link to google&lt;/a&gt;&lt;/body&gt;</pre></div></td></tr></table></td></tr>\n" +
                EXPECTED_FOOTER;
        assertEquals(expectedHtml, actualHtml);
    }

    @Test
    public void withScreenshot() {
        // given reset
        out.reset();

        // when build with screenshot
        TestReport report = out.withScreenshot("AsDaSd").build(null);
        String actualHtml = report.getReport();

        //then
        String expectedHtml = EXPECTED_HEADER +
                "<tr><td><table><tr><td><img width=\"400\" height=\"300\" src=\"data:image/png;base64,AsDaSd\" /></td></tr></table></td></tr>\n" +
                EXPECTED_FOOTER;
        assertEquals(expectedHtml, actualHtml);
    }

    @Test
    public void withText() {
        // given reset
        out.reset();

        // when build with text
        TestReport report = out.withText("asd asd asd").build(null);
        String actualHtml = report.getReport();

        //then
        String expectedHtml = EXPECTED_HEADER +
                "<tr><td><table><tr><td>asd asd asd</td></tr></table></td></tr>\n" +
                EXPECTED_FOOTER;
        assertEquals(expectedHtml, actualHtml);
    }

    @Test
    public void withStackTrace() {
        // given reset
        out.reset();

        // when build with exception
        Throwable e = new RuntimeException("outer", new RuntimeException("inner"));
        TestReport report = out.withStackTrace(e).build(null);
        String actualHtml = report.getReport();

        //then
        String partialExpectedHtml = "<tr><td><table><tr><td>java.lang.RuntimeException: outer\n";
        assertThat(actualHtml, containsString(partialExpectedHtml));
    }

    /**
     * http://xunitpatterns.com/Test-Specific%20Subclass.html
     */
    static class HtmlTestReportBuilderForTest extends HtmlTestReportBuilder {
        StringBuilder getStringBuilder() {
            return builder;
        }
        boolean isBuilt() {
            return built;
        }
        String getStringBuilt() {
            return builder.toString();
        }

        public void setBuilt(boolean built) {
            this.built = built;
        }
    }
}