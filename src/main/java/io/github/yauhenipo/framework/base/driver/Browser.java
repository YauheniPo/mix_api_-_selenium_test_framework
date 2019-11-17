package io.github.yauhenipo.framework.base.driver;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.github.yauhenipo.framework.util.configurations.BrowserProperties;
import io.github.yauhenipo.framework.util.configurations.StageProperties;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import javax.naming.NamingException;

@Log4j2
public final class Browser {

    private static BrowserProperties browserProperties = BrowserProperties.getInstance();
    private static String BROWSER_URL = StageProperties.getInstance().getUrl();
    private static boolean IS_BROWSER_HEADLESS = browserProperties.isHeadless();
    private static String currentBrowser = System.getProperty("browser", browserProperties.getBrowser());
    public static final long IMPLICITLY_WAIT = browserProperties.getTimeout();
    private static Browser instance = new Browser();

    private Browser() {
        initDriver();
    }

    public static Browser getInstance() {
        if (instance == null) {
            synchronized (Browser.class) {
                if (instance == null) {
                    instance = new Browser();
                }
            }
        }
        return instance;
    }

    public static RemoteWebDriver getDriver() {
        return ((RemoteWebDriver)((WrapsDriver)WebDriverRunner.getWebDriver()).getWrappedDriver());
    }

    public void openStartPage() {
        Selenide.open("/");
    }

    private static void initDriver() {
        Configuration.timeout = IMPLICITLY_WAIT;
        Configuration.headless = IS_BROWSER_HEADLESS;
        Configuration.baseUrl = BROWSER_URL;
        try {
            BrowserFactory.setUp(currentBrowser);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
}
