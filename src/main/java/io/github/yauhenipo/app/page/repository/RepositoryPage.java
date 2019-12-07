package io.github.yauhenipo.app.page.repository;

import io.github.yauhenipo.app.page.repository.element.RepositoryNavBar;
import io.github.yauhenipo.app.layout.WithRepoNavBar;
import io.github.yauhenipo.app.page.BaseGitHubPage;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class RepositoryPage extends BaseGitHubPage implements WithRepoNavBar {

    @Override
    public void waitUntilProgressLoadingBar() {
        log.info("Wait for progress bar.");
        waitForElementToBeExist(progressLoaderBarLocator);
    }

    public <TTab extends RepositoryPage> TTab selectNavBar(RepositoryNavBar.NavBar tab, Class<TTab> nextTab) {
        return repoNavBar.select(tab, nextTab);
    }
}
