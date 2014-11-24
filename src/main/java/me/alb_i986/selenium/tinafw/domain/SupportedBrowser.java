package me.alb_i986.selenium.tinafw.domain;

import org.openqa.selenium.remote.DesiredCapabilities;

public enum SupportedBrowser {

	CHROME("chrome", "ChromeDriver", DesiredCapabilities.chrome()),
	FIREFOX("firefox", "FirefoxDriver", DesiredCapabilities.firefox()),
	SAFARI("safari", "SafariDriver", DesiredCapabilities.safari()),
	IE("ie", "InternetExplorerDriver", DesiredCapabilities.internetExplorer()),
	HTML_UNIT("htmlunit", "HtmlUnitDriver", DesiredCapabilities.htmlUnit()),
	;

	private static final String SELENIUM_PACKAGE = "org.openqa.selenium";
	
	private String fullyQualifiedClassName;
	private DesiredCapabilities capabilities;

	private SupportedBrowser(String subPackage, String className, DesiredCapabilities capabilities) {
		this.fullyQualifiedClassName = SELENIUM_PACKAGE + "." + subPackage + "." + className;
		this.capabilities = capabilities;
	}
	
	public String toFullyQualifiedClassName() {
		return fullyQualifiedClassName;
	}

	public DesiredCapabilities toCapabilities() {
		return this.capabilities;
	}

	/**
	 * @return the concrete WebDriver class corresponding to this enum
	 * @throws ClassNotFoundException
	 */
	public Class<?> toClass() throws ClassNotFoundException {
		return Class.forName(toFullyQualifiedClassName());
	}
	
	/**
	 * @return the lower cased name of the enum constant
	 */
	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}
}