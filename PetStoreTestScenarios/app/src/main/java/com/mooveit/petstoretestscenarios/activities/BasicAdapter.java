package com.mooveit.petstoretestscenarios.activities;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class BasicAdapter<ITEMS_TYPE, HOLDER_TYPE extends BaseViewHolder<ITEMS_TYPE>> extends
        RecyclerView.Adapter<HOLDER_TYPE> {

    private List<ITEMS_TYPE> mItems;
    private Class<HOLDER_TYPE> mHolderClass;
    private int mLayout;

    public BasicAdapter(int layout, List<ITEMS_TYPE> items, Class<HOLDER_TYPE> holderClass) {
        mItems = items;
        mHolderClass = holderClass;
        mLayout = layout;
    }

    @Override
    public HOLDER_TYPE onCreateViewHolder(ViewGroup viewGroup, int itemViewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(mLayout, viewGroup, false);

        HOLDER_TYPE holder;

        try {
            holder = mHolderClass.getConstructor(View.class).newInstance(view);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(HOLDER_TYPE viewHolder, int position) {
        ITEMS_TYPE item = mItems.get(position);
        viewHolder.performBind(item);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

}