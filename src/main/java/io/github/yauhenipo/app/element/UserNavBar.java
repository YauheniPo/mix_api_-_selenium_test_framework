package io.github.yauhenipo.app.element;

import io.github.yauhenipo.app.layout.AbstractNavBar;
import io.github.yauhenipo.framework.base.element.NavBar;
import io.github.yauhenipo.framework.util.configurations.Config;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class UserNavBar extends AbstractNavBar {

    @Override
    public void waitUntilProgressLoadingBar() {
        if (waitForElementToBeExist(loaderBarLocator, Config.getBrowserProperties().getTimeout()) != null) {
            log.info("Wait for progress bar.");
            waitForElementToBeExist(progressLoaderBarLocator);
        }
    }

    @AllArgsConstructor
    public enum NavBarImpl implements NavBar {
        REPOSITORIES("TAB_REPOSITORIES");

        private String item;
        private static final String USER_NAV_BAR_ITEM = ".//a[contains(@data-hydro-click, '%s')]";

        @Override
        public String getItemLocator() {
            return String.format(USER_NAV_BAR_ITEM, this.item);
        }
    }
}
