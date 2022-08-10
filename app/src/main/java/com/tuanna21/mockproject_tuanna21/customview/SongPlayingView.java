package com.tuanna21.mockproject_tuanna21.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.tuanna21.mockproject_tuanna21.R;

public class SongPlayingView extends View {
    //constants
    private final int PROGRESS_BAR_WIDTH = 10;
    private final int MARGIN_BORDER = 15;
    private final int PROGRESS_BAR_THUMB_WIDTH = 16;
    private final int THUMB_RADIUS = PROGRESS_BAR_THUMB_WIDTH / 2;

    //Paint
    private Paint mProgressBarDefaultPaint;
    private Paint mProgressBarPaint;
    private Paint mProgressBarThumbPaint;
    private Paint mViewPaint;
    //Rect
    private RectF mProgressBarRect;
    private RectF mProgressBarThumbRect;
    private RectF mViewRect;
    //Image
    private Bitmap mImage;
    private boolean mAvailableImage = false;
    //Variables
    private int mViewSize;
    private int mViewRadius;
    private int mCenterX, mCenterY;
    private int mX = 0, mY = 0;
    private int mMaxProgress;
    private int progress;
    private boolean mIsSeeking;

    //Listener
    private ProgressChangeListener mProgressChangedListener;

    public SongPlayingView(Context context) {
        super(context);
        initPaint();
        initRect();
    }

    public SongPlayingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        initRect();
    }

    public SongPlayingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        initRect();
    }

    public SongPlayingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initPaint();
        initRect();
    }

    public void setImage(Bitmap image) {
        this.mImage = image;
        mAvailableImage = true;
        invalidate();
        requestLayout();
    }

    public void setProgressChangedListener(ProgressChangeListener listener) {
        this.mProgressChangedListener = listener;
    }

    public void setMaxProgressAndBackToStart(int maxProgress) {
        this.mMaxProgress = maxProgress;
        this.progress = 0;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        if (mMaxProgress != 0 && !mIsSeeking) {
            double mAngle = (double) (((double) this.progress / ((double) mMaxProgress)) * 360);
            calculatePositionFromAngle(mAngle);
            invalidate();
            requestLayout();
        }
    }

    private void initRect() {
        mProgressBarRect = new RectF();
        mProgressBarThumbRect = new RectF();
        mViewRect = new RectF();
    }

    private void initPaint() {
        mProgressBarDefaultPaint = new Paint();
        mProgressBarDefaultPaint.setColor(ContextCompat.getColor(getContext(), R.color.custom_progressbar_default_color));
        mProgressBarDefaultPaint.setStyle(Paint.Style.STROKE);
        mProgressBarDefaultPaint.setStrokeWidth(PROGRESS_BAR_WIDTH);
        mProgressBarDefaultPaint.setAntiAlias(true);

        mProgressBarThumbPaint = new Paint();
        mProgressBarThumbPaint.setColor(ContextCompat.getColor(getContext(), R.color.custom_progressbar_progress_color));
        mProgressBarThumbPaint.setStyle(Paint.Style.STROKE);
        mProgressBarThumbPaint.setStrokeWidth(PROGRESS_BAR_THUMB_WIDTH);
        mProgressBarThumbPaint.setAntiAlias(true);

        mViewPaint = new Paint();
        mViewPaint.setColor(ContextCompat.getColor(getContext(), R.color.main_theme_color));
        mViewPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mViewPaint.setStrokeWidth(PROGRESS_BAR_THUMB_WIDTH);
        mViewPaint.setAntiAlias(true);

        mProgressBarPaint = new Paint();
        mProgressBarPaint.setColor(ContextCompat.getColor(getContext(), R.color.custom_progressbar_progress_color));
        mProgressBarPaint.setStyle(Paint.Style.STROKE);
        mProgressBarPaint.setStrokeWidth(PROGRESS_BAR_THUMB_WIDTH);
        mProgressBarPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mDesiredWidth = MeasureSpec.getSize(widthMeasureSpec);
        int mDesiredHeight = MeasureSpec.getSize(heightMeasureSpec);
        mViewSize = Math.max(
                mDesiredWidth,
                mDesiredHeight
        );
        mViewRadius = mViewSize / 2 - MARGIN_BORDER - PROGRESS_BAR_WIDTH / 2;
        mCenterX = mCenterY = mViewSize / 2;
        if (mX == 0 && mY == 0) {
            mX = mCenterX;
            mY = MARGIN_BORDER + PROGRESS_BAR_WIDTH / 2;
        }

        mProgressBarRect.set(
                mCenterX - mViewRadius,
                mCenterX - mViewRadius,
                mCenterX + mViewRadius,
                mCenterX + mViewRadius
        );

        mProgressBarThumbRect.set(
                mX - THUMB_RADIUS,
                mY - THUMB_RADIUS,
                mX + THUMB_RADIUS,
                mY + THUMB_RADIUS
        );

        mViewRect.set(0, 0, mViewSize, mViewSize);

        setMeasuredDimension(
                mViewSize,
                mViewSize
        );
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(mViewRect, mViewPaint);
        if (mAvailableImage) {
            canvas.drawBitmap(mImage, null, mProgressBarRect, null);
        }
        canvas.drawArc(mProgressBarRect, 0, 360, false, mProgressBarDefaultPaint);
        canvas.drawArc(mProgressBarRect, 270, (int) calculateAngle(mX, mY), false, mProgressBarPaint);
        canvas.drawArc(mProgressBarThumbRect, 0, 360, true, mProgressBarThumbPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();
                float distanceTouchToCenter = (float) Math.sqrt((x - mCenterX) * (x - mCenterX) + (y - mCenterY) * (y - mCenterY));

                mIsSeeking = distanceTouchToCenter > mViewRadius - PROGRESS_BAR_WIDTH * 2
                        && distanceTouchToCenter < mViewRadius + PROGRESS_BAR_WIDTH * 2;
                if (mIsSeeking) {
                    handleActionMove(event);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mIsSeeking)
                    handleActionMove(event);
                break;
            case MotionEvent.ACTION_UP:
                handleActionUp();
                break;
        }

        invalidate();
        return true;
    }

    private void handleActionUp() {
        mIsSeeking = false;
        if (mProgressChangedListener != null) {
            int angle = (int) (calculateAngle(mX, mY) / 360 * mMaxProgress);
            mProgressChangedListener.onProgressChanged(angle);
        }
    }

    private void handleActionMove(MotionEvent event) {
        float mPositionX = event.getX();
        float mPositionY = event.getY();

        double mAngle = calculateAngle(mPositionX, mPositionY);
        progress = (int) (mAngle / 360 * mMaxProgress);

        calculatePositionFromAngle(mAngle);

        mProgressBarThumbRect.set(
                mX - THUMB_RADIUS,
                mY - THUMB_RADIUS,
                mX + THUMB_RADIUS,
                mY + THUMB_RADIUS
        );

    }

    private void calculatePositionFromAngle(double mAngle) {
        double mCos = Math.cos(mAngle / 180 * Math.PI);
        double mSin = Math.sin(mAngle / 180 * Math.PI);

        if (mCos == 0) {
            mY = mCenterY;
            if (mSin > 0) {
                mX = mCenterX + mViewRadius;
            } else {
                mX = mCenterX - mViewRadius;
            }
        } else if (mSin == 0) {
            mX = mCenterX;
            if (mCos > 0) {
                mY = mCenterY - mViewRadius;
            } else {
                mY = mCenterY + mViewRadius;
            }
        } else {
            if (mCos > 0 && mSin > 0) {
                mY = (int) (mCenterY - mViewRadius * mCos);
                mX = (int) (mCenterX + mViewRadius * mSin);
            } else if (mCos > 0 && mSin < 0) {
                mY = (int) (mCenterY - Math.abs(mViewRadius * mCos));
                mX = (int) (mCenterX - Math.abs(mViewRadius * mSin));
            } else if (mCos < 0 && mSin > 0) {
                mY = (int) (mCenterY + Math.abs(mViewRadius * mCos));
                mX = (int) (mCenterX + Math.abs(mViewRadius * mSin));
            } else {
                mY = (int) (mCenterY + Math.abs(mViewRadius * mCos));
                mX = (int) (mCenterX - Math.abs(mViewRadius * mSin));
            }
        }
    }

    private float calculateDistance(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    public double calculateAngle(float x1, float y1) {
        double angle = Math.toDegrees(Math.atan2(mCenterX - x1, mCenterY - y1));
        angle = angle + Math.ceil(-angle / 360) * 360;
        // Tính độ lệch của phương thức và drawArc
        return (360 - angle);
    }

    public interface ProgressChangeListener {
        // call on action up to notify progress has changed
        void onProgressChanged(int progressChanged);
    }
}
