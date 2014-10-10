package me.alb_i986.selenium.tinafw.tasks;

/**
 * A CompositeWebTask accepting only a Given, a When,
 * and a Then as components.
 * <p>
 * Also, provides factory methods for creating givens whens thens.
 */
public class BDDWebTask extends CompositeWebTask {

	public BDDWebTask(Given given, When when, Then then) {
		super(given, when, then);
	}

	public static Given given(WebTask... givens) {
		return new Given(givens);
	}

	public static When when(WebTask... whens) {
		return new When(whens);
	}
	
	public static Then then(WebTask... thens) {
		return new Then(thens);
	}
	
}
