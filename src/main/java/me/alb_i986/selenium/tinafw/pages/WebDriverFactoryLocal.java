package me.alb_i986.selenium.tinafw.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;

public class WebDriverFactoryLocal extends WebDriverFactory {

	@Override
	public WebDriver getWebDriver() {
		WebDriver driver;
		//TODO Class.forName(browserType.toString().toLowerCase())
		switch (BROWSER_TYPE) {
			case CHROME:
				driver = new ChromeDriver();
				break;
			case FIREFOX:
				driver = new FirefoxDriver();
				break;
			case SAFARI:
				driver = new SafariDriver();
				break;
			case IE:
				driver = new InternetExplorerDriver();
				break;
			default:
				throw new IllegalArgumentException("The specified Browser type (" + BROWSER_TYPE + ")"
							+ " is not supported at the moment");
		}
		return driver;
	}

}
