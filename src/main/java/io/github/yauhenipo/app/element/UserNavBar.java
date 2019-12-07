package io.github.yauhenipo.app.element;

import io.github.yauhenipo.app.page.BaseGitHubPage;
import io.github.yauhenipo.framework.base.WebPage;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.InvocationTargetException;

@Log4j2
public class UserNavBar extends WebPage {

    public <TPage extends BaseGitHubPage> TPage select(NavBar tab, Class<TPage> nextPage) {
        click(tab.getItemLocator());
        try {
            return nextPage.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            log.error(e);
            e.printStackTrace();
        }
        return null;
    }

    @AllArgsConstructor
    public enum NavBar {
        REPOSITORIES("Repositories");

        private String item;
        private static final String USER_NAV_BAR_ITEM = ".//a[contains(text(), '%s')]";

        public String getItemLocator() {
            return String.format(USER_NAV_BAR_ITEM, this.item);
        }
    }
}
