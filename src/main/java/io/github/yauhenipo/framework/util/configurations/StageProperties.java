package io.github.yauhenipo.framework.util.configurations;

import lombok.Getter;
import ru.yandex.qatools.properties.PropertyLoader;
import ru.yandex.qatools.properties.annotations.Property;
import ru.yandex.qatools.properties.annotations.Resource;
import ru.yandex.qatools.properties.annotations.With;
import ru.yandex.qatools.properties.providers.MapOrSyspropPathReplacerProvider;

@Resource.Classpath("${system.test.env}-stage.properties")
@With(MapOrSyspropPathReplacerProvider.class)
public class StageProperties {

    @Getter
    @Property("stage.url")
    private String url;

    @Getter
    @Property("api.url")
    private String apiUrl;

    @Getter
    @Property("stage.user")
    private String user;

    private static StageProperties configuration = new StageProperties();

    private StageProperties() {
        PropertyLoader.populate(this);
    }

    public static StageProperties getInstance() {
        if (configuration == null) {
            synchronized (StageProperties.class) {
                if (configuration == null) {
                    configuration = new StageProperties();
                }
            }
        }
        return configuration;
    }
}