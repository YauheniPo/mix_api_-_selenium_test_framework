package io.github.yauhenipo.app.test;

import io.github.yauhenipo.app.TestGroup;
import io.github.yauhenipo.app.page.MainPage;
import io.github.yauhenipo.app.page.SearchPage;
import io.github.yauhenipo.framework.base.BaseTestApi;
import io.github.yauhenipo.framework.util.configurations.Config;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Severity;
import ru.yandex.qatools.allure.model.SeverityLevel;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import java.util.List;

@Log4j2
public class TestGithub extends BaseTestApi {

    @Features(value = "Search")
    @Description(value = "Validation of searching users")
    @Severity(value = SeverityLevel.NORMAL)
    @Test(groups = {TestGroup.SEARCH})
    public void testSearchProductItems() {
        final String searchingUser = Config.getStageProperties().getUser();

        MainPage mainPage = new MainPage();
        List<String> searchLoginUsers = ((SearchPage)mainPage
                .header
                .search(searchingUser)
                .pressEnter(SearchPage.class))
                .selectItem(SearchPage.SearchingMenu.USERS)
                .getSearchLoginItems();

        Response response = RestAssured.given().param("q", searchingUser)
                .get("search/users").then().extract().response();

        List<String> loginUsers = response.path("items.login");

        assertThat(String.format("User '%s' does not exist in searching items", searchingUser),
                searchLoginUsers,
                equalTo(loginUsers));
    }
}
