package com.mooveit.android.dependencies;

import com.fasterxml.jackson.databind.ObjectMapper;

public interface ObjectMapperAware {
    void setObjectMapper(ObjectMapper objectMapper);
}
