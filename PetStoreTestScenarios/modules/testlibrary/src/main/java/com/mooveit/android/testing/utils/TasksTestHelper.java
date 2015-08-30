package com.mooveit.android.testing.utils;

import com.mooveit.android.tasks.BaseTask;
import com.mooveit.android.testing.NonMockedRequestException;

import java.util.HashSet;
import java.util.Set;

public class TasksTestHelper {

    // Set of tasks classes that do not need to be mocked
    private static final Set<Class<? extends BaseTask>> callableTasks = new HashSet<Class<? extends BaseTask>>();

    public static Object defaultResultFor(BaseTask task) throws Exception {
        Object result = null;

        if (result == null && !callableTasks.contains(task.getClass())) {
            throw new NonMockedRequestException(task);
        }

        return result;
    }
}
