package io.github.yauhenipo.framework.util.configurations;

import lombok.Getter;
import ru.yandex.qatools.properties.PropertyLoader;
import ru.yandex.qatools.properties.annotations.Property;
import ru.yandex.qatools.properties.annotations.Resource;

@Resource.Classpath("stage.properties")
public class FrameworkProperties {

    @Getter
    @Property("framework.maxRerunCount")
    private Integer maxRerunCount = 0;

    private static FrameworkProperties configuration = new FrameworkProperties();

    public static FrameworkProperties getInstance() {
        if (configuration == null) {
            synchronized (FrameworkProperties.class) {
                if (configuration == null) {
                    configuration = new FrameworkProperties();
                }
            }
        }
        return configuration;
    }

    private FrameworkProperties() {
        PropertyLoader.populate(this);
    }
}