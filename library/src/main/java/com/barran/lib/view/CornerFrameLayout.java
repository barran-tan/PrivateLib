package com.barran.lib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.barran.lib.R;
import com.barran.lib.utils.DisplayUtil;

/**
 * 可以替换CardView实现圆角
 * <p>
 * create by tanwei@bigo.sg
 * on 2019/8/27
 */
public class CornerFrameLayout extends FrameLayout {

    private Paint mPaint;
    private RectF mBoundRectF;
    private final Path mClipPath = new Path();

    private float radius;
    private int bgColor;
    private float strokeWidth;
    private int strokeColor;
    //如果不需要边就设置 true，stroke color 和 width 都会失效
    private boolean borderless;

    private boolean mHardwareAccelerate;

    private static boolean UNDER_ANDROID_P = Build.VERSION.SDK_INT <= Build.VERSION_CODES.O;

    private PorterDuffXfermode mContentXfermode;

    public CornerFrameLayout(Context context) {
        super(context);
        init(null);
    }

    public CornerFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CornerFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray t = getContext().obtainStyledAttributes(attrs, R.styleable.CornerFrameLayout);
            radius = t.getDimension(R.styleable.CornerFrameLayout_cornerRadius, DisplayUtil.dp2px(10));
            bgColor = t.getColor(R.styleable.CornerFrameLayout_bgColor, Color.WHITE);
            strokeWidth = t.getDimension(R.styleable.CornerFrameLayout_borderWidth, DisplayUtil.dp2px(1));
            borderless = t.getBoolean(R.styleable.CornerFrameLayout_borderless, false);
            strokeColor = t.getColor(R.styleable.CornerFrameLayout_borderColor, Color.BLACK);
            mHardwareAccelerate = t.getBoolean(R.styleable.CornerFrameLayout_hardwareAccelerate, true);
            t.recycle();
        }

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(bgColor);
        mPaint.setStyle(Paint.Style.FILL);
        mContentXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
        mPaint.setXfermode(mContentXfermode);
        mBoundRectF = new RectF();

        if (!mHardwareAccelerate) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

    }

    public void setBorderColor(int color) {
        strokeColor = color;
        invalidate();
    }

    public void setBorderWidth(float width) {
        strokeWidth = width;
        invalidate();
    }

    @Override
    protected void onSizeChanged (int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);

        if (w > 0 && h > 0) {
            mBoundRectF.set(0, 0, w, h);
            mClipPath.rewind();
            mClipPath.addRoundRect(mBoundRectF, radius, radius, Path.Direction.CW);
            mClipPath.close();
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        int count = canvas.saveLayer(mBoundRectF, null, Canvas.ALL_SAVE_FLAG);
        try {
            if (UNDER_ANDROID_P || !drawUpAndroidP(canvas)) {
                drawUnderAndroidP(canvas);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            canvas.restoreToCount(count);
        }
    }

    private boolean drawUpAndroidP(Canvas canvas) {
        boolean clipOk = canvas.clipPath(mClipPath);
        if (!clipOk) {
            return false;
        }
        super.dispatchDraw(canvas);
        return true;
    }

    private void drawUnderAndroidP(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (!borderless && strokeWidth > 0) { //have valid stroke
            mPaint.setXfermode(null);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(strokeColor);
            final float oldStrokeWidth = mPaint.getStrokeWidth();
            mPaint.setStrokeWidth(strokeWidth);
            canvas.drawRoundRect(mBoundRectF, radius, radius, mPaint);
            mPaint.setStrokeWidth(oldStrokeWidth);
        }
        mPaint.setXfermode(mContentXfermode);
        mPaint.setStrokeWidth(0);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(bgColor);
        canvas.drawPath(mClipPath, mPaint);
    }

    public void setRadius(float radius) {
        this.radius = radius;
        mClipPath.rewind();
        mClipPath.addRoundRect(mBoundRectF, radius, radius, Path.Direction.CW);
        mClipPath.close();

        invalidate();
    }
}
