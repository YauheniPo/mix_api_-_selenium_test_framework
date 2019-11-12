package io.github.yauhenipo.app.test;

import io.github.yauhenipo.app.TestGroup;
import io.github.yauhenipo.app.pages.MainPage;
import io.github.yauhenipo.framework.base.BaseTest;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Severity;
import ru.yandex.qatools.allure.model.SeverityLevel;

@Log4j2
public class TestGithub extends BaseTest {

    @Description(value = "Validation of search field")
    @Features(value = "Validation of search product")
    @Severity(value = SeverityLevel.NORMAL)
    @Test(groups = {TestGroup.SEARCH, TestGroup.MOBILE})
    public void testSearchProductItems() {
        MainPage mainPage = new MainPage();
    }

    @Description(value = "Validation of price")
    @Features(value = "Validation of average price")
    @Severity(value = SeverityLevel.NORMAL)
    @Test(groups = {TestGroup.PRICE, TestGroup.MOBILE, TestGroup.PRODUCT})
    public void testPrice() {

    }
}
