package io.github.yauhenipo.framework.base;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.lang.reflect.InvocationTargetException;

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
        return getWebDriver().findElement(By.xpath(locator));
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

    public void pressEnter() {
        findElement(this.locator).sendKeys(Keys.ENTER);
    }
}
