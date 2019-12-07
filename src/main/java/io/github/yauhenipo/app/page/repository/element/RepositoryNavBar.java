package io.github.yauhenipo.app.page.repository.element;

import io.github.yauhenipo.app.page.repository.RepositoryPage;
import io.github.yauhenipo.framework.base.WebPage;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.InvocationTargetException;

@Log4j2
public class RepositoryNavBar extends WebPage {

    public <TPage extends RepositoryPage> TPage select(NavBar tab, Class<TPage> nextPage) {
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
        CODE("", "Code"),
        INSIGHTS("pulse", "Insights");

        private String hrefPart;
        private String text;
        private static final String REPO_NAV_BAR_ITEM = ".//nav[contains(@class, 'reponav')]//*[contains(@href, '%s') or contains(text(), '%s')]";

        public String getItemLocator() {
            return String.format(REPO_NAV_BAR_ITEM, this.hrefPart, this.text);
        }
    }
}
