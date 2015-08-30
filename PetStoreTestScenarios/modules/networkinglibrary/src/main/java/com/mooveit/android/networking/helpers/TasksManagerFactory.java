package com.mooveit.android.networking.helpers;

import com.mooveit.android.services.TasksService;
import com.octo.android.robospice.SpiceManager;

public class TasksManagerFactory {

    public SpiceManager getTasksManagerInstance() {
        return new SpiceManager(TasksService.class);
    }
}
