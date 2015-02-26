package com.mooveit.twittertopics.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.mooveit.twittertopics.R;
import com.mooveit.twittertopics.entities.Tweet;
import com.mooveit.twittertopics.utils.TwitterHelper;
import com.squareup.picasso.Picasso;

public class TweetAdapter extends ArrayAdapter<Tweet> {

    private final Context mContext;
    private LayoutInflater mInflater;

    protected static class ViewHolder {
        TextView tweetTextView;
        TextView screenNameTextView;
        TextView userNameTextView;
        ImageView imageNetImageView;
    }

    public TweetAdapter(Context context, List<Tweet> tweets) {
        super(context, R.layout.layout_item_tweets_relative, tweets);
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        View viewToUse = null;

        if (view == null) {
            viewToUse = mInflater.inflate(R.layout.layout_item_tweets_relative, null);
            holder = createView(viewToUse);
            viewToUse.setTag(holder);
        } else {
            viewToUse = view;
            holder = (ViewHolder) viewToUse.getTag();
        }
        setViewDataTweet(holder, i);
        return viewToUse;
    }

    private ViewHolder createView(View viewToUse) {
        ViewHolder holder = new ViewHolder();
        holder.userNameTextView = (TextView) viewToUse.findViewById(R.id.user_name);
        holder.screenNameTextView = (TextView) viewToUse.findViewById(R.id.user_alias);
        holder.tweetTextView = (TextView) viewToUse.findViewById(R.id.text);
        holder.imageNetImageView = (ImageView) viewToUse.findViewById(R.id.profile_image);
        return holder;
    }

    private void setViewDataTweet(final ViewHolder holder, int position) {
        Tweet tweet= getItem(position);
        holder.userNameTextView.setText(tweet.getUser().getName());
        holder.screenNameTextView.setText(tweet.getUser().getName());
        holder.tweetTextView.setText(tweet.getText());

        Picasso.with(mContext).
                load(tweet.getUser().getProfileImageUrl()).
                transform(TwitterHelper.getInstance().getRoundedTransform()).
                into(holder.imageNetImageView);
    }

}
