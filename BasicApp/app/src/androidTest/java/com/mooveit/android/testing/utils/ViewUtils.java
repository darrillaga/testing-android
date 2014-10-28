package com.mooveit.android.testing.utils;

import android.app.Activity;
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
}
