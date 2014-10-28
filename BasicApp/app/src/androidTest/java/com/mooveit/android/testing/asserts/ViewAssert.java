package com.mooveit.android.testing.asserts;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import junit.framework.Assert;

public class ViewAssert {

    /**
     * Assert two bitmaps are equals acording to
     * {@link android.graphics.Bitmap#sameAs(android.graphics.Bitmap)}
     * @param a
     * @param b
     */
    public static void assertEqualBitmap(Bitmap a, Bitmap b) {
        Assert.assertTrue(a.sameAs(b));
    }

    /**
     * @see .assertEqualBitmap(Bitmap, Bitmap)
     *
     * The first bitmap is taken from the ImageView
     */
    public static void assertEqualBitmap(ImageView iv, Bitmap bitmap) {
        Bitmap imageBitmap = ((BitmapDrawable) iv.getDrawable()).getBitmap();
        Assert.assertNotNull(imageBitmap);
        assertEqualBitmap(imageBitmap, bitmap);
    }
}
