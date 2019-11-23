package io.github.yauhenipo.framework.base;

import io.github.yauhenipo.framework.base.driver.Browser;
import io.github.yauhenipo.framework.util.configurations.Config;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Log4j2
public abstract class WebPage extends BaseEntity {

    private String locator;

    protected void setValue(String locator, String sendValue) {
        findElement(locator).sendKeys(sendValue);
    }

    protected void click(String locator) {
        findElement(locator).click();
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
        ExpectedCondition<List<WebElement>> condition = driver -> {
            try {
                return getElements(Browser.getDriver().findElements(By.xpath(locators[0])), Arrays.copyOfRange(locators, 1, locators.length));
            } catch (Exception e) {
                log.debug(e.getMessage());
                return null;
            }
        };
        return SmartWait.waitFor(condition, timeout);
    }

    private List<WebElement> getElements(List<WebElement> webElements, String... locators) {
        List<WebElement> elements = new ArrayList<>();
        if (locators.length == 0) {
            return webElements;
        }
        for (WebElement elem : webElements) {
            if (!elem.isDisplayed()) {
                return null;
            }
            elements.addAll(elem.findElements(By.xpath(locators[0])));
        }
        return getElements(elements, Arrays.copyOfRange(locators, 1, locators.length));
    }

    public <T extends WebPage> WebPage pressEnter(Class<T> nextPage) {
        pressEnter();
        try {
            return nextPage.getConstructor().newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            log.error(e);
            e.printStackTrace();
        }
        return null;
    }

    private void pressEnter() {
        findElement(this.locator).sendKeys(Keys.ENTER);
    }
}
