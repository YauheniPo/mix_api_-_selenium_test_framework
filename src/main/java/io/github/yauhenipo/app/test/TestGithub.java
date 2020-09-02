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
import io.github.yauhenipo.framework.base.api.BaseTestApi;
import io.github.yauhenipo.framework.util.configurations.Config;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Severity;
import ru.yandex.qatools.allure.model.SeverityLevel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

@Log4j2
public class TestGithub extends BaseTestApi {

    private final String searchingUser = Config.getStageProperties().getUser();
    private Map<String, String> userSearchParam = new HashMap<String, String>() {
        {
            put("q", searchingUser);
        }
    };

    @Features(value = "Search")
    @Description(value = "Validation of searching users")
    @Severity(value = SeverityLevel.NORMAL)
    @Test(groups = {TestGroup.SEARCH, TestGroup.USER})
    public void testSearchProductItems() {
        Response response = apiService.response("search/users", userSearchParam);

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

        Map<String, String> userPathParam = new HashMap<String, String>() {
            {
                put("user", searchingUser);
            }
        };

        Response userRepositoriesResponse =
                apiService.response("/users/{user}/repos", userSearchParam, userPathParam);

        RepositoryApiData forkedRepository = userRepositoriesResponse
                .jsonPath()
                .getObject(String.format("findAll {it.fork == true}.find {it.name.equals('%s')}", forkedRepoText),
                        RepositoryApiData.class);

        Response repositoryResponse = apiService.response(forkedRepository.getUrl());

        RepositoryApiData repository = repositoryResponse
                .jsonPath()
                .getObject("parent",
                        RepositoryApiData.class);

        Response repositoryContributorsResponse = apiService.response(repository.getUrl(), "contributors");

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
                .searchRepository(forkedRepoText)
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
