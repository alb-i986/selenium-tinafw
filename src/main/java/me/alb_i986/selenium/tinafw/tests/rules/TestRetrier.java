package me.alb_i986.selenium.tinafw.tests.rules;

import org.apache.log4j.Logger;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class TestRetrier implements TestRule {

	protected static final Logger logger = Logger.getLogger(TestRetrier.class);
	
	private int maxExecutions;

	/**
	 * If arg lte 0, then set it to 1.
	 * @param maxExecutions
	 */
	public TestRetrier(int maxExecutions) {
		if(maxExecutions>0)
			this.maxExecutions = maxExecutions;
		else
			this.maxExecutions = 1;
	}


	public Statement apply(Statement base, Description description) {
		return statement(base, description);
	}

	private Statement statement(final Statement base, final Description description) {
		return new Statement() {
			
			@Override
			public void evaluate() throws Throwable {
				Throwable caughtThrowable = null;
				int i;
				for (i = 0; i < maxExecutions; i++) {
					try {
						base.evaluate();
						break;
					} catch (Throwable t) {
						caughtThrowable = t;
						// if this will not actually be retried,
						// there is no need to log the failure (would be a duplicate log)
						if(maxExecutions>1) {
							logger.info("END test: FAILED " + (i + 1) + "/" + maxExecutions + " "
									+ description.getDisplayName());
						}
					}
				}
				if(i==maxExecutions) { 
					throw caughtThrowable;
				} else {
					logger.info("END test: FAILED " + (i + 1) + "/" + maxExecutions + " "
							+ description.getDisplayName() + " -- seems FLAKY");
				}
			}
		};
	}
}
