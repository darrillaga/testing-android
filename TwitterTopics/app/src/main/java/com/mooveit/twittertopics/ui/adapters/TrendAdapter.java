package com.mooveit.twittertopics.ui.adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mooveit.twittertopics.R;
import com.mooveit.twittertopics.entities.Trend;
import com.mooveit.twittertopics.utils.TwitterHelper;

import java.util.ArrayList;
import java.util.List;

public class TrendAdapter extends ArrayAdapter<Trend> {

    private Context mContext;
    private LayoutInflater mInflater;


    protected static class ViewHolder {
        TextView trendTextView;
        TextView sharpTextView;
    }

    public TrendAdapter(Context context, List<Trend> trends) {
        super(context, R.layout.layout_item_trends_relative, trends);
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        View viewToUse = null;

        if (convertView == null) {
            viewToUse = mInflater.inflate(R.layout.layout_item_trends_relative, null);
            holder = createView(viewToUse);
            viewToUse.setTag(holder);
        } else {
            viewToUse = convertView;
            holder = (ViewHolder) viewToUse.getTag();
        }
        setViewDataTrend(holder, position);
        return viewToUse;
    }

    private ViewHolder createView(View viewToUse) {
        ViewHolder holder = new ViewHolder();
        holder.trendTextView = (TextView) viewToUse.findViewById(R.id.name);
        holder.sharpTextView = (TextView) viewToUse.findViewById(R.id.sharp);
        return holder;
    }

    private void setViewDataTrend(final ViewHolder holder, int position) {
        Trend trend = getItem(position);
        holder.trendTextView.setText(trend.getTrend());
        ((GradientDrawable)holder.sharpTextView.getBackground()).setColor(
               TwitterHelper.getInstance().setTrendCircleColor(position));
    }
}
