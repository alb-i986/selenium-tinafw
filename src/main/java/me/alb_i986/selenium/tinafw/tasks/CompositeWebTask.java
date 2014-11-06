package me.alb_i986.selenium.tinafw.tasks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import me.alb_i986.selenium.tinafw.ui.Page;

/**
 * A WebTask made up of many supposedly small (sub-)tasks,
 * which in turn may be CompositeWebTask's too.
 * <p>
 * CompositeWebTask allows you to build trees of WebTask's.
 * The idea is to have many atomic WebTask's (the leaves of the tree)
 * focused on a very small user interaction.
 * And then you start build up tasks by <i>composition</i>.
 * <p>
 * <i>With CompositeWebTask you may even get rid of page objects (!)</i>,
 * and have WebTask's talk directly to PageComponent's.
 * <p>
 * CompositeWebTask exploits the
 * <a href="http://en.wikipedia.org/wiki/Composite_pattern">
 * Composite design pattern</a>.
 */
public class CompositeWebTask extends BaseWebTask implements Iterable<WebTask> {

	private List<WebTask> subtasks = new ArrayList<>();
	
	/**
	 * @see #CompositeWebTask(List)
	 */
	public CompositeWebTask(WebTask... subtasks) {
		this(Arrays.asList(subtasks));
	}

	/**
	 * Set the given list as the list of components of this composite.
     * Replace any null subtask with a {@link NullTask}.
	 */
	public CompositeWebTask(List<WebTask> subtasks) {
		super();
		Collections.replaceAll(subtasks, null, new NullTask());
		this.subtasks = subtasks;
	}

	/**
	 * Runs each subtask in order, and finally return the
	 * page that the last task was visiting.
	 * Each subtask will run with the same user as the composite.
	 * 
	 * @throws AssertionError if the user set in this composite is null
	 */
	@Override
	public Page run(Page initialPage) {
		Page currentPage = initialPage;
		for(WebTask task : this) {
			logger.info("BEGIN subtask " + task.getClass().getSimpleName());
			// before running the subtask, set the user
			task.setUser(getUser());
			currentPage = task.run(currentPage);
			logger.info("END subtask " + task.getClass().getSimpleName());
		}
		return currentPage;
	}

	/**
	 * The iterator for iterating over the subtasks
	 * this composite is made up of.
	 * <p>
	 * Backed by {@link List#iterator()}.
	 */
	@Override
	public Iterator<WebTask> iterator() {
		return subtasks.iterator();
	}

}
