package me.alb_i986.selenium.tinafw.tasks.runnable;

import java.util.List;

import me.alb_i986.selenium.tinafw.domain.SupportedBrowser;
import me.alb_i986.selenium.tinafw.domain.WebUser;
import me.alb_i986.selenium.tinafw.tasks.CompositeWebTask;
import me.alb_i986.selenium.tinafw.tasks.WebTask;

/**
 * A fully executable <i>single</i> user interaction with the SUT.
 * <p>
 * The only thing required is to inject a {@link WebUser} <i>with a {@link SupportedBrowser}</i>.
 * E.g.:
 * <pre>
 * {@code
 * WebUser user = new WebUser().withBrowserType(SupportedBrowser.CHROME);
 * SingleUserInteraction sui = .. ;
 * sui.setUser(user);
 * new Thread(sui).start();
 * }
 * </pre>
 */
public class SingleUserInteraction extends CompositeWebTask implements RunnableTask {

	public SingleUserInteraction(WebTask... subtasks) {
		super(subtasks);
	}

	public SingleUserInteraction(List<WebTask> subtasks) {
		super(subtasks);
	}

	/**
	 * Open the browser, execute this task, and finally close the browser.
	 */
	@Override
	public void run() {
		if(user == null)
			throw new IllegalStateException("user is null");
		user.openBrowser();
		try {
			user.doTask(this);
		} finally {
			user.closeBrowser();
		}
	}

}
