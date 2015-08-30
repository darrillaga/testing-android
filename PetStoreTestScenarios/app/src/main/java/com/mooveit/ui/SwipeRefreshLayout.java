package com.mooveit.ui;

import android.content.Context;
import android.util.AttributeSet;

public class SwipeRefreshLayout extends android.support.v4.widget.SwipeRefreshLayout {

    public SwipeRefreshLayout(Context context) {
        super(context);
    }

    public SwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
        // Pass it up to our parent
        if (getParent() != null) {
            getParent().requestDisallowInterceptTouchEvent(disallowIntercept);
        }
    }

     private boolean mMeasured = false;
     private boolean mPreMeasureRefreshing = false;

     @Override
     public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
         super.onMeasure(widthMeasureSpec, heightMeasureSpec);
         if (!mMeasured) {
             mMeasured = true;
             setRefreshing(mPreMeasureRefreshing);
         }
     }

     @Override
     public void setRefreshing(boolean refreshing) {
         if (mMeasured) {
             super.setRefreshing(refreshing);
         } else {
             mPreMeasureRefreshing = refreshing;
         }
     }
}
