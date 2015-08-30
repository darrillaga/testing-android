package com.mooveit.android.networking;

import android.content.Context;

import com.mooveit.android.Application;
import com.mooveit.android.networking.converter.BaseEntityToFormUrlEncodedMessageConverter;

import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RestTemplateBuilder {

    private static final int WEB_SERVICES_CONNECT_TIMEOUT = 60000;
    private static final int WEB_SERVICES_READ_TIMEOUT = 60000;

    private static final String UTF8_CHARSET_NAME = "UTF-8";

    public static RestTemplate build(Context context) {
        RestTemplate restTemplate = new RestTemplate();

        MappingJackson2HttpMessageConverter jsonConverter = createJsonConverter(context);

        final List<HttpMessageConverter<?>> listHttpMessageConverters =
                restTemplate.getMessageConverters();

        listHttpMessageConverters.add(createStringConverter());
        listHttpMessageConverters.add(jsonConverter);
        listHttpMessageConverters.add(createFormDataWriter());


        restTemplate.setMessageConverters(listHttpMessageConverters);

        manageTimeOuts(restTemplate);

        return restTemplate;
    }

    private static StringHttpMessageConverter createStringConverter() {
        Charset charset = Charset.forName(UTF8_CHARSET_NAME);

        return new StringHttpMessageConverter(charset, Arrays.asList(charset));
    }

    private static MappingJackson2HttpMessageConverter createJsonConverter(Context context) {
        MappingJackson2HttpMessageConverter jsonConverter =
                new MappingJackson2HttpMessageConverter();

        List supportedTypes = new ArrayList();
        supportedTypes.add(MediaType.APPLICATION_JSON);
        jsonConverter.setSupportedMediaTypes(supportedTypes);

        jsonConverter.setObjectMapper(Application.get(context).getObjectMapper());

        return jsonConverter;
    }

    private static BaseEntityToFormUrlEncodedMessageConverter createFormDataWriter() {
        return new BaseEntityToFormUrlEncodedMessageConverter();
    }

    private static void manageTimeOuts(RestTemplate restTemplate) {
        ClientHttpRequestFactory factory = restTemplate.getRequestFactory();

        if (factory instanceof HttpComponentsClientHttpRequestFactory) {
            setTimeoutForRequestFactory((HttpComponentsClientHttpRequestFactory) factory);
        } else if (factory instanceof SimpleClientHttpRequestFactory) {
            setTimeoutForRequestFactory((SimpleClientHttpRequestFactory) factory);
        }
    }

    private static void setTimeoutForRequestFactory
            (HttpComponentsClientHttpRequestFactory advancedFactory) {

        advancedFactory.setConnectTimeout(WEB_SERVICES_CONNECT_TIMEOUT);
        advancedFactory.setReadTimeout(WEB_SERVICES_READ_TIMEOUT);
    }

    private static void setTimeoutForRequestFactory(SimpleClientHttpRequestFactory advancedFactory) {
        advancedFactory.setConnectTimeout(WEB_SERVICES_CONNECT_TIMEOUT);
        advancedFactory.setReadTimeout(WEB_SERVICES_READ_TIMEOUT);
    }
}
