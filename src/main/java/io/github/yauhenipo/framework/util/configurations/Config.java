package io.github.yauhenipo.framework.util.configurations;

import lombok.Getter;

public class Config {

    @Getter
    private static BrowserProperties browserProperties = BrowserProperties.getInstance();
    @Getter
    private static FrameworkProperties frameworkProperties = FrameworkProperties.getInstance();
    @Getter
    private static StageProperties stageProperties = StageProperties.getInstance();
}
