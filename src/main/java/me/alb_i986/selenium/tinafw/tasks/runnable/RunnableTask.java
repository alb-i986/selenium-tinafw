package me.alb_i986.selenium.tinafw.tasks.runnable;

import me.alb_i986.selenium.tinafw.tasks.WebTask;

/**
 * A {@link WebTask} that encapsulates a full user interaction (single or
 * multiple) with the SUT, and which can be executed, possibly in a separate
 * thread, as a {@link Runnable}.
 * <p>
 * Ultimately, it represents a test.
 */
public interface RunnableTask extends WebTask, Runnable {

	@Override
	public void run();

}
