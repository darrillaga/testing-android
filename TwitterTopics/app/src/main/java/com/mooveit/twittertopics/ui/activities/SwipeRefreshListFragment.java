package com.mooveit.twittertopics.ui.activities;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

public class SwipeRefreshListFragment extends ListFragment
        implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        setUpSwipeRefreshLayout(view);

        return view;
    }

    private void setUpSwipeRefreshLayout(View view) {
        AbsListView absListView = (AbsListView) view.findViewById(android.R.id.list);
        ViewGroup parent = (ViewGroup) absListView.getParent();

        int index = parent.indexOfChild(absListView);
        parent.removeView(absListView);

        mSwipeRefreshLayout = new SwipeRefreshLayout(getActivity());
        mSwipeRefreshLayout.setLayoutParams(
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
                )
        );

        mSwipeRefreshLayout.addView(absListView);

        parent.addView(mSwipeRefreshLayout, index);

        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    public void setRefreshing(boolean isRefreshing) {
        mSwipeRefreshLayout.setRefreshing(isRefreshing);
    }

    @Override
    public void onRefresh() {
        setRefreshing(false);
    }
}
