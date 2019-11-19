package io.github.yauhenipo.framework.base;

import io.github.yauhenipo.framework.base.driver.Browser;
import io.github.yauhenipo.framework.util.listener.CustomListener;
import lombok.extern.log4j.Log4j2;
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

    protected RemoteWebDriver getWebDriver() {
        return Browser.getDriver();
    }
}
