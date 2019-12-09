package io.github.yauhenipo.framework.base;

import io.github.yauhenipo.framework.base.driver.Browser;
import io.github.yauhenipo.framework.util.configurations.Config;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * The type Smart wait.
 */
@Log4j2
final public class SmartWait {

    /**
     * Wait for t.
     *
     * @param <T>              the type parameter
     * @param condition        the condition
     * @param timeOutInSeconds the time out in seconds
     * @return the t
     */
    public static <T> T waitFor(ExpectedCondition<T> condition, long timeOutInSeconds) {
        Browser.getDriver().manage().timeouts().implicitlyWait(0L, TimeUnit.SECONDS);
        Wait<WebDriver> wait = new FluentWait<>((WebDriver) Browser.getDriver())
                .ignoring(StaleElementReferenceException.class, WebDriverException.class)
                .withTimeout(Duration.ofSeconds(timeOutInSeconds)).pollingEvery(Duration.ofMillis(500));
        try {
            return wait.until(condition);
        } catch (Exception e) {
            log.debug("SmartWait.waitFor", e);
            return null;
        } finally {
            Browser.getDriver().manage().timeouts().implicitlyWait(Config.getBrowserProperties().getTimeout(), TimeUnit.SECONDS);
        }
    }

    /**
     * Wait for true boolean.
     *
     * @param condition        the condition
     * @param timeOutInSeconds the time out in seconds
     * @return the boolean
     */
    public static boolean waitForTrue(ExpectedCondition<Boolean> condition, long timeOutInSeconds) {
        try {
            return waitFor(condition, timeOutInSeconds) != null;
        } catch (Exception e) {
            log.debug("Wait waitForTrue", e);
            return false;
        }
    }

    public static void waitForJSandJQueryToLoad() {
        // wait for jQuery to load
        ExpectedCondition<Boolean> jQueryLoad = driver -> {
            try {
                return 0 == (Long) ((JavascriptExecutor) Objects.requireNonNull(driver))
                        .executeScript("return $.active");
            } catch (Exception e) {
                log.info("no jquery present");
                return true;
            }
        };

        // wait for Javascript to load
        ExpectedCondition<Boolean> jsLoad = driver -> ((JavascriptExecutor) Objects.requireNonNull(driver))
                .executeScript("return document.readyState")
                .toString().equals("complete");

        waitFor(jQueryLoad, Config.getBrowserProperties().getConditionTimeout());
        waitFor(jsLoad, Config.getBrowserProperties().getConditionTimeout());
    }
}