package io.github.yauhenipo.framework.base.driver;

import com.codeborne.selenide.WebDriverRunner;
import io.github.bonigarcia.wdm.DriverManagerType;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.yauhenipo.framework.util.configurations.BrowserProperties;
import io.github.yauhenipo.framework.util.listener.EventHandler;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import javax.naming.NamingException;
import java.util.EnumMap;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

final public class BrowserFactory {

    public enum BrowserType {

        CHROME {
            @Override
            public RemoteWebDriver getWebDriver() {
                ChromeOptions options = new ChromeOptions();
                if (BrowserProperties.getInstance().isHeadless()) {
                    options.addArguments("--start-maximized");
                    options.addArguments("headless");
                }
                return new ChromeDriver(options);
            }
        };

        public abstract WebDriver getWebDriver();
    }

    private static final EnumMap<BrowserType, DriverManagerType> driverManagerMap = new EnumMap<BrowserType, DriverManagerType>(BrowserType.class) {{
        put(BrowserType.CHROME, DriverManagerType.CHROME);
    }};

    private BrowserFactory() {
        throw new IllegalStateException("Utility class");
    }

    public static void setUp(final String browser) throws NamingException {
        Set<String> driverNames = driverManagerMap.keySet().stream().map(Enum::name).collect(Collectors.toSet());
        BrowserType browserType = BrowserType.valueOf(browser.toUpperCase(Locale.ENGLISH));
        if (driverNames.contains(browserType.name())) {
            WebDriverManager.getInstance(driverManagerMap.get(browserType)).setup();
            EventFiringWebDriver eventDriver = new EventFiringWebDriver(browserType.getWebDriver());
            EventHandler handler = new EventHandler();
            eventDriver.register(handler);
            WebDriverRunner.setWebDriver(eventDriver);
        } else {
            throw new NamingException(String.format("Wrong Browser Name: %s", browser));
        }
    }
}
