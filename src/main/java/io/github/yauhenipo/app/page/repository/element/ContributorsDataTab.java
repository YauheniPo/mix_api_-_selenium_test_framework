package io.github.yauhenipo.app.page.repository.element;

import io.github.yauhenipo.app.page.repository.RepositoryInsightsTab;
import io.github.yauhenipo.framework.base.layout.Builder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public class ContributorsDataTab extends RepositoryInsightsTab {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ContributorsDataTable {

        private List<String> locators = new ArrayList<>();

        public static BuilderImpl build() {
            return new ContributorsDataTable().new BuilderImpl();
        }

        public class BuilderImpl implements Builder {

            private static final String CONTRIBUTORS_GRAPHS_LOCATOR = ".//div[@class='graphs']";
            private static final String CONTRIBUTORS_LIST_LOCATOR = ".//div[@id='contributors']";
            private static final String CONTRIBUTORS_ITEM_ACCOUNT_LOCATOR = ".//a[@data-hovercard-type='user'][@class='text-normal']";

            private BuilderImpl() {
                locators.add(CONTRIBUTORS_GRAPHS_LOCATOR);
            }

            public BuilderImpl list() {
                locators.add(CONTRIBUTORS_LIST_LOCATOR);
                return this;
            }

            public BuilderImpl login() {
                locators.add(CONTRIBUTORS_ITEM_ACCOUNT_LOCATOR);
                return this;
            }

            @Override
            public String[] get() {
                return ContributorsDataTable.this.locators.toArray(new String[0]);
            }
        }
    }
}
