package me.alb_i986.selenium.tinafw.tasks;

import java.util.List;

import me.alb_i986.selenium.tinafw.ui.WebPage;

import static me.alb_i986.selenium.tinafw.tasks.WebTasks.*;

/**
 * A {@link CompositeWebTask} which chains subtasks in a logical OR.
 * It allows to handle situations where it is not known exactly what
 * the state of the SUT before a task starts will be, but
 * it is known that there are only X possible initial states.
 * With {@link OrWebTask}, you can write one task for each of
 * the possible scenarios to handle, and have them executed in turn,
 * until one finishes without throwing any exception.
 * In case none of the alternatives finishes cleanly, then the last
 * thrown exception will be thrown.
 * All of the subtasks must be homogeneous, i.e. they all must finish on
 * the same page when they don't fail.
 * <p>
 * In its most basic form, {@link OrWebTask} runs subtasks in order,
 * until one succeeds.
 * This works as long as each of the failing subtasks leaves the state of
 * the page and the DOM intact, in a way that the next alternative is able
 * to run cleanly as if the previous ones had never run.
 * For this condition to hold, for example, each alternative
 * should be always on the very same page, at any time,
 * never navigate to another page, and should not change the state
 * of dialogs (i.e. should not open dialogs which were not initially open,
 * or close dialogs which were initially open).
 * <p>
 * This constraint can be removed by implementing a mechanism that
 * restores the initial situation. Two possible ways:
 * <ul>
 * <li>either by encapsulating the restoring logic in each of the alternative
 * subtasks itself (e.g. with a catch block which closes the dialog and
 * re-throws the underlying exception)
 * </li>
 * <li>or by having a more complex {@link OrWebTask} which runs
 * a separate "restorer" task every time an alternative subtask fails
 * </li>
 * </ul> 
 * <p>
 * In the basic form, in other words by default, the "restorer"
 * is a {@link NullTask}.
 * It can be changed with {@link #withRestorer(WebTask)}.
 */
public class OrWebTask extends CompositeWebTask {
	
	private WebTask restorer = NULL_TASK;

	public OrWebTask(List<WebTask> subtasks) {
		super(subtasks);
	}

	public OrWebTask(WebTask... subtasks) {
		super(subtasks);
	}

	@Override
	public WebPage run(WebPage initialPage) {
		RuntimeException caughtException = null;
		for (WebTask subtask : this) {
			try {
				logger.info("BEGIN OR subtask " + subtask.getClass().getSimpleName());
				// before running the subtask, set the user
				subtask.setUser(getUser());
				return subtask.run(initialPage);
			} catch(RuntimeException e) {
				logger.info("END OR subtask " + subtask.getClass().getSimpleName() +
						". Error: " + e.getMessage());
				caughtException = e;
				initialPage = restorer.run(initialPage);
				continue;
			}
		}
		throw caughtException;
	}
	
	public OrWebTask withRestorer(WebTask restorer) {
		this.restorer = restorer;
		return this;
	}
	
}
