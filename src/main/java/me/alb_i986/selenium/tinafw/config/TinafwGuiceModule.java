package me.alb_i986.selenium.tinafw.config;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import me.alb_i986.selenium.tinafw.ui.WebDriverFactory;
import me.alb_i986.selenium.tinafw.ui.WebDriverFactoryDecoratorImplicitWait;
import me.alb_i986.selenium.tinafw.ui.WebDriverFactoryLocal;
import me.alb_i986.selenium.tinafw.ui.WebDriverFactoryRemote;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class TinafwGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
    }

    /**
     * A {@link WebDriverFactory} according to the settings:
     * <ul>
     * <li>a {@link WebDriverFactoryLocal} if {@link Config#PROP_GRID_HUB_URL} is
     *     not defined;
     * <li>else, a {@link WebDriverFactoryRemote}
     * <li>finally, decorate it with {@link WebDriverFactoryDecoratorImplicitWait}, if
     *     {@link Config#PROP_TIMEOUT_IMPLICIT_WAIT} is defined
     * </ul>
     */
    @Provides
    WebDriverFactory webDriverFactoryProvider() {
        WebDriverFactory tmpFactoryFromConfig;
        URL hubURL = Config.getGridHubUrl();
        if(hubURL == null) {
            tmpFactoryFromConfig = new WebDriverFactoryLocal();
        } else {
            DesiredCapabilities extraDesiredCapabilities = new DesiredCapabilities();
            extraDesiredCapabilities.setVersion(Config.getGridBrowserVersion());
            extraDesiredCapabilities.setPlatform(Config.getGridPlatform());
            tmpFactoryFromConfig = new WebDriverFactoryRemote(hubURL, extraDesiredCapabilities);
        }
        Long implicitWait = Config.getImplicitWait();
        if(implicitWait != null)
            tmpFactoryFromConfig = new WebDriverFactoryDecoratorImplicitWait(implicitWait, tmpFactoryFromConfig);
        return tmpFactoryFromConfig;
    }
}
