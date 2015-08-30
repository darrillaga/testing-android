package com.mooveit.android.dependencies;

import android.content.Context;

import com.mooveit.android.Application;

public class Injector {

    public static void inject(Object o, Context context) {
        injectContext(o, context);

        injectRestTemplate(o, context);

        injectObjectMapper(o, context);
    }

    private static void injectRestTemplate(Object o, Context context) {
        if (o instanceof RestTemplateAware) {
            RestTemplateAware restTemplateAware = (RestTemplateAware) o;
            restTemplateAware.setRestTemplate(Application.get(context).getRestTemplate());
        }
    }

    private static void injectContext(Object o, Context context) {
        if (o instanceof ContextAware) {
            ContextAware contextAware = (ContextAware) o;
            contextAware.setContext(context);
        }
    }

    private static void injectObjectMapper(Object o, Context context) {
        if (o instanceof ObjectMapperAware) {
            ObjectMapperAware objectMapperAware = (ObjectMapperAware) o;

            objectMapperAware.setObjectMapper(Application.get(context).getObjectMapper());
        }
    }

    public static <T extends Iterable<? extends Object>> void injectOverObjects(T objects, Context context) {
        for (Object object : objects) {
            inject(object, context);
        }
    }
}
