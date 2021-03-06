package io.github.yauhenipo.app.element;

import io.github.yauhenipo.app.page.BaseGitHubPage;
import io.github.yauhenipo.framework.base.layout.Builder;
import io.github.yauhenipo.framework.util.configurations.Config;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class RepositoriesTab extends BaseGitHubPage {

    private static final String SEARCH_REPOS_INPUT_FIELD = "//input[@id='your-repos-filter']";

    @Override
    public void waitUntilProgressLoadingBar() {
        if (waitForElementToBeExist(loaderBarLocator, Config.getBrowserProperties().getTimeout()) != null) {
            log.info("Wait for progress bar.");
            waitForElementToBeExist(progressLoaderBarLocator);
        }
    }

    public RepositoriesTab searchRepository(String repos) {
        click(SEARCH_REPOS_INPUT_FIELD);
        setValue(SEARCH_REPOS_INPUT_FIELD, repos);
        return this;
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class RepositoriesTable {

        private List<String> locators = new ArrayList<>();

        public static BuilderImpl build() {
            return new RepositoriesTable().new BuilderImpl();
        }

        public class BuilderImpl implements Builder {

            private static final String REPOSITORIES_LIST_LOCATOR = ".//div[@id='user-repositories-list']";
            private static final String REPOSITORY_ITEM_CONTENT_LOCATOR = ".//div[contains(@class, 'd-inline-block') and contains(@class, 'col')]";
            private static final String REPOSITORY_FORKED_LOCATOR = ".//span[contains(text(), 'Forked')]";

            private BuilderImpl() {
                locators.add(REPOSITORIES_LIST_LOCATOR);
            }

            public BuilderImpl content() {
                locators.add(REPOSITORY_ITEM_CONTENT_LOCATOR);
                return this;
            }

            public BuilderImpl forked() {
                locators.add(REPOSITORY_FORKED_LOCATOR);
                return this;
            }

            @Override
            public String[] get() {
                return RepositoriesTable.this.locators.toArray(new String[0]);
            }
        }
    }
}
