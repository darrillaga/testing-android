package com.mooveit.twittertopics.utils;

import android.widget.AbsListView;

public abstract class EndlessScrollListener implements AbsListView.OnScrollListener {

    private int mItemsBeforeLoading = 4;

    public abstract void onEndReached();

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {

        if ((totalItemCount - visibleItemCount) <= (firstVisibleItem + mItemsBeforeLoading)) {

            onEndReached();
        }
    }

}
