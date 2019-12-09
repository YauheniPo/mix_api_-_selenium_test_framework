package io.github.yauhenipo.app.layout;

import io.github.yauhenipo.app.page.BaseGitHubPage;
import io.github.yauhenipo.framework.base.element.NavBar;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.InvocationTargetException;

@Log4j2
public abstract class AbstractNavBar extends BaseGitHubPage {

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
}
