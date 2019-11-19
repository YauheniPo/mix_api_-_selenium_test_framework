package io.github.yauhenipo.app.page;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

public class SearchPage extends BaseGitHubPage {

    private static final String SEARCHING_LIST_LOCATOR = ".//div[@class='px-2']";
    private static final String SEARCHING_ITEM_CONTENT_LOCATOR = SEARCHING_LIST_LOCATOR + "//div[contains(@class, 'list-item')]";


    public SearchPage selectItem(SearchingMenu item) {
        click(item.getItemLocator());
        return this;
    }

    @RequiredArgsConstructor
    public enum SearchingMenu {
        USERS("Users");

        @NonNull public String item;
        private static final String SEARCHING_MENU_ITEM_LOCATOR = ".//a[contains(@class, '-item') and not(contains(@class, 'UnderlineNav'))][contains(text(), '%s')]";

        public String getItemLocator() {
            return String.format(SEARCHING_MENU_ITEM_LOCATOR, this.item);
        }
    }
}
