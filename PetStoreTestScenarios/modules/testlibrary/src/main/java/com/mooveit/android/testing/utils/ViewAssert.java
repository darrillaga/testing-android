package com.mooveit.android.testing.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import junit.framework.Assert;

public class ViewAssert {

    public static void assertEqualBitmap(Bitmap a, Bitmap b) {
        Assert.assertTrue(a.sameAs(b));
    }

    public static void assertEqualBitmap(ImageView iv, Bitmap bitmap) {
        Bitmap imageBitmap = ((BitmapDrawable) iv.getDrawable()).getBitmap();
        Assert.assertNotNull(imageBitmap);
        assertEqualBitmap(imageBitmap, bitmap);
    }
}
