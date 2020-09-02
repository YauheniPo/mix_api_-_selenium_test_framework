package io.github.yauhenipo.framework.base.api;

import io.github.yauhenipo.framework.base.BaseEntity;
import io.restassured.RestAssured;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.BeforeSuite;

@Log4j2
public abstract class BaseTestApi extends BaseEntity {

    protected ApiService apiService = new ApiService();

    @BeforeSuite
    public void beforeSuite() {
        RestAssured.baseURI = apiService.BASE_URL;
        RestAssured.basePath = "/";
    }
}
