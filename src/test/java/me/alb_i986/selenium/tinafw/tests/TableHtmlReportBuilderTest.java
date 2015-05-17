package me.alb_i986.selenium.tinafw.tests;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TableHtmlReportBuilderTest {

    private static final String EXPECTED_HEADER = "<html>\n" +
            "<head>\n" +
            "<script src=\"https://google-code-prettify.googlecode.com/svn/loader/run_prettify.js\"></script>\n" +
            "</head>\n" +
            "<body>\n" +
            "<table border=\"1\"><tbody>\n";

    private static final String EXPECTED_FOOTER = "</tbody></table></body></html>";

    private TableHtmlReportBuilderForTest out;

    @Before
    public void before() {
        out = new TableHtmlReportBuilderForTest();
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
        String htmlReport = out.build();

        //then
        assertThat(htmlReport, allOf(startsWith("<html>"), endsWith("</html>")));
        assertTrue(out.isBuilt());
        assertEquals(htmlReport, out.build());
    }

    @Test
    public void withTitle() {
        // given reset
        out.reset();

        // when build with title
        String actualHtml = out.withTitle("my test").build();

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
        String actualHtml = out.withProperty("Browser", "Chrome").build();

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
        String actualHtml = out.withPageSource("<html><body><a href=\"http://www.google.com\">link to google</a></body>").build();

        //then
        String expectedHtml = EXPECTED_HEADER +
                "<tr><td><table><tr><td><pre class=\"prettyprint\">&lt;html&gt;&lt;body&gt;&lt;a href=&quot;http://www.google.com&quot;&gt;link to google&lt;/a&gt;&lt;/body&gt;</pre></td></tr></table></td></tr>\n" +
                EXPECTED_FOOTER;
        assertEquals(expectedHtml, actualHtml);
    }

    @Test
    public void withScreenshot() {
        // given reset
        out.reset();

        // when build with screenshot
        String actualHtml = out.withScreenshot("AsDaSd").build();

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
        String actualHtml = out.withText("asd asd asd").build();

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
        String actualHtml = out.withStackTrace(e).build();

        //then
        String partialExpectedHtml = "<tr><td><table><tr><td>java.lang.RuntimeException: outer\n";
        assertThat(actualHtml, containsString(partialExpectedHtml));
    }

    /**
     * http://xunitpatterns.com/Test-Specific%20Subclass.html
     */
    static class TableHtmlReportBuilderForTest extends TableHtmlReportBuilder {
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