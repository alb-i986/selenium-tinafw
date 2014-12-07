package me.alb_i986.selenium.tinafw.domain;

import me.alb_i986.selenium.tinafw.ui.*;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

/**
 * A Browser can be opened and closed, and can be used to browse to some page.
 * It is backed by a {@link WebDriver}: by opening a Browser, a WebDriver is instantiated.
 * <p>
 * It relies on a {@link WebDriverFactory} for instantiating {@link WebDriver}s.
 * The factory may be injected in the constructor.
 * See {@link #Browser()} for details on the default factory.
 * <p>
 * A Browser has a type, one of {@link SupportedBrowser}.
 * The type is not bound to an instance of Browser: it is chosen when opening the browser.
 * Thus, a Browser instance, during its life, may represent many different types
 * of browsers (one for each pair of calls to {@link #open(SupportedBrowser)}
 * and {@link #close()}).
 */
public class Browser {

	protected static final Logger logger = Logger.getLogger(Browser.class);

	private WebDriverFactory driverFactory;

	private WebDriver driver;
	private SupportedBrowser type;

	/**
	 * Inject the default {@link WebDriverFactory}, which is
	 * {@link WebDriverFactoryProvider#fromConfig}
	 */
	public Browser() {
		this(WebDriverFactoryProvider.fromConfig);
	}
	
	/**
	 * Create a Browser and inject the given {@link WebDriverFactory}.
	 * 
	 * @param driverFactory
	 */
	public Browser(WebDriverFactory driverFactory) {
		if(driverFactory == null)
			throw new IllegalArgumentException("The WebDriverFactory is null");
		this.driverFactory = driverFactory;
	}

	/**
	 * Open a browser according to the given parameter,
	 * by instantiating a {@link WebDriver}.
	 * Do nothing if this browser was already open.
	 * 
	 * @see WebDriverFactory#getWebDriver(SupportedBrowser)
	 */
	public void open(SupportedBrowser browserType) {
		if(!isOpen()) {
			logger.info("Opening browser " + browserType);
			this.driver = driverFactory.getWebDriver(browserType);
			this.type = browserType;
		}
	}
	
	/**
	 * Close the browser by quitting the underlying {@link WebDriver}.
	 * Do nothing if this browser was not open.
	 * 
	 * @see WebDriver#quit()
	 */
	public void close() {
		if(isOpen()) {
			driver.quit();
			driver = null;
			type = null;
		}
	}
	
	/**
	 * @see PageHelper.Navigation#browseTo(String, WebDriver)
	 */
	public void browseTo(String relativeUrl) {
		assertIsOpen();
		PageHelper.Navigation.browseTo(relativeUrl, driver);
	}

	/**
	 * Browse to the given {@link LoadablePage}, and return its instance.
	 * 
	 * @return the requested {@link LoadablePage}
	 * 
	 * @see LoadablePage#load(Class, WebDriver)
	 */
	public <T extends LoadablePage> T browseTo(Class<T> loadablePageClass) {
		assertIsOpen();
		return LoadablePage.load(loadablePageClass, driver);
	}
	
	/**
	 * @see PageHelper.Navigation#browseBack(WebDriver)
	 */
	public void browseBack() {
		assertIsOpen();
		PageHelper.Navigation.browseBack(driver);
	}

	/**
	 * @return true if driver is not null.
	 */
	public boolean isOpen() {
		return driver != null;
	}
	
	public WebDriver getWebDriver() {
		return driver;
	}
	
	public SupportedBrowser getType() {
		return type;
	}
	
	/**
	 * @throws IllegalStateException if this browser is not open
	 */
	private void assertIsOpen() {
		if(!isOpen())
			throw new IllegalStateException("The browser is not open");
	}

	/**
	 * Supposed to be used only by unit tests.
	 */
	void setDriver(WebDriver driver) {
		this.driver = driver;
	}

}
