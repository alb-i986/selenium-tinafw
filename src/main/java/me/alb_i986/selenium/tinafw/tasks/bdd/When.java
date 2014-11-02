package me.alb_i986.selenium.tinafw.tasks.bdd;

import java.util.List;

import me.alb_i986.selenium.tinafw.tasks.CompositeWebTask;
import me.alb_i986.selenium.tinafw.tasks.WebTask;

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
