package io.github.yauhenipo.framework.base.driver;

import io.github.yauhenipo.framework.util.configurations.Config;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import javax.naming.NamingException;
import java.util.concurrent.TimeUnit;

@Log4j2
public final class Browser {

    private static final String CURRENT_BROWSER = System.getProperty("browser", Config.getBrowserProperties().getBrowser());
    private static ThreadLocal<EventFiringWebDriver> driverHolder = ThreadLocal.withInitial(Browser::initDriver);
    private static Browser instance = new Browser();

    private Browser() {
        log.info("Init Browser");
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
        if (driverHolder.get() == null) {
            driverHolder.set(initDriver());
        }
        return (RemoteWebDriver)driverHolder.get().getWrappedDriver();
    }

    public void exit() {
        try {
            getDriver().quit();
            log.info("WebDriver quit");
        } catch (Exception e) {
            log.error(this, e);
        } finally {
            if (isBrowserAlive()) {
                driverHolder.set(null);
            }
        }
    }

    private boolean isBrowserAlive() {
        return driverHolder.get() != null;
    }

    public void navigate(final String url) {
        getDriver().navigate().to(url);
    }

    public void openBaseUrl() {
        navigate(Config.getStageProperties().getUrl());
        windowMaximise();
    }

    public void windowMaximise() {
        getDriver().manage().window().maximize();
    }

    private static EventFiringWebDriver initDriver() {
        try {
            EventFiringWebDriver driver = BrowserFactory.setUp(CURRENT_BROWSER);
            driver.manage().timeouts().implicitlyWait(Config.getBrowserProperties().getTimeout(), TimeUnit.SECONDS);
            return driver;
        } catch (NamingException e) {
            log.error(e);
            e.printStackTrace();
        }
        return null;
    }
}
