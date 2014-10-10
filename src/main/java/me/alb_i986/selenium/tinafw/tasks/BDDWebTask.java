package me.alb_i986.selenium.tinafw.tasks;

/**
 * A CompositeWebTask accepting only a Given, a When,
 * and a Then as components.
 */
public class BDDWebTask extends CompositeWebTask {

	public BDDWebTask(Given given, When when, Then then) {
		super(given, when, then);
	}
	
}
