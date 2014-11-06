package me.alb_i986.selenium.tinafw.ui;

import me.alb_i986.selenium.tinafw.domain.SupportedBrowser;

import org.openqa.selenium.WebDriver;

/**
 * Decorate a WebDriver so that it can handle the certificate
 * error page that IE displays when navigating to a website
 * with https, but with an invalid certificate (e.g. self-signed).
 * See also e.g. http://stackoverflow.com/questions/7710619
 */
public class WebDriverFactoryDecoratorHandleCertErrOnIE extends WebDriverFactoryDecorator {

	public WebDriverFactoryDecoratorHandleCertErrOnIE(WebDriverFactory decoratingFactory) {
		super(decoratingFactory);
	}

	@Override
	public WebDriver getWebDriver(SupportedBrowser browserType) {
		WebDriver driver = super.getWebDriver(browserType);
		if(browserType == SupportedBrowser.IE) {
			// here is the trick in JS
			driver.get("javascript:document.getElementById('overridelink').click();");
		}
		return driver;
	}

}
