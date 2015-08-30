package com.mooveit.android.testing.testrules;

import android.app.Activity;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.util.Log;

import com.mooveit.android.Application;
import com.mooveit.android.networking.helpers.SpiceManagerInstanceProvider;
import com.mooveit.android.networking.robospice.mock.MockResponseObjectsProvider;
import com.mooveit.android.networking.robospice.mock.ServerMock;
import com.mooveit.android.services.NetworkingService;
import com.mooveit.android.tasks.BaseTask;
import com.mooveit.android.testing.NonMockedRequestException;
import com.mooveit.android.testing.utils.EspressoUtils;
import com.mooveit.android.testing.utils.RequestTestHelper;
import com.mooveit.android.testing.utils.TasksManagerMockingFactory;
import com.mooveit.android.testing.utils.TasksTestHelper;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.request.SpiceRequest;
import com.octo.android.robospice.request.listener.RequestListener;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.HashMap;
import java.util.Map;

public class SpicedTestRule<T extends Activity> implements TestRule, SpiceManagerInstanceProvider, MockResponseObjectsProvider {

    private IntentsTestRule<T> intentsTestRule;

    private Map<Class<? extends SpiceRequest>, Object> mockResponses = new HashMap<>();

    private Map<Class<? extends BaseTask>, Runnable> spiedTasks = new HashMap<>();

    public SpicedTestRule(Class<T> clazz) {
        intentsTestRule = buildTestRule(clazz);
    }

    public T getActivity() {
        return intentsTestRule.getActivity();
    }

    protected void beforeActivityLaunched() {
    }

    protected void afterActivityFinished() {
    }

    protected void afterActivityLaunched() {
    }

    @Override
    public Statement apply(Statement base, Description description) {
        Statement wrapper = new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    base.evaluate();
                } finally {
                    EspressoUtils.unregisterIdlingResources();
                }
            }
        };

        return intentsTestRule.apply(wrapper, description);
    }

    @Override
    public SpiceManager provideSpiceManager() {
        return ServerMock.mockSpiceManager(
                new SpiceManager(NetworkingService.class),
                this,
                NetworkingService.class.getCanonicalName()
        );
    }

    @Override
    public Object getResponseFor(SpiceRequest request, RequestListener requestListener) throws Exception {
        if (request instanceof BaseTask) {
            return getResultForTask((BaseTask) request);
        } else {
            return getResponseForRequest(request, requestListener);
        }
    }

    public void spyTask(Class<? extends BaseTask> taskClass, Runnable runnable) {
        spiedTasks.put(taskClass, runnable);
    }

    private Object getResultForTask(BaseTask task) throws Exception {
        Runnable spy = spiedTasks.get(task.getClass());

        if (spy != null) {
            spy.run();
        }

        return TasksTestHelper.defaultResultFor(task);
    }

    private Object getResponseForRequest(SpiceRequest request, RequestListener requestListener) throws Exception {
        Object response = mockResponses.get(request.getClass());

        if (response == null) {
            response = RequestTestHelper.defaultRequestFor(request, requestListener);
        }

        if (response == null) {
            throw new NonMockedRequestException(request);
        }

        RequestTestHelper.injectDependencies(request, requestListener, InstrumentationRegistry.getTargetContext());

        return response;
    }

    private IntentsTestRule<T> buildTestRule(Class<T> clazz) {
        return new IntentsTestRule<T>(clazz) {
            @Override
            protected void beforeActivityLaunched() {
                super.beforeActivityLaunched();

                Application.get(InstrumentationRegistry.getInstrumentation().getTargetContext()).getSpiceManagerFactory().
                        setSpiceManagerInstanceProvider(SpicedTestRule.this);

                Application.get(InstrumentationRegistry.getInstrumentation().getTargetContext()).
                        setTasksManagerFactory(new TasksManagerMockingFactory(SpicedTestRule.this));

                SpicedTestRule.this.beforeActivityLaunched();
            }

            @Override
            protected Intent getActivityIntent() {
                Intent intent = SpicedTestRule.this.getActivityIntent();

                if (intent == null) {
                    intent = super.getActivityIntent();
                }

                return intent;
            }

            @Override
            protected void afterActivityFinished() {
                try {
                    super.afterActivityFinished();
                } catch (Exception e) {
                    Log.e(null, e.getMessage(), e);
                }

                SpicedTestRule.this.afterActivityFinished();
            }

            @Override
            protected void afterActivityLaunched() {
                super.afterActivityLaunched();
                SpicedTestRule.this.afterActivityLaunched();
            }
        };
    }

    public void registerResponseFor(Class<? extends SpiceRequest> getItemCategoriesRequestClass, Object response) {
        mockResponses.put(getItemCategoriesRequestClass, response);
    }

    protected Intent getActivityIntent() {
        return null;
    }
}

