package io.github.yauhenipo.framework.base.driver;

import io.github.bonigarcia.wdm.DriverManagerType;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.yauhenipo.framework.util.configurations.Config;
import io.github.yauhenipo.framework.util.listener.EventHandler;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
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
                if (Config.getBrowserProperties().isHeadless()) {
                    options.addArguments("headless");
                }
                return new ChromeDriver(options);
            }
        },
        FIREFOX {
            @Override
            public RemoteWebDriver getWebDriver() {
                FirefoxBinary firefoxBinary = new FirefoxBinary();
                if (Config.getBrowserProperties().isHeadless()) {
                    firefoxBinary.addCommandLineOptions("--headless");
                }
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setBinary(firefoxBinary);
                return new FirefoxDriver(firefoxOptions);
            }
        };

        public abstract WebDriver getWebDriver();
    }

    private static final EnumMap<BrowserType, DriverManagerType> driverManagerMap = new EnumMap<BrowserType, DriverManagerType>(BrowserType.class) {{
        put(BrowserType.CHROME, DriverManagerType.CHROME);
        put(BrowserType.FIREFOX, DriverManagerType.FIREFOX);
    }};

    private BrowserFactory() {
        throw new IllegalStateException("Utility class");
    }

    public static EventFiringWebDriver setUp(final String browser) throws NamingException {
        Set<String> driverNames = driverManagerMap.keySet().stream().map(Enum::name).collect(Collectors.toSet());
        BrowserType browserType = BrowserType.valueOf(browser.toUpperCase(Locale.ENGLISH));
        if (driverNames.contains(browserType.name())) {
            WebDriverManager.getInstance(driverManagerMap.get(browserType)).setup();
            EventFiringWebDriver eventDriver = new EventFiringWebDriver(browserType.getWebDriver());
            EventHandler handler = new EventHandler();
            eventDriver.register(handler);
            return eventDriver;
        } else {
            throw new NamingException(String.format("Wrong Browser Name: %s", browser));
        }
    }
}
