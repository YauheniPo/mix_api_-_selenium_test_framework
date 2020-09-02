package io.github.yauhenipo.framework.base.api;

import io.github.yauhenipo.framework.util.configurations.Config;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class ApiService {

    public static final String BASE_URL = Config.getStageProperties().getApiUrl();

    public Response getResponse(String baseUri, String path, Map<String, ?> parametersMap,
                                Map<String, ?> parameterNameValuePairs) {
        return RestAssured.given()
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .params(parametersMap)
                .log().all()
                .baseUri(baseUri)
                .pathParams(parameterNameValuePairs)
                .get(path);
    }

    public Response getResponse(String path, Map<String, ?> parametersMap, Map<String, ?> parameterNameValuePairs) {
        return getResponse(BASE_URL, path, parametersMap, parameterNameValuePairs);
    }

    public Response response(String baseUri, String path, Map<String, ?> parametersMap,
                             Map<String, ?> parameterNameValuePairs) {
        Response response = getResponse(baseUri, path, parametersMap, parameterNameValuePairs);
        return response
                .then().log().all().statusCode(200).extract()
                .response();
    }

    public Response response(String path, Map<String, ?> parametersMap, Map<String, ?> parameterNameValuePairs) {
        return response(BASE_URL, path, parametersMap, parameterNameValuePairs);
    }

    public Response response(String path, Map<String, ?> parametersMap) {
        return response(path, parametersMap, new HashMap<>());
    }

    public Response response(String path) {
        return response(path, new HashMap<>(), new HashMap<>());
    }

    public Response response(String baseUri, String path) {
        return response(baseUri, path, new HashMap<>(), new HashMap<>());
    }
}
