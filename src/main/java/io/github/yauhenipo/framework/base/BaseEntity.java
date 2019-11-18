package io.github.yauhenipo.framework.base;

import io.github.yauhenipo.framework.base.driver.Browser;
import io.github.yauhenipo.framework.util.listener.CustomListener;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

@Log4j2
@Listeners({CustomListener.class})
public abstract class BaseEntity {

    @BeforeMethod()
    public void beforeMethod() {
        Browser.getInstance().openBaseUrl();
    }

    @AfterMethod
    public void afterMethod() {
        Browser.getInstance().exit();
    }

    private RemoteWebDriver getWebDriver() {
        return Browser.getDriver();
    }

    protected void setValue(String locator, String sendValue) {
        findElement(locator).sendKeys(sendValue);
    }

    private WebElement findElement(String locator) {
        return getWebDriver().findElement(By.xpath(locator));
    }
}
