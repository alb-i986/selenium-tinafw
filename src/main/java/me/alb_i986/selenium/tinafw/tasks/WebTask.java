package me.alb_i986.selenium.tinafw.tasks;

import me.alb_i986.selenium.tinafw.domain.WebUser;
import me.alb_i986.selenium.tinafw.ui.WebPage;

/**
 * A WebTask is supposed to simulate a user interaction with the SUT.
 * E.g.:
 * <ul>
 * <li>open the browser</li>
 * <li>navigate to some URL of the SUT</li>
 * <li>login</li>
 * <li>click buttons, links, fill forms, etc.</li>
 * </ul>
 * <p>
 * WebTask uses page objects to carry on its logic.
 * It is the gate between the layer 'tests' and 'pages', the one
 * that makes for maintainable test suites.
 * In fact, it allows for sharing code among tests.
 * Each concrete WebTask should be as fine-grained as possible:
 * the finer, the more reusable, the better.
 * Fine-grained WebTask's can be then chained together, thanks to
 * {@link CompositeWebTask}.
 * Each WebTask in a chain is able to continue the interaction with the SUT
 * from the page the previous task was visiting.
 * <p>
 * Concrete WebTask's should be parametrized by fields: they
 * should define a field for each piece of test data they need, along with a
 * <a href="http://martinfowler.com/bliki/FluentInterface.html" target="new">
 * fluent</a> setter method.
 * Test data should be stored (statically, i.e. hardcoded, or dynamically,
 * e.g. in a DB) in test classes and passed to WebTask's via the fluent setter methods. 
 * <p>
 * It is best to think of a WebTask as a 
 * <a href="http://martinfowler.com/bliki/GivenWhenThen.html" target="new">
 * Given, When, or Then step</a>.
 * Please, see also
 * <a href="http://dannorth.net/introducing-bdd/" target="new">BDD</a>. 
 * 
 * @see CompositeWebTask
 */
public interface WebTask {

	/**
	 * Perform this task as the user {@link #getUser()}.
	 * Precondition: the user must have already been set
	 * (see {@link #setUser(WebUser)}).
	 * 
	 * @param initialPage the page it is displayed before this task starts
	 * @return the last page that is displayed after this task finishes
	 */
	public WebPage run(WebPage initialPage);
	
	/**
	 * Builds an {@link OrWebTask} made up of (in order)
	 * this task and the given alternative task.
	 * This method can be "recursively" chained in order to define
	 * a multi-member OR, e.g.:
	 * <pre>
	 * {@code
	 * new MyTask()
	 *    .or(new AlternativeTask1())
	 *    .or(new AlternativeTask2())
	 *    .or(new AlternativeTask3())
	 * ;
	 * }
	 * </pre>
	 * 
	 * @param alternativeTask the task to run if this task fails
	 * @return an {@link OrWebTask} made up of (in order)
	 *         this task and the alternative
	 */
	default public OrWebTask or(WebTask alternativeTask) {
		return new OrWebTask(this, alternativeTask);
	}
	
	/**
	 * @param user the user this task will run as.
	 * @return this
	 */
	public WebTask setUser(WebUser user);

	/**
	 * @return the user that this task runs as
	 */
	public WebUser getUser();
	
}
