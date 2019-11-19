package io.github.yauhenipo.app.test;

import io.github.yauhenipo.app.TestGroup;
import io.github.yauhenipo.app.page.MainPage;
import io.github.yauhenipo.app.page.SearchPage;
import io.github.yauhenipo.framework.base.BaseTest;
import io.github.yauhenipo.framework.util.configurations.Config;
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
        ((SearchPage)mainPage.header.search(Config.getStageProperties().getUser()).pressEnter(SearchPage.class))
                .selectItem(SearchPage.SearchingMenu.USERS);


    }
}
