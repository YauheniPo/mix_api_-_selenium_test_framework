package io.github.yauhenipo.app.element;

import io.github.yauhenipo.app.page.BaseGitHubPage;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class RepositoriesTab extends BaseGitHubPage {

    public <TPage extends BaseGitHubPage> TPage clickByContainsTextPresent(RepositoriesTable.Builder builder, String text, Class<TPage> nextPage) {
        List<WebElement> elements = findElements(builder.get());
        elements.stream().findFirst().filter(webElement -> StringUtils.containsIgnoreCase(webElement.getText(), text)).get().click();
        try {
            return nextPage.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            log.error(e);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void waitUntilProgressLoadingBar() {
        log.info("Wait for progress bar.");
        waitForElementToBeExist(progressLoaderBarLocator);
    }

    public static class RepositoriesTable {

        private List<String> locators = new ArrayList<>();

        private RepositoriesTable() {
        }

        public static Builder build() {
            return new RepositoriesTable().new Builder();
        }

        public class Builder {

            private static final String REPOSITORIES_LIST_LOCATOR = ".//div[@id='user-repositories-list']";
            private static final String REPOSITORY_ITEM_CONTENT_LOCATOR = ".//div[contains(@class, 'd-inline-block') and contains(@class, 'col')]";
            private static final String REPOSITORY_FORKED_LOCATOR = ".//span[contains(text(), 'Forked')]";

            private Builder() {
                locators.add(REPOSITORIES_LIST_LOCATOR);
            }

            public Builder content() {
                locators.add(REPOSITORY_ITEM_CONTENT_LOCATOR);
                return this;
            }

            public Builder forked() {
                locators.add(REPOSITORY_FORKED_LOCATOR);
                return this;
            }

            String[] get() {
                return RepositoriesTable.this.locators.toArray(new String[0]);
            }
        }
    }
}
