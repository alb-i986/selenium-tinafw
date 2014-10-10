package me.alb_i986.selenium.tinafw.tasks;

import java.util.List;

/**
 * A Then step (BDD).
 */
public class Then extends CompositeWebTask {

	public Then(List<WebTask> tasks) {
		super(tasks);
	}

	public Then(WebTask... tasks) {
		super(tasks);
	}

	public static Then then(WebTask... thens) {
		return new Then(thens);
	}

}
