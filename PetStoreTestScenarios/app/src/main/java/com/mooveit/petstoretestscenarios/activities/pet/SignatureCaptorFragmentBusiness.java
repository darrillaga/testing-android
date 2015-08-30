package com.mooveit.petstoretestscenarios.activities.pet;

import android.graphics.Bitmap;
import android.net.Uri;

import com.mooveit.android.tasks.TaskListener;
import com.mooveit.petstoretestscenarios.tasks.StoreSignatureByteArrayTask;
import com.octo.android.robospice.SpiceManager;

class SignatureCaptorFragmentBusiness {

    private Interactions mInteractions;

    public SignatureCaptorFragmentBusiness(Interactions interactions) {
        mInteractions = interactions;
    }

    public void savePicture(Bitmap signature) {

        StoreSignatureByteArrayTask storeSignatureByteArrayTask =
                new StoreSignatureByteArrayTask(signature);

        mInteractions.getTasksManager().execute(storeSignatureByteArrayTask, new TaskListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                mInteractions.onCreatePictureSuccess(uri);
            }

            @Override
            public void onError(Exception e) {
                mInteractions.notifyError(e);
            }
        });
    }

    public interface Interactions {
        void onCreatePictureSuccess(Uri uri);
        void notifyError(Exception exception);
        SpiceManager getTasksManager();
    }
}
