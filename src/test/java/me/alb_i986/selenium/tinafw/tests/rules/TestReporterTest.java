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

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TestReporterTest {

    @Mock private TestReportWriter mockedReportWriter;
    @Mock private Browser mockedBrowser;

    private TestReporter out;

    @Test
    public void failed_givenNullBrowser() {
        // given
        TestReportBuilderStub reportBuilderStub = new TestReportBuilderStub(createDummyTestReport());
        out = new TestReporter(reportBuilderStub, mockedReportWriter);
        out.setBrowser(null);

        // when
        Throwable dummyThrowable = new Throwable("dummy throwable");
        Description dummyDesc = Description.createTestDescription(DummyTestClass.class, "");
        out.failed(dummyThrowable, dummyDesc);

        // then
        verify(mockedReportWriter).write(reportBuilderStub.report);
    }

    @Test
    public void failed_givenNonNullBrowser() {
        // given
        TestReportBuilderStub reportBuilderStub = new TestReportBuilderStub(createDummyTestReport());
        out = new TestReporter(reportBuilderStub, mockedReportWriter);
        assertNotNull(mockedBrowser);
        out.setBrowser(mockedBrowser);

        // when
        Throwable dummyThrowable = new Throwable("dummy throwable");
        Description dummyDesc = Description.createTestDescription(DummyTestClass.class,
                reportBuilderStub.report.getTestName());
        out.failed(dummyThrowable, dummyDesc);

        // then
        verify(mockedReportWriter).write(reportBuilderStub.report);
    }

    private static TestReport createDummyTestReport() {
        String testName = "Dummy Test";
        String reportContent = "<html>dummy report content</html>";
        return new TestReport(testName, reportContent);
    }

    private static class DummyTestClass {}

    /**
     * A TestReportBuilder whose {@link #build(String)} returns the given TestReport.
     * All other methods do nothing but return a self instance.
     *
     * We are not mocking this with Mockito because of the fluent interface it features.
     */
    private static class TestReportBuilderStub implements TestReportBuilder {

        TestReport report;

        public TestReportBuilderStub(TestReport reportToBuild) {
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