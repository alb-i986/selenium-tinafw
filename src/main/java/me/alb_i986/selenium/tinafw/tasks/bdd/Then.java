package me.alb_i986.selenium.tinafw.tasks.bdd;

import java.util.List;

import me.alb_i986.selenium.tinafw.tasks.CompositeWebTask;
import me.alb_i986.selenium.tinafw.tasks.WebTask;

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

}
