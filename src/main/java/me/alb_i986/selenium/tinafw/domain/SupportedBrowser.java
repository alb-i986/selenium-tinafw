package me.alb_i986.selenium.tinafw.domain;

public enum SupportedBrowser {

	CHROME("chrome.ChromeDriver"),
	FIREFOX("firefox.FirefoxDriver"),
	SAFARI("safari.SafariDriver"),
	IE("ie.InternetExplorerDriver"),
	HTML_UNIT("htmlunit.HtmlUnitDriver"),
	;

	private static final String SELENIUM_PACKAGE = "org.openqa.selenium";
	
	private String partialFQClassName;

	private SupportedBrowser(String name) {
		this.partialFQClassName = name;
	}
	
	public String toFullyQualifiedClassName() {
		return SELENIUM_PACKAGE + "." + partialFQClassName;
	}

	/**
	 * @return the concrete WebDriver class corresponding to this enum
	 * @throws ClassNotFoundException
	 */
	public Class<?> toClass() throws ClassNotFoundException {
		return Class.forName(toFullyQualifiedClassName());
	}
}