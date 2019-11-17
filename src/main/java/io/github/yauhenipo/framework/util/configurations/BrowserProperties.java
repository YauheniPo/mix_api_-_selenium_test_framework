package io.github.yauhenipo.framework.util.configurations;

import lombok.Getter;
import ru.yandex.qatools.properties.PropertyLoader;
import ru.yandex.qatools.properties.annotations.Property;
import ru.yandex.qatools.properties.annotations.Resource;

@Resource.Classpath("browser.properties")
public class BrowserProperties {

    @Getter
    @Property("browser")
    private String browser = "chrome";

    @Getter
    @Property("browser.headless")
    private boolean isHeadless = false;

    @Getter
    @Property("browser.timeout")
    private long timeout = 10;

    private static BrowserProperties configuration = new BrowserProperties();

    private BrowserProperties() {
        PropertyLoader.populate(this);
    }

    public static BrowserProperties getInstance() {
        if (configuration == null) {
            synchronized (BrowserProperties.class) {
                if (configuration == null) {
                    configuration = new BrowserProperties();
                }
            }
        }
        return configuration;
    }
}