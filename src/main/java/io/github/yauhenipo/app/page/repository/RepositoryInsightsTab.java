package io.github.yauhenipo.app.page.repository;

import io.github.yauhenipo.framework.base.element.NavBar;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.InvocationTargetException;

@Log4j2
public class RepositoryInsightsTab extends RepositoryPage {

    public <TPage extends RepositoryInsightsTab> TPage selectMenuItem(NavBar item, Class<TPage> nextPage) {
        click(item.getItemLocator());
        try {
            return nextPage.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            log.error(e);
            e.printStackTrace();
        }
        return null;
    }

    @RequiredArgsConstructor
    public enum Menu implements NavBar {
        CONTRIBUTORS("Contributors");

        @NonNull
        public String item;
        private static final String MENU_ITEM_LOCATOR = ".//nav[@class='menu']//*[text()='%s']";

        @Override
        public String getItemLocator() {
            return String.format(MENU_ITEM_LOCATOR, this.item);
        }
    }
}
