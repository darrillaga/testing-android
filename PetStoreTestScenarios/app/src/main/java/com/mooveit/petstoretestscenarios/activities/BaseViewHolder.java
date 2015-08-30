package com.mooveit.petstoretestscenarios.activities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class BaseViewHolder<T>
        extends RecyclerView.ViewHolder {

    private Context mContext;
    private T mCurrentItem;

    public BaseViewHolder(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
    }

    /**
     * Perform the item object and views binding
     * @param item
     */
    public void performBind(T item) {
        mCurrentItem = item;
        onBind(item);
    }

    /**
     * Event callback when a bind is performed
     * @param item
     */
    public abstract void onBind(T item);

    /**
     * Return the last binded item
     * @return
     */
    public T getCurrentItem() {
        return mCurrentItem;
    }

    /**
     * Return the view context
     * @return
     */
    protected Context getContext() {
        return mContext;
    }
}
