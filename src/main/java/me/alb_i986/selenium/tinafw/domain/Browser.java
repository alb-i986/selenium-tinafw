package me.alb_i986.selenium.tinafw.domain;

import com.google.inject.Inject;
import me.alb_i986.selenium.tinafw.ui.*;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

/**
 * A Browser can be opened and closed, and can be used to browse to some page.
 * It is backed by a {@link WebDriver}: by opening a Browser, a WebDriver is instantiated.
 * <p>
 * It relies on a {@link WebDriverFactory} for actually instantiating
 * {@link WebDriver}s.
 * <p>
 * A Browser has a type, one of {@link SupportedBrowser}.
 * The type is not bound to an instance of Browser: it is chosen when opening the browser.
 * Thus, a Browser instance, during its life, may represent many different types
 * of browsers (one for each pair of calls to {@link #open(SupportedBrowser)}
 * and {@link #close()}).
 */
public class Browser implements AutoCloseable {

	private static final Logger logger = Logger.getLogger(Browser.class);

	private WebDriverFactory driverFactory;
	private BrowserState state;
    private SupportedBrowser type;

	/**
	 * Create a Browser and inject the given {@link WebDriverFactory}.
     *
     * @throws IllegalArgumentException if the given driverFactory is null
	 */
	@Inject
	public Browser(WebDriverFactory driverFactory) {
		if(driverFactory == null) {
			throw new IllegalArgumentException("The WebDriverFactory is null");
		}
		this.driverFactory = driverFactory;
		state = new ClosedBrowser();
	}

	/**
	 * Open a browser according to the given parameter,
	 * by instantiating a {@link WebDriver}.
	 * Do nothing if this browser is already open.
	 * 
	 * @see WebDriverFactory#getWebDriver(SupportedBrowser)
	 */
	public void open(SupportedBrowser browserType) {
		state.open(browserType);
	}
	
	/**
	 * Close the browser by quitting the underlying {@link WebDriver}.
	 * Do nothing if this browser is closed.
	 * 
	 * @see WebDriver#quit()
	 */
    @Override
	public void close() {
		state.close();
	}
	
	/**
     * @throws IllegalStateException in case this browser is currently closed
     *
	 * @see PageHelper.Navigation#browseTo(String, WebDriver)
	 */
	public void browseTo(String relativeUrl) {
		state.browseTo(relativeUrl);
	}

	/**
	 * Browse to the given {@link LoadablePage}, and return its instance.
	 * 
	 * @return the requested {@link LoadablePage}
     *
     * @throws IllegalStateException in case this browser is currently closed
     *
	 * @see LoadablePage#load(Class, WebDriver)
	 */
	public <T extends LoadablePage> T browseTo(Class<T> loadablePageClass) {
		return state.browseTo(loadablePageClass);
	}
	
	/**
     * @throws IllegalStateException in case this browser is currently closed
     *
	 * @see PageHelper.Navigation#browseBack(WebDriver)
	 */
	public void browseBack() {
		state.browseBack();
	}

	/**
	 * @return true if the underlying driver is not null.
	 */
	public boolean isOpen() {
		return state.isOpen();
	}

    /**
     * @return the underlying driver; null if this browser is currently closed.
     */
	public WebDriver getWebDriver() {
		return state.getWebDriver();
	}

    public SupportedBrowser getType() {
        return type;
    }

    /**
	 * State pattern
	 */
	interface BrowserState {

		void open(SupportedBrowser browserType);
		void close();
		void browseTo(String relativeUrl);
		<T extends LoadablePage> T browseTo(Class<T> loadablePageClass);
		void browseBack();
		boolean isOpen();
		WebDriver getWebDriver();
	}

	class OpenBrowser implements BrowserState {

        private final WebDriver driver;

        public OpenBrowser(WebDriver driver) {
            if (driver == null) {
                throw new IllegalArgumentException(
                        "An open browser cannot be initialized with a null driver." +
                        " This is likely to be a bug.");
            }
            this.driver = driver;
        }

        /**
         * NOP
         */
        @Override
        public void open(SupportedBrowser browserType) {
            return;
        }

        @Override
        public void close() {
            driver.quit();
            state = new ClosedBrowser();
            type = null;
        }

        /**
         * @see PageHelper.Navigation#browseTo(String, WebDriver)
         */
        @Override
        public void browseTo(String relativeUrl) {
            PageHelper.Navigation.browseTo(relativeUrl, driver);
        }

        /**
         * Browse to the given {@link LoadablePage}, and return its instance.
         *
         * @return the requested {@link LoadablePage}
         *
         * @see LoadablePage#load(Class, WebDriver)
         */
        @Override
        public <T extends LoadablePage> T browseTo(Class<T> loadablePageClass) {
            return LoadablePage.load(loadablePageClass, driver);
        }

        /**
         * @see PageHelper.Navigation#browseBack(WebDriver)
         */
        @Override
        public void browseBack() {
            PageHelper.Navigation.browseBack(driver);
        }

        /**
         * @return true
         */
        @Override
        public boolean isOpen() {
            return true;
        }

        @Override
        public WebDriver getWebDriver() {
            return driver;
        }
    }

	class ClosedBrowser implements BrowserState {

        private static final String ILLEGAL_STATE_ERR_MESSAGE = "The browser is closed";

        @Override
        public void open(SupportedBrowser browserType) {
            logger.info("Opening browser " + browserType);
            WebDriver driver = driverFactory.getWebDriver(browserType);
            state = new OpenBrowser(driver);
            type = browserType;
        }

        /**
         * No-op.
         */
        @Override
        public void close() {
            return;
        }

        @Override
        public void browseTo(String relativeUrl) {
            throw new IllegalStateException(ILLEGAL_STATE_ERR_MESSAGE);
        }

        @Override
        public <T extends LoadablePage> T browseTo(Class<T> loadablePageClass) {
            throw new IllegalStateException(ILLEGAL_STATE_ERR_MESSAGE);
        }

        @Override
        public void browseBack() {
            throw new IllegalStateException(ILLEGAL_STATE_ERR_MESSAGE);
        }

        @Override
        public boolean isOpen() {
            return false;
        }

        @Override
        public WebDriver getWebDriver() {
            return null;
        }
    }
}
