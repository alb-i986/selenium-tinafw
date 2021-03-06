package me.alb_i986.selenium.tinafw.tests;

import me.alb_i986.selenium.tinafw.tasks.WebTask;
import me.alb_i986.selenium.tinafw.domain.WebUser;;

/**
 * All a WebTest needs to know is:
 * <ul>
 * <li>who: which {@link WebUser} is about to use the SUT</li>
 * <li>what: what {@link WebTask} the user needs to perform in order to test the SUT</li>
 * </ul>
 * <p>
 * Therefore, a basic test involving only one user interacting with
 * the SUT can be written simply as:
 * <pre>
 * {@code
 * User user;
 * WebTask task;
 * user.openBrowser();
 * user.doTask(task);
 * user.closeBrowser();
 * }
 * </pre>
 * 
 */
public interface WebTest {

}
