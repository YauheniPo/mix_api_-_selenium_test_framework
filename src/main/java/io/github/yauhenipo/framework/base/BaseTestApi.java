package io.github.yauhenipo.framework.base;

import io.github.yauhenipo.framework.util.configurations.Config;
import io.restassured.RestAssured;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.BeforeSuite;

@Log4j2
public abstract class BaseTestApi extends BaseEntity {

    @BeforeSuite
    public void beforeSuite() {
        RestAssured.baseURI = Config.getStageProperties().getApiUrl();
        RestAssured.basePath = "/";
    }

}
