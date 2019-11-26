package io.github.yauhenipo.framework.base;

import io.github.yauhenipo.framework.base.driver.Browser;
import io.github.yauhenipo.framework.util.configurations.Config;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Log4j2
public abstract class WebPage extends BaseEntity {

    private String locator;

    protected String getText(String locator) {
        return findElement(locator).getText();
    }

    protected String getAttribute(String locator, String attribute) {
        return findElement(locator).getAttribute(attribute);
    }

    protected void setValue(String locator, String sendValue) {
        log.info(String.format("Set Value to element. Value: %s; locator: %s", sendValue, locator));
        ExpectedCondition<Boolean> condition = driver -> {
            try {
                WebElement element = findElement(locator);
                Objects.requireNonNull(element).clear();
                Objects.requireNonNull(element).sendKeys(sendValue);
                return element.getAttribute("value").equals(sendValue);
            } catch (Exception e) {
                log.debug(e);
                return false;
            }
        };
        SmartWait.waitFor(condition, Config.getBrowserProperties().getConditionTimeout());
    }

    protected void click(String locator) {
        log.info(String.format("Click to locator %s", locator));
        waitUntilElementToBeClickable(locator).click();
    }

    private WebElement waitUntilElementToBeClickable(String locator) {
        return SmartWait.waitFor(ExpectedConditions.elementToBeClickable(By.xpath(locator)), Config.getBrowserProperties().getConditionTimeout());
    }

    protected WebElement waitForElementToBeExist(String locator, long timeOutInSeconds) {
        return SmartWait.waitFor(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)), timeOutInSeconds);
    }

    protected WebElement waitForElementToBeExist(String locator) {
        return waitForElementToBeExist(locator, Config.getBrowserProperties().getConditionTimeout());
    }

    private WebElement findElement(String locator) {
        this.locator = locator;
        return findElements(Config.getBrowserProperties().getConditionTimeout(), locator).get(0);
    }

    protected List<WebElement> findElements(String... locator) {
        return findElements(Config.getBrowserProperties().getConditionTimeout(), locator);
    }

    private List<WebElement> findElements(long timeout, String... locators) {
        SmartWait.waitForJSandJQueryToLoad();
        waitUntilProgressLoadingBar();
        log.info(String.format("Find element: %s", Arrays.asList(locators)));
        ExpectedCondition<List<WebElement>> condition = driver -> {
            try {
                log.info(String.format("findElements: locator: %s", locators[0]));
                return getElements(Browser.getDriver().findElements(By.xpath(locators[0])), Arrays.copyOfRange(locators, 1, locators.length));
            } catch (Exception e) {
                log.debug(e.getMessage());
                return null;
            }
        };
        return SmartWait.waitFor(condition, timeout);
    }

    private List<WebElement> getElements(List<WebElement> webElements, String... locators) {
        log.info(String.format("getElements: element size: %d; locators: %s", webElements.size(), Arrays.asList(locators)));
        if (webElements.size() == 0) {
            return null;
        }
        List<WebElement> elements = new ArrayList<>();
        if (locators.length == 0) {
            log.info(String.format("Was found %d elements", webElements.size()));
            return webElements;
        }
        for (WebElement elem : webElements) {
            if (!elem.isDisplayed()) {
                return null;
            }
            elements.addAll(elem.findElements(By.xpath(locators[0])));
        }
        log.info(String.format("For the next getting elements: size: %d; locators: [%s]", elements.size(), Arrays.asList(locators)));
        return getElements(elements, Arrays.copyOfRange(locators, 1, locators.length));
    }

    public <T extends WebPage> WebPage pressEnter(Class<T> nextPage) {
        pressEnter(this.locator);
        try {
            return nextPage.getConstructor().newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            log.error(e);
            e.printStackTrace();
        }
        return null;
    }

    protected void pressEnter(String locator) {
        log.info(String.format("Press ENTER: %s", locator));
        findElement(locator).sendKeys(Keys.ENTER);
    }

    protected abstract void waitUntilProgressLoadingBar();
}
