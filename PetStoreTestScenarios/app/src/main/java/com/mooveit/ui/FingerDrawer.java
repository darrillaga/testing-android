package com.mooveit.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class FingerDrawer extends View {

    private static final float STROKE_WIDTH = 8f;
    private static final float TOUCH_TOLERANCE = 4;

    private Paint mPaint = new Paint();
    private Path mPath = new Path();

    private float lastTouchX;
    private float lastTouchY;

    private FingerDrawingStartEventsListener mFingerDrawingStartEventsListener = new DummyFingerDrawingEventsListener();
    private FingerDrawingStopEventsListener mFingerDrawingStopEventsListener = new DummyFingerDrawingEventsListener();

    {
        init();
    }

    public FingerDrawer(Context context) {
        super(context);
    }

    public FingerDrawer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FingerDrawer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FingerDrawer(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init() {
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(STROKE_WIDTH);
        mPaint.setDither(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    public Bitmap saveToBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(),
                Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);

        canvas.drawColor(Color.WHITE);
        draw(canvas);

        return bitmap;
    }

    public void clear() {
        mPath.reset();
        invalidate();
    }

    public void setOnFingerDrawingStartEventsListener(FingerDrawingStartEventsListener listener) {
        if (listener == null) {
            listener = new DummyFingerDrawingEventsListener();
        }

        mFingerDrawingStartEventsListener = listener;
    }

    public void setOnFingerDrawingStopEventsListener(FingerDrawingStopEventsListener listener) {
        if (listener == null) {
            listener = new DummyFingerDrawingEventsListener();
        }

        mFingerDrawingStopEventsListener = listener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(mPath, mPaint);
    }

    private void touchStart(float x, float y) {
        mPath.moveTo(x, y);
        lastTouchX = x;
        lastTouchY = y;

        mFingerDrawingStartEventsListener.onStartFingerDrawing(x, y);
    }

    private void touchMove(float x, float y) {
        float dx = Math.abs(x - lastTouchX);
        float dy = Math.abs(y - lastTouchY);

        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            float endPointX = (x + lastTouchX) / 2;
            float endPointY = (y + lastTouchY) / 2;

            mPath.quadTo(lastTouchX, lastTouchY, endPointX, endPointY);

            lastTouchX = x;
            lastTouchY = y;
        }
    }

    private void touchUp() {
        mPath.lineTo(lastTouchX, lastTouchY);
        mFingerDrawingStopEventsListener.onStopFingerDrawing(lastTouchX, lastTouchX);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            touchStart(x, y);
            invalidate();
            break;
        case MotionEvent.ACTION_MOVE:
            touchMove(x, y);
            invalidate();
            break;
        case MotionEvent.ACTION_UP:
            touchUp();
            invalidate();
            break;
        }
        return true;
    }

    public interface FingerDrawingStopEventsListener {
        void onStopFingerDrawing(float x, float y);
    }

    public interface FingerDrawingStartEventsListener {
        void onStartFingerDrawing(float x, float y);
    }

    private static class DummyFingerDrawingEventsListener implements
            FingerDrawingStartEventsListener, FingerDrawingStopEventsListener {

        @Override
        public void onStopFingerDrawing(float x, float y) {
            // Do nothing
        }

        @Override
        public void onStartFingerDrawing(float x, float y) {
            // Do nothing
        }
    }
}
