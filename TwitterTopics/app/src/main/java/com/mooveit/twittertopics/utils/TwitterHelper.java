package com.mooveit.twittertopics.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.util.Base64;

import java.util.HashMap;

import com.mooveit.twittertopics.R;
import com.squareup.picasso.Transformation;

import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriComponentsBuilder;

public class TwitterHelper {

    private static TwitterHelper instance;
    private Context mContext;
    private final Transformation mRoundedTransformation = new Transformation() {

        @Override
        public Bitmap transform(Bitmap source) {
            Bitmap newBitmap = getRoundedBitmap(source);
            source.recycle();

            return newBitmap;
        }

        @Override
        public String key() {
            return "RoundedTransformation";
        }
    };

    private TwitterHelper(Context ctx) {
        mContext = ctx;
    }

    public static TwitterHelper getInstance() {
        return instance;
    }

    public static void init(Context context) {
        if (instance == null) {
           instance = new TwitterHelper(context);
        }
    }

    public static void setInstance(TwitterHelper twitterInstance) {
        instance = twitterInstance;
    }

    public String getBaseUrl() {
        Uri.Builder builder = new Uri.Builder();

        builder.scheme("https")
                .authority(mContext.getString(R.string.url_twitter_api))
                .appendPath(mContext.getString(R.string.twitter_api_version));

        return builder.build().toString();
    }

    public String getTokenUrl() {
        return mContext.getString(R.string.oauthUrl);
    }

    public int setTrendCircleColor(int position) {

        int returnColor = 0;
        switch (position % 10) {
            case 0:
                returnColor =  mContext.getResources().getColor(R.color.coral);
                break;
            case 1:
                returnColor = mContext.getResources().getColor(R.color.coral_darken);
                break;
            case 2:
                returnColor = mContext.getResources().getColor(R.color.purple_light);
                break;
            case 3:
                returnColor = mContext.getResources().getColor(R.color.purple_darken);
                break;
            case 4:
                returnColor = mContext.getResources().getColor(R.color.blue_light);
                break;
            case 5:
                returnColor = mContext.getResources().getColor(R.color.blue_darken);
                break;
            case 6:
                returnColor = mContext.getResources().getColor(R.color.green_light);
                break;
            case 7:
                returnColor = mContext.getResources().getColor(R.color.green_darken);
                break;
            case 8:
                returnColor = mContext.getResources().getColor(R.color.coral);
                break;
            case 9:
                returnColor = mContext.getResources().getColor(R.color.coral_darken);
                break;
        }
        return returnColor;
    }

    public Transformation getRoundedTransform() {
        return mRoundedTransformation;
    }

    public Bitmap getRoundedBitmap(Bitmap bitmap) {
        Bitmap output = null;
        if (bitmap != null) {
            output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            final Canvas canvas = new Canvas(output);

            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            final RectF rectF = new RectF(rect);

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            canvas.drawOval(rectF, paint);

            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);
        }

        return output;
    }
}
