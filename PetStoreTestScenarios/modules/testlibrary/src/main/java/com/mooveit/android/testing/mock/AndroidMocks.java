package com.mooveit.android.testing.mock;

import android.content.Context;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

public class AndroidMocks {

    public static Context mockContext(final Context context) {
        return generalDelegate(Context.class, context);
    }

    public static <DELEGATED> DELEGATED generalDelegate(Class<DELEGATED> clazz, final Object delegated) {
        return (DELEGATED) mock(clazz, withSettings().defaultAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return invocationOnMock.getMethod().invoke(
                        delegated, invocationOnMock.getArguments()
                );
            }
        }));
    }

    public static <DELEGATED> DELEGATED generalDelegate(DELEGATED delegated) {
        return generalDelegate((Class<DELEGATED>) delegated.getClass(), delegated);
    }
}
