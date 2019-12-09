package io.github.yauhenipo.app.page.repository.element;

import io.github.yauhenipo.app.layout.AbstractNavBar;
import io.github.yauhenipo.framework.base.element.NavBar;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class RepositoryNavBar extends AbstractNavBar {

    @AllArgsConstructor
    public enum NavBarImpl implements NavBar {
        CODE("", "Code"),
        INSIGHTS("pulse", "Insights");

        private String hrefPart;
        private String text;
        private static final String REPO_NAV_BAR_ITEM = ".//nav[contains(@class, 'reponav')]//*[contains(@href, '%s') or contains(text(), '%s')]";

        @Override
        public String getItemLocator() {
            return String.format(REPO_NAV_BAR_ITEM, this.hrefPart, this.text);
        }
    }
}
