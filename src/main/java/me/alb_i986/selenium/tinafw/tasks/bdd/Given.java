package me.alb_i986.selenium.tinafw.tasks.bdd;

import java.util.List;

import me.alb_i986.selenium.tinafw.tasks.CompositeWebTask;
import me.alb_i986.selenium.tinafw.tasks.WebTask;

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
	
}
