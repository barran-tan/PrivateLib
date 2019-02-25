package com.barran.lib.view.text;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;


import com.barran.lib.R;
import com.barran.lib.utils.DisplayUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * <p>设置正圆形的radius： {@linkplain R.styleable#ColorfulTextView_bgRoundRadius}</p>
 *
 * <p>注意：可以使用通过xml定义的shape或者使用下面的属性来定义背景
 *
 * <li>设置背景solid颜色： {@linkplain R.styleable#ColorfulTextView_bgSolidColor}
 *
 * <br>支持使用background来设置color或者xml背景
 *
 * <li>设置背景stroke颜色： {@linkplain R.styleable#ColorfulTextView_bgStrokeColor}
 *
 * <br>如果未设置背景，则默认设置为透明背景色
 *
 * <li>设置背景stroke宽度： {@linkplain R.styleable#ColorfulTextView_bgStrokeWidth} <br>如果仅设置了颜色则默认为1dp
 *
 * <li>设置背景radius： {@linkplain R.styleable#ColorfulTextView_bgRadius}
 *
 * Created by tanwei on 2017/12/20.
 */

public class ColorfulTextView extends AppCompatTextView{

    private static final String TAG = "ColorfulTextView";

    private static final int INVALID_COLOR = 1;

    private boolean roundRadius;

    private int touchUpRadius;

    private int solidColor, radius;

    private int strokeWidth, strokeColor;

    private int leftLineColor;

    private int lineWidth, lineHeight;

    public ColorfulTextView(Context context) {
        super(context);
    }

    public ColorfulTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.ColorfulTextView);

        // 默认是不使用正圆形的圆角，可修改默认值使全局生效
        roundRadius = array.getBoolean(R.styleable.ColorfulTextView_bgRoundRadius, false);

        // 自定义drawable属性
        solidColor = array.getColor(R.styleable.ColorfulTextView_bgSolidColor,
                INVALID_COLOR);
        radius = array.getDimensionPixelOffset(R.styleable.ColorfulTextView_bgRadius, 0);
        // stroke
        strokeWidth = array
                .getDimensionPixelOffset(R.styleable.ColorfulTextView_bgStrokeWidth, 0);
        strokeColor = array.getColor(R.styleable.ColorfulTextView_bgStrokeColor,
                INVALID_COLOR);
        // 默认width为1dp
        if (strokeWidth == 0 && strokeColor != INVALID_COLOR) {
            strokeWidth = DisplayUtil.dp2px(1);
        }

        // 左侧竖线
        leftLineColor = array.getColor(R.styleable.ColorfulTextView_leftLineColor, INVALID_COLOR);
        lineHeight = array.getDimensionPixelOffset(
                R.styleable.ColorfulTextView_lineHeight,
                ViewGroup.LayoutParams.MATCH_PARENT);
        lineWidth = array.getDimensionPixelOffset(R.styleable.ColorfulTextView_lineWidth,
                DisplayUtil.dp2px(3));

        // 设置xml自定义背景
        initDrawable();
        array.recycle();
    }

    private void initDrawable() {
        // 如果未设置背景色，则尝试获取background
        Drawable background = getBackground();
        if (solidColor == INVALID_COLOR) {
            // 设置了strokeColor：默认为透明背景
            if (background == null && strokeColor != INVALID_COLOR) {
                solidColor = Color.TRANSPARENT;
                background = buildDrawable();
                super.setBackground(background);
            } else if (background instanceof ColorDrawable) {
                // 设置了背景色
                solidColor = ((ColorDrawable) background).getColor();
                background = buildDrawable();
                super.setBackground(background);
            }
            else if (background instanceof StateListDrawable) {
                DrawableContainer.DrawableContainerState drawableContainerState = (DrawableContainer.DrawableContainerState) background
                        .getConstantState();
                if (drawableContainerState != null) {
                    boolean needRefresh = false;
                    Drawable[] children = drawableContainerState.getChildren();
                    if (drawableContainerState.getChildCount() > 0) {
                        for (int i = 0; i < drawableContainerState.getChildCount(); i++) {
                            Drawable d = children[i];
                            if (d != null) {
                                if (d instanceof ColorDrawable) {
                                    d = buildGradientDrawableFromPureColor(
                                            ((ColorDrawable) d).getColor());
                                    needRefresh = true;
                                    Log.d(TAG,
                                            this + "  initDrawable : change  "
                                                    + children[i] + "  to  " + d);
                                    children[i] = d;
                                }
                            }
                        }
                    }
                    if (needRefresh) {
                        StateListDrawable oriSld = (StateListDrawable) background;
                        StateListDrawable sld = new StateListDrawable();
                        boolean hasException = false;
                        try {
                            Method method = StateListDrawable.class
                                    .getMethod("getStateCount");
                            int stateCount = (int) method.invoke(oriSld);
                            Log.d(TAG, "initDrawable  :  stateCount =" + stateCount);
                            Method getStateSetMethod = StateListDrawable.class
                                    .getMethod("getStateSet", int.class);
                            Method getStateDrawableMethod = StateListDrawable.class
                                    .getMethod("getStateDrawable", int.class);
                            for (int i = 0; i < stateCount; i++) {
                                sld.addState((int[]) getStateSetMethod.invoke(oriSld, i),
                                        (Drawable) getStateDrawableMethod.invoke(oriSld,
                                                i));
                            }
                            Log.d(TAG, this + "  initDrawable done , need refresh");
                            super.setBackground(sld);
                        } catch (NoSuchMethodException e) {
                            Log.w(TAG, e);
                            hasException = true;
                        } catch (IllegalAccessException e) {
                            Log.w(TAG, e);
                            hasException = true;
                        } catch (InvocationTargetException e) {
                            Log.w(TAG, e);
                            hasException = true;
                        } finally {
                            if (hasException) {
                                Log.w(TAG,"initDrawable : set from newDrawable");
                                super.setBackground(drawableContainerState.newDrawable());
                            }
                        }
                    }
                }
            }
        } else {
            background = buildDrawable();
            super.setBackground(background);
        }

        // 非正圆形圆角时设置radius（避免重复设置）
        if (!roundRadius && radius > 0) {
            handleDrawableRadius(background, radius);
        }
    }

    private GradientDrawable buildGradientDrawableFromPureColor(@ColorInt int color) {
        GradientDrawable xmlDrawable = new GradientDrawable();
        xmlDrawable.setColor(color);
        xmlDrawable.setShape(GradientDrawable.RECTANGLE);
        return xmlDrawable;
    }

    private GradientDrawable buildDrawable() {
        GradientDrawable xmlDrawable = buildGradientDrawableFromPureColor(solidColor);
        if (strokeWidth > 0) {
            xmlDrawable.setStroke(strokeWidth, strokeColor);
        }

        return xmlDrawable;
    }

    // 修正背景为正圆形radius
    private void touchUpRadius() {
        Log.d(TAG,this + "  touchUpRadius");
        int height = getMeasuredHeight();
        if (height == 0) {
            return;
        }
        if (roundRadius) {
            Drawable background = getBackground();
            touchUpRadius = height / 2;
            handleDrawableRadius(background, touchUpRadius);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (isRoundRadiusDirty()) {
            touchUpRadius();
        }
    }

    private void handleDrawableRadius(Drawable drawable, int radius) {
        Log.d(TAG,this + "  handleDrawableRadius : drawable " + drawable + "  radius=" + radius);
        if (drawable instanceof StateListDrawable) {
            DrawableContainer.DrawableContainerState drawableContainerState = (DrawableContainer.DrawableContainerState) drawable
                    .getConstantState();
            if (drawableContainerState != null) {
                Drawable[] children = drawableContainerState.getChildren();
                if (drawableContainerState.getChildCount() > 0) {
                    for (int i=0; i < drawableContainerState.getChildCount(); i++) {
                        Drawable d = children[i];
                        if (d != null) {
                            handleDrawableRadius(d, radius);
                        }
                    }
                }
            }
        }
        else if (drawable instanceof LayerDrawable) {
            int layerSize = ((LayerDrawable) drawable).getNumberOfLayers();
            for (int i = 0; i < layerSize; i++) {
                Drawable childDrawable = ((LayerDrawable) drawable).getDrawable(i);
                if (childDrawable != null) {
                    handleDrawableRadius(childDrawable, radius);
                }
            }
        }
        else if (drawable instanceof GradientDrawable) {
            ((GradientDrawable) drawable).setCornerRadius(radius);
            Log.d(TAG,this + "  handleDrawableRadius : setCornerRadius to  " + radius);
        }
        else {
            Log.w(TAG, "invalid drawable type : "
                    + (drawable != null ? drawable.getClass().getSimpleName() : "null"));
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        Log.d(TAG,"onLayout : changed=" + changed + "  left=" + left + "  top=" + top + "  right=" + right + "  bottom=" + bottom);
        if (leftLineColor != INVALID_COLOR) {
            buildLineDrawable();
        }

        super.onLayout(changed, left, top, right, bottom);
    }

    // 判断是否需要设置正圆形圆角
    private boolean isRoundRadiusDirty() {
        return roundRadius
                && (touchUpRadius == 0 || touchUpRadius != getMeasuredHeight() / 2);
    }

    private void buildLineDrawable() {
        int height = getHeight();
        if (height <= 0) {
            return;
        }
        GradientDrawable line = new GradientDrawable();
        line.setColor(leftLineColor);
        if (lineHeight == ViewGroup.LayoutParams.MATCH_PARENT) {
            lineHeight = height - DisplayUtil.dp2px(3);
        }
        else if (lineHeight > height) {
            lineHeight = height;
        }

        line.setBounds(0, 0, lineWidth, lineHeight);

        Drawable[] compoundDrawables = getCompoundDrawables();
        if (compoundDrawables.length >= 4) {
            setCompoundDrawables(line, compoundDrawables[1], compoundDrawables[2],
                    compoundDrawables[3]);
        }
        else {
            setCompoundDrawables(line, null, null, null);
        }
    }

    @Override
    public void setBackground(Drawable background) {
        super.setBackground(background);
        if (roundRadius) {
            requestLayout();
        }
    }

    public boolean isRoundRadius() {
        return roundRadius;
    }

    /**
     * 设置圆角是否为正圆形
     */
    public void setRoundRadius(boolean roundRadius) {
        this.roundRadius = roundRadius;
        requestLayout();
    }

    public void buildBackground(DrawableBuilder builder) {
        if (builder.solidColor != INVALID_COLOR) {
            GradientDrawable xmlDrawable = new GradientDrawable();
            xmlDrawable.setColor(builder.solidColor);
            xmlDrawable.setShape(GradientDrawable.RECTANGLE);

            if (builder.strokeWidth > 0) {
                xmlDrawable.setStroke(builder.strokeWidth,
                        builder.strokeColor != INVALID_COLOR ? builder.strokeColor
                                : builder.solidColor);
            }
            if (builder.radius > 0) {
                xmlDrawable.setCornerRadius(builder.radius);
            }
            roundRadius = builder.roundRadius;

            setBackground(xmlDrawable);

            if (roundRadius) {
                touchUpRadius();
            }
        }
    }

    public static class DrawableBuilder {
        protected int solidColor = INVALID_COLOR;
        protected int strokeColor = INVALID_COLOR;
        protected int strokeWidth;
        protected int radius;
        protected boolean roundRadius;

        public DrawableBuilder setSolidColor(int color) {
            solidColor = color;
            return this;
        }

        public DrawableBuilder setStrokeColor(int color) {
            strokeColor = color;
            return this;
        }

        public DrawableBuilder setStrokeWidth(int width) {
            strokeWidth = width;
            return this;
        }

        public DrawableBuilder setRadius(int radius) {
            this.radius = radius;
            return this;
        }

        public DrawableBuilder setRoundRadius(boolean round) {
            roundRadius = round;
            return this;
        }
    }
}
