package io.github.yauhenipo.app.page;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class SearchPage extends BaseGitHubPage {

    private static final String SEARCHING_LIST_LOCATOR = ".//div[@class='px-2']";
    private static final String SEARCHING_ITEM_CONTENT_LOCATOR = ".//div[contains(@class, 'list-item')]";
    private static final String SEARCHING_ITEM_LOGIN_LOCATOR = ".//em";

    public List<String> getSearchContentItems() {
        return findElements(SEARCHING_LIST_LOCATOR, SEARCHING_ITEM_CONTENT_LOCATOR).stream()
                .map(WebElement::getText).collect(Collectors.toList());
    }

    public List<String> getSearchLoginItems() {
        return findElements(SEARCHING_LIST_LOCATOR, SEARCHING_ITEM_CONTENT_LOCATOR, SEARCHING_ITEM_LOGIN_LOCATOR).stream()
                .map(WebElement::getText).collect(Collectors.toList());
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
        private static final String SEARCHING_MENU_ITEM_LOCATOR = ".//a[contains(@class, '-item') and not(contains(@class, 'UnderlineNav'))][contains(text(), '%s')]";

        public String getItemLocator() {
            return String.format(SEARCHING_MENU_ITEM_LOCATOR, this.item);
        }
    }

    @Override
    public void waitUntilProgressLoadingBar() {
        waitForElementToBeExist(progressLoaderBarLocator);
    }
}
