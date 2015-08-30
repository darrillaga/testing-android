package com.mooveit.android.networking.converter;

import com.mooveit.android.networking.helpers.ObjectParamsCreator;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.Assert;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BaseEntityToFormUrlEncodedMessageConverter implements HttpMessageConverter<Object> {

    private Charset charset = Charset.forName("UTF-8");

    private List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();

    private List<HttpMessageConverter<?>> partConverters = new ArrayList<HttpMessageConverter<?>>();


    public BaseEntityToFormUrlEncodedMessageConverter() {
        this.supportedMediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);

        this.partConverters.add(new ByteArrayHttpMessageConverter());
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
        stringHttpMessageConverter.setWriteAcceptCharset(false);
        this.partConverters.add(stringHttpMessageConverter);
        this.partConverters.add(new ResourceHttpMessageConverter());
    }

    /**
     * Set the message body converters to use. These converters are used to convert objects to MIME parts.
     */
    public final void setPartConverters(List<HttpMessageConverter<?>> partConverters) {
        Assert.notEmpty(partConverters, "'partConverters' must not be empty");
        this.partConverters = partConverters;
    }

    /**
     * Add a message body converter. Such a converters is used to convert objects to MIME parts.
     */
    public final void addPartConverter(HttpMessageConverter<?> partConverter) {
        Assert.notNull(partConverter, "'partConverter' must not be NULL");
        this.partConverters.add(partConverter);
    }

    /**
     * Sets the character set used for writing form data.
     */
    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        return false;
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        if (mediaType.equals(MediaType.APPLICATION_FORM_URLENCODED)) {
            return true;
        }

        return false;
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return Collections.unmodifiableList(this.supportedMediaTypes);
    }

    @Override
    public Object read(Class<? extends Object> clazz, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {

        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void write(Object object, MediaType contentType,
                      HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {

        writeForm(object, contentType, outputMessage);
    }

    private void writeForm(Object object, MediaType contentType, HttpOutputMessage outputMessage)
            throws IOException {
        outputMessage.getBody().write(ObjectParamsCreator.getInstance().
                queryParamsFromObject("", object, true).getBytes());
    }

}
