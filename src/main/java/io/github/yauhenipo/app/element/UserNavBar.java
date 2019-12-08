package io.github.yauhenipo.app.element;

import io.github.yauhenipo.app.layout.AbstractNavBar;
import io.github.yauhenipo.framework.base.element.NavBar;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class UserNavBar extends AbstractNavBar {

    @AllArgsConstructor
    public enum NavBarImpl implements NavBar {
        REPOSITORIES("Repositories");

        private String item;
        private static final String USER_NAV_BAR_ITEM = ".//a[contains(text(), '%s')]";

        @Override
        public String getItemLocator() {
            return String.format(USER_NAV_BAR_ITEM, this.item);
        }
    }
}
