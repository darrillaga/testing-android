package com.mooveit.petstoretestscenarios.tasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.mooveit.android.dependencies.ContextAware;
import com.mooveit.android.tasks.BaseTask;
import com.mooveit.petstoretestscenarios.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class StoreSignatureByteArrayTask extends BaseTask<Uri> implements ContextAware {

    private Context mContext;
    private Bitmap mSignature;

    public StoreSignatureByteArrayTask(Bitmap signature) {
        super(Uri.class);
        mSignature = signature;
    }

    @Override
    public Uri perform() {
        int maxWidth = mContext.getResources().getInteger(R.integer.max_signature_width);

        if (mSignature.getWidth() > maxWidth) {
            scaleSignature(maxWidth);
        }

        String path = MediaStore.Images.Media.insertImage(mContext.getContentResolver(), mSignature,
                "signature", "signature");

        return Uri.parse(path);
    }

    private void scaleSignature(int maxWidth) {
        Bitmap scaledSignature = Bitmap.
                createScaledBitmap(
                        mSignature, maxWidth,
                        mSignature.getHeight() * maxWidth / mSignature.getWidth(),
                        false
                );

        mSignature = scaledSignature;
    }

    @Override
    public void setContext(Context context) {
        mContext = context;
    }
}
