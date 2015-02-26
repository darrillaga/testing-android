package com.mooveit.android.testing.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class ViewUtils {

    public static TextView setText(Activity activity, int id, String text) {
        TextView textView = (TextView) activity.findViewById(id);

        textView.setText(text);

        return textView;
    }

    public static TextView setText(Activity activity, int id, int textId) {
        return setText(activity, id, activity.getString(textId));
    }

    public static int getBackgroundColor(View view) {
        // The actual color, not the id.
        int color = Color.BLACK;

        if(view.getBackground() instanceof ColorDrawable) {
            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                color = getBackgroundPreHoneycomb(view);
            }
            else {
                color = ((ColorDrawable) view.getBackground()).getColor();
            }
        }

        return color;
    }

    private static int getBackgroundPreHoneycomb(View view) {
        Bitmap bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Rect bounds = new Rect();

        // If the ColorDrawable makes use of its bounds in the draw method,
        // we may not be able to get the color we want. This is not the usual
        // case before Ice Cream Sandwich (4.0.1 r1).
        // Yet, we change the bounds temporarily, just to be sure that we are
        // successful.
        ColorDrawable colorDrawable = (ColorDrawable) view.getBackground();

        bounds.set(colorDrawable.getBounds()); // Save the original bounds.
        colorDrawable.setBounds(0, 0, 1, 1); // Change the bounds.

        colorDrawable.draw(canvas);
        int color = bitmap.getPixel(0, 0);

        colorDrawable.setBounds(bounds); // Restore the original bounds.

        return color;
    }

    public static MotionEvent createMotionEvent(int action) {
        return MotionEvent.obtain(
                System.currentTimeMillis(),
                System.currentTimeMillis() + 100,
                action,
                1, 1, 1, 1, 1, 1, 1, 1, 1
        );
    }
}