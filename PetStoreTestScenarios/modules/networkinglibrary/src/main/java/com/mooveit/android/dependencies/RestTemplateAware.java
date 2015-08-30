package com.mooveit.android.dependencies;

import org.springframework.web.client.RestTemplate;

public interface RestTemplateAware {
    void setRestTemplate(RestTemplate restTemplate);
}
