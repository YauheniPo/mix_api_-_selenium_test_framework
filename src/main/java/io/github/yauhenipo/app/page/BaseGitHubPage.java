package io.github.yauhenipo.app.page;

import io.github.yauhenipo.app.layout.WithHeader;
import io.github.yauhenipo.framework.base.WebPage;

public abstract class BaseGitHubPage extends WebPage implements WithHeader {

    private String loaderBarLocator = "//*[contains(@class, 'progress') and contains(@class, 'loader')]";
    String progressLoaderBarLocator = loaderBarLocator + "[contains(@style, '100%')]";

    @Override
    protected void waitUntilProgressLoadingBar() {}
}
