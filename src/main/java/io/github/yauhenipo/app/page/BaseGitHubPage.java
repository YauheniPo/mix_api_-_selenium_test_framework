package io.github.yauhenipo.app.page;

import io.github.yauhenipo.app.element.Header;
import io.github.yauhenipo.framework.base.WebPage;
import io.github.yauhenipo.framework.base.layout.Builder;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public abstract class BaseGitHubPage extends WebPage {

    public final Header header = new Header();

    private String loaderBarLocator = "//*[contains(@class, 'progress') and contains(@class, 'loader')]";
    protected String progressLoaderBarLocator = loaderBarLocator + "[contains(@style, '100%')]";

    public List<String> getContentItems(Builder builder) {
        return findElements(builder.get()).stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public <TPage extends BaseGitHubPage> TPage clickByTextPresent(Builder builder, String text, Class<TPage> nextPage) {
        List<WebElement> elements = findElements(builder.get());
        elements.stream().findFirst().filter(webElement -> webElement.getText().equals(text)).get().click();
        try {
            return nextPage.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            log.error(e);
            e.printStackTrace();
        }
        return null;
    }

    public <TPage extends BaseGitHubPage> TPage clickByContainsTextPresent(Builder builder, String text, Class<TPage> nextPage) {
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
}