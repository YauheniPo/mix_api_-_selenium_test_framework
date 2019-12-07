package io.github.yauhenipo.app.page;

import io.github.yauhenipo.app.element.UserNavBar;
import io.github.yauhenipo.app.layout.WithUserNavBar;

public class AccountPage extends BaseGitHubPage implements WithUserNavBar {

    public <TPage extends BaseGitHubPage> TPage selectNavBar(UserNavBar.NavBar tab, Class<TPage> nextPage) {
        return userNavBar.select(tab, nextPage);
    }
}