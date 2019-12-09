package io.github.yauhenipo.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RepositoryApiData {

    private long id;
    private String url;
    private String fullName;
}

