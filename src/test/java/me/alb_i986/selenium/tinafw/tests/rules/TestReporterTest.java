package me.alb_i986.selenium.tinafw.tests.rules;

import me.alb_i986.selenium.tinafw.domain.Browser;
import me.alb_i986.selenium.tinafw.tests.TestReport;
import me.alb_i986.selenium.tinafw.tests.TestReportBuilder;
import me.alb_i986.selenium.tinafw.tests.TestReportWriter;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TestReporterTest {

    private MockedTestReportBuilder mockedReportBuilder;
    @Mock private TestReportWriter mockedReportWriter;
    @Mock private Browser mockedBrowser;

    private TestReporter out;

    @Test
    public void givenNullBrowser_whenFailed() {
        // given
        String testName = "Dummy Test";
        String reportContent = "<html>dummy report content</html>";
        TestReport testReport = new TestReport(testName, reportContent);
        mockedReportBuilder = new MockedTestReportBuilder(testReport);
        out = new TestReporter(mockedReportBuilder, mockedReportWriter);
        out.setBrowser(null);

        // when
        Throwable dummyThrowable = new Throwable("dummy throwable");
        Description dummyDesc = Description.createTestDescription(DummyTestClass.class, testName);
        out.failed(dummyThrowable, dummyDesc);

        // then
        verify(mockedReportWriter).write(testReport);
    }

    @Test
    public void givenNonNullBrowser_whenFailed() {
        // given
        String testName = "Dummy Test";
        String reportContent = "<html>dummy report content</html>";
        TestReport testReport = new TestReport(testName, reportContent);
        mockedReportBuilder = new MockedTestReportBuilder(testReport);
        out = new TestReporter(mockedReportBuilder, mockedReportWriter);
        out.setBrowser(mockedBrowser);

        // when
        Throwable dummyThrowable = new Throwable("dummy throwable");
        Description dummyDesc = Description.createTestDescription(DummyTestClass.class, testName);
        out.failed(dummyThrowable, dummyDesc);

        // then
        verify(mockedReportWriter).write(testReport);
    }


    private static class DummyTestClass {}

    private static class MockedTestReportBuilder implements TestReportBuilder {

        private TestReport report;

        public MockedTestReportBuilder(TestReport reportToBuild) {
            this.report = reportToBuild;
        }

        @Override
        public TestReportBuilder reset() {
            return this;
        }

        @Override
        public TestReport build(String testName) {
            return report;
        }

        @Override
        public TestReportBuilder withTitle(String title) {
            return this;
        }

        @Override
        public TestReportBuilder withPageSource(String pageSource) {
            return this;
        }

        @Override
        public TestReportBuilder withScreenshot(String screenshotAsBase64) {
            return this;
        }

        @Override
        public TestReportBuilder withProperty(String key, String value) {
            return this;
        }

        @Override
        public TestReportBuilder withStackTrace(Throwable e) {
            return this;
        }

        @Override
        public TestReportBuilder withText(String text) {
            return this;
        }
    }
}