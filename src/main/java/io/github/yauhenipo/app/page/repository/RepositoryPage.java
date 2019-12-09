package io.github.yauhenipo.app.page.repository;

import io.github.yauhenipo.app.layout.AbstractNavBar;
import io.github.yauhenipo.app.page.BaseGitHubPage;
import io.github.yauhenipo.app.page.repository.element.RepositoryNavBar;
import io.github.yauhenipo.framework.util.configurations.Config;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class RepositoryPage extends BaseGitHubPage {

    public final AbstractNavBar repoNavBar = new RepositoryNavBar();

    @Override
    public void waitUntilProgressLoadingBar() {
        if (waitForElementToBeExist(loaderBarLocator, Config.getBrowserProperties().getTimeout()) != null) {
            log.info("Wait for progress bar.");
            waitForElementToBeExist(progressLoaderBarLocator);
        }
    }
}
