package io.github.yauhenipo.framework.base;

import com.codeborne.selenide.testng.BrowserPerTest;
import com.codeborne.selenide.testng.ScreenShooter;
import io.github.yauhenipo.framework.base.driver.Browser;
import io.github.yauhenipo.framework.utils.listener.CustomListener;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

@Log4j2
@Listeners({BrowserPerTest.class, ScreenShooter.class, CustomListener.class})
public class BaseEntity {

    @BeforeMethod()
    public void beforeMethod() {
        Browser.getInstance().openStartPage();
    }

    protected RemoteWebDriver getWebDriver() {
        return Browser.getDriver();
    }
}
