package io.github.yauhenipo.app.element;

import io.github.yauhenipo.framework.base.WebPage;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class Header extends WebPage {

    static String SEARCH_INPUT_FIELD = ".//input[contains(@class, 'header-search-input')]";

    public Header search(String searchValue) {
        setValue(SEARCH_INPUT_FIELD, searchValue);
        return this;
    }

    @Override
    protected void waitUntilProgressLoadingBar() {}
}
