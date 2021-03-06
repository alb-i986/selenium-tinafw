package me.alb_i986.selenium.tinafw.tests.rules;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AssumptionViolatedException;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import me.alb_i986.selenium.tinafw.tests.WebTest;

/**
 * Reports the status of the running test (starting/passed/failed/skipped) to a {@link Logger}.
 * 
 */
public class TestLogger extends TestWatcher {
	
	protected static final Logger logger = LogManager.getLogger(WebTest.class);
	
	@Override
	protected void skipped(AssumptionViolatedException e, Description description) {
		logger.info("END test: " + TestStatus.SKIPPED + " > " + description.getDisplayName() + " -- cause: " + e.getMessage());
	}

	@Override
	protected void starting(Description description) {
		logger.info("BEGIN test " + description.getDisplayName());
	}

	@Override
	protected void failed(Throwable e, Description description) {
		logFinishedTest(TestStatus.FAILED, description);
	}

	@Override
	protected void succeeded(Description description) {
		logFinishedTest(TestStatus.PASSED, description);
	}
	
	private void logFinishedTest(TestStatus status, Description description) {
		logger.info("END test: " + status + " > " + description.getDisplayName());
	}
	
	enum TestStatus {
		PASSED,
		FAILED,
		SKIPPED,
	}

}
