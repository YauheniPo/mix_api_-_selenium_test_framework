package io.github.yauhenipo.app.test;

import io.github.yauhenipo.app.TestGroup;
import io.github.yauhenipo.app.element.RepositoriesTab;
import io.github.yauhenipo.app.element.UserNavBar;
import io.github.yauhenipo.app.model.RepositoryApiData;
import io.github.yauhenipo.app.model.UserApiData;
import io.github.yauhenipo.app.page.AccountPage;
import io.github.yauhenipo.app.page.MainPage;
import io.github.yauhenipo.app.page.SearchPage;
import io.github.yauhenipo.app.page.repository.RepositoryInsightsTab;
import io.github.yauhenipo.app.page.repository.RepositoryPage;
import io.github.yauhenipo.app.page.repository.element.ContributorsDataTab;
import io.github.yauhenipo.app.page.repository.element.RepositoryNavBar;
import io.github.yauhenipo.framework.base.BaseTestApi;
import io.github.yauhenipo.framework.util.configurations.Config;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Severity;
import ru.yandex.qatools.allure.model.SeverityLevel;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Log4j2
public class TestGithub extends BaseTestApi {

    private final String searchingUser = Config.getStageProperties().getUser();

    @Features(value = "Search")
    @Description(value = "Validation of searching users")
    @Severity(value = SeverityLevel.NORMAL)
    @Test(groups = {TestGroup.SEARCH, TestGroup.USER})
    public void testSearchProductItems() {

        Response response = RestAssured.given()
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .param("q", searchingUser)
                .get("search/users")
                .then().statusCode(200).extract()
                .response();

        List<String> loginUsers = response.path("items.login");

        List<String> searchLoginUsers = new MainPage()
                .header
                .search(searchingUser)
                .pressEnter(SearchPage.class)
                .selectItem(SearchPage.SearchingMenu.USERS)
                .getContentItems(SearchPage.SearchResultTable.build().content().login());

        assertThat(String.format("User '%s' does not exist in searching items", searchingUser),
                searchLoginUsers,
                equalTo(loginUsers));
    }

    @Features(value = "Contributor")
    @Description(value = "Validation of searching project contributor")
    @Severity(value = SeverityLevel.NORMAL)
    @Test(groups = {TestGroup.SEARCH, TestGroup.USER, TestGroup.CONTRIBUTOR})
    public void testSearchProjectContributor() {

        final String forkedRepoText = "healenium-web";

        Response userRepositoriesResponse = RestAssured.given()
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .pathParam("user", searchingUser)
                .get("/users/{user}/repos")
                .then().statusCode(200).extract()
                .response();

        RepositoryApiData forkedRepository = userRepositoriesResponse
                .jsonPath()
                .getObject(String.format("findAll {it.fork == true}.find {it.name.equals('%s')}", forkedRepoText),
                        RepositoryApiData.class);

        Response repositoryResponse = RestAssured.given()
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .get(forkedRepository.getUrl())
                .then().statusCode(200).extract()
                .response();

        RepositoryApiData repository = repositoryResponse
                .jsonPath()
                .getObject("parent",
                        RepositoryApiData.class);

        Response repositoryContributorsResponse = RestAssured.given()
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .baseUri(repository.getUrl())
                .get("contributors")
                .then().statusCode(200).extract()
                .response();

        List<UserApiData> contributorsApiDataList = repositoryContributorsResponse
                .jsonPath()
                .getList("",
                        UserApiData.class);

        List<String> contributorList = new MainPage()
                .header
                .search(searchingUser)
                .pressEnter(SearchPage.class)
                .selectItem(SearchPage.SearchingMenu.USERS)
                .clickByTextPresent(SearchPage.SearchResultTable.build().content().login(),
                        searchingUser,
                        AccountPage.class)
                .userNavBar
                .select(UserNavBar.NavBarImpl.REPOSITORIES, RepositoriesTab.class)
                .clickByContainsTextPresent(RepositoriesTab.RepositoriesTable.build().content().forked(),
                        forkedRepoText,
                        RepositoryPage.class)
                .repoNavBar
                .select(RepositoryNavBar.NavBarImpl.INSIGHTS, RepositoryInsightsTab.class)
                .selectMenuItem(RepositoryInsightsTab.Menu.CONTRIBUTORS, ContributorsDataTab.class)
                .getContentItems(ContributorsDataTab.ContributorsDataTable.build().list().login());

        assertThat("Contributors list does not exist item.",
                contributorsApiDataList.stream().map(UserApiData::getLogin).collect(Collectors.toList()),
                hasItems(contributorList.toArray()));
    }
}
