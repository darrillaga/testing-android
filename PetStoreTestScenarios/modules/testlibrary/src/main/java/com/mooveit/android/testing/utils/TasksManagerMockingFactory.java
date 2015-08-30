package com.mooveit.android.testing.utils;

import com.mooveit.android.networking.helpers.TasksManagerFactory;
import com.mooveit.android.networking.robospice.mock.MockResponseObjectsProvider;
import com.mooveit.android.networking.robospice.mock.ServerMock;
import com.mooveit.android.services.TasksService;
import com.octo.android.robospice.SpiceManager;

public class TasksManagerMockingFactory extends TasksManagerFactory {

    private MockResponseObjectsProvider mMockResponseObjectsProvider;

    public TasksManagerMockingFactory(MockResponseObjectsProvider mockResponseObjectsProvider) {
        mMockResponseObjectsProvider = mockResponseObjectsProvider;
    }

    @Override
    public SpiceManager getTasksManagerInstance() {
        return ServerMock.mockSpiceManager(new SpiceManager(TasksService.class), mMockResponseObjectsProvider,
                TasksService.class.getCanonicalName());
    }
}