package io.github.yauhenipo.app.page;

import io.github.yauhenipo.framework.base.element.NavBar;
import io.github.yauhenipo.framework.base.layout.Builder;
import io.github.yauhenipo.framework.util.configurations.Config;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class SearchPage extends BaseGitHubPage {

    public SearchPage selectItem(SearchingMenu item) {
        click(item.getItemLocator());
        return this;
    }

    @RequiredArgsConstructor
    public enum SearchingMenu implements NavBar {
        USERS("Users");

        @NonNull
        public String item;
        private static final String SEARCHING_MENU_ITEM_LOCATOR = ".//a[contains(@class, '-item') and not(contains(@class, 'UnderlineNav'))][contains(text(), '%1$s')]//span[@data-search-type='%1$s']";

        @Override
        public String getItemLocator() {
            return String.format(SEARCHING_MENU_ITEM_LOCATOR, this.item);
        }
    }

    @Override
    public void waitUntilProgressLoadingBar() {
        if (waitForElementToBeExist(loaderBarLocator, Config.getBrowserProperties().getTimeout()) != null) {
            log.info("Wait for progress bar.");
            waitForElementToBeExist(progressLoaderBarLocator);
        }
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class SearchResultTable {

        private List<String> locators = new ArrayList<>();

        public static BuilderImpl build() {
            return new SearchResultTable().new BuilderImpl();
        }

        public class BuilderImpl implements Builder {

            private static final String SEARCHING_LIST_LOCATOR = ".//div[@class='px-2']";
            private static final String SEARCHING_ITEM_CONTENT_LOCATOR = ".//div[contains(@class, 'list-item')]";
            private static final String SEARCHING_ITEM_LOGIN_LOCATOR = ".//em";

            private BuilderImpl() {
                locators.add(SEARCHING_LIST_LOCATOR);
            }

            public BuilderImpl content() {
                locators.add(SEARCHING_ITEM_CONTENT_LOCATOR);
                return this;
            }

            public BuilderImpl login() {
                locators.add(SEARCHING_ITEM_LOGIN_LOCATOR);
                return this;
            }

            @Override
            public String[] get() {
                return SearchResultTable.this.locators.toArray(new String[0]);
            }
        }
    }
}
