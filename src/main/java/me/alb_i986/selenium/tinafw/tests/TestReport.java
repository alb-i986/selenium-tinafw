package me.alb_i986.selenium.tinafw.tests;

import java.util.Date;

public class TestReport {

    private String format;
    private String report;
    private Date date;
    private String testName;

    public TestReport(String testName, String report) {
        this.testName = testName;
        this.report = report;
        this.date = new Date();
    }

    public String getFormat() {
        return format;
    }

    public String getReport() {
        return report;
    }

    public String getTestName() {
        return testName;
    }

    public Date getCreatedDate() {
        return date;
    }

    public TestReport withFormat(String format) {
        this.format = format;
        return this;
    }

    /**
     * @see #getReport()
     */
    @Override
    public String toString() {
        return getReport();
    }
}
