package me.alb_i986.selenium.tinafw.tasks;

import java.util.List;

/**
 * A Given step (BDD).
 */
public class Given extends CompositeWebTask {

	public Given(List<WebTask> tasks) {
		super(tasks);
	}

	public Given(WebTask... tasks) {
		super(tasks);
	}
	
	public static Given given(WebTask... givens) {
		return new Given(givens);
	}

}
