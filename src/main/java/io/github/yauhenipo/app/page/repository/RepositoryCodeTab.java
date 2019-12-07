package io.github.yauhenipo.app.page.repository;

import lombok.AllArgsConstructor;

public class RepositoryCodeTab extends RepositoryPage {

    @AllArgsConstructor
    public enum SummaryBar {
        CONTIBUTORS("contributors");

        private String item;
        private static final String REPO_SUMMARY_NAV_BAR_ITEM = ".//ul[contains(@class, 'summary')]//*[contains(@href, '%s')]";

        public String getItemLocator() {
            return String.format(REPO_SUMMARY_NAV_BAR_ITEM, this.item);
        }
    }
}
