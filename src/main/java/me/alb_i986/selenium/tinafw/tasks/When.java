package me.alb_i986.selenium.tinafw.tasks;

import java.util.List;

/**
 * A When step (BDD).
 */
public class When extends CompositeWebTask {

	public When(List<WebTask> tasks) {
		super(tasks);
	}

	public When(WebTask... tasks) {
		super(tasks);
	}
	
}
