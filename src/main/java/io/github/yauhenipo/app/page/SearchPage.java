package io.github.yauhenipo.app.page;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class SearchPage extends BaseGitHubPage {

    public static class SearchResultTable {

        private List<String> locators = new ArrayList<>();

        private SearchResultTable() {
        }

        public static Builder build() {
            return new SearchResultTable().new Builder();
        }

        public class Builder {

            private static final String SEARCHING_LIST_LOCATOR = ".//div[@class='px-2']";
            private static final String SEARCHING_ITEM_CONTENT_LOCATOR = ".//div[contains(@class, 'list-item')]";
            private static final String SEARCHING_ITEM_LOGIN_LOCATOR = ".//em";

            private Builder() {
                locators.add(SEARCHING_LIST_LOCATOR);
            }

            public Builder content() {
                locators.add(SEARCHING_ITEM_CONTENT_LOCATOR);
                return this;
            }

            public Builder login() {
                locators.add(SEARCHING_ITEM_LOGIN_LOCATOR);
                return this;
            }

            String[] get() {
                return SearchResultTable.this.locators.toArray(new String[0]);
            }
        }
    }

    public List<String> getSearchContentItems(SearchResultTable.Builder builder) {
        return findElements(builder.get()).stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public SearchPage selectItem(SearchingMenu item) {
        click(item.getItemLocator());
        return this;
    }

    @RequiredArgsConstructor
    public enum SearchingMenu {
        USERS("Users");

        @NonNull
        public String item;
        private static final String SEARCHING_MENU_ITEM_LOCATOR = ".//a[contains(@class, '-item') and not(contains(@class, 'UnderlineNav'))][contains(text(), '%1$s')]//span[@data-search-type='%1$s']";

        public String getItemLocator() {
            return String.format(SEARCHING_MENU_ITEM_LOCATOR, this.item);
        }
    }

    @Override
    public void waitUntilProgressLoadingBar() {
        log.info("Wait for progress bar");
        waitForElementToBeExist(progressLoaderBarLocator);
    }
}
