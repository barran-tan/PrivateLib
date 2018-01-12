package com.barran.lib.view.text;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;

import com.barran.lib.R;


/**
 * <p>设置正圆形的radius： {@linkplain R.styleable#ColorfulTextView_bgRoundRadius}</p>
 *
 * <p>注意：可以使用通过xml定义的shape或者使用下面的属性来定义背景</p>
 *
 * <p>设置背景solid颜色(必须)： {@linkplain R.styleable#ColorfulTextView_bgSolidColor}</p>
 *
 * <p>设置背景stroke颜色： {@linkplain R.styleable#ColorfulTextView_bgStrokeColor}</p>
 *
 * <p>设置背景stroke宽度： {@linkplain R.styleable#ColorfulTextView_bgStrokeWidth}</p>
 *
 * <p>设置背景radius： {@linkplain R.styleable#ColorfulTextView_bgRadius}</p>
 *
 * Created by tanwei on 2017/12/20.
 */

public class ColorfulTextView extends AppCompatTextView{

    private static final String TAG = "ColorfulTextView";

    private boolean roundRadius;

    private GradientDrawable xmlDrawable;

    private int touchUpRadius;

    public ColorfulTextView(Context context) {
        super(context);
    }

    public ColorfulTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        
        TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.ColorfulTextView);
        
        // 默认是不使用正圆形的圆角，可修改默认值使全局生效
        roundRadius = array.getBoolean(R.styleable.ColorfulTextView_bgRoundRadius, false);
        
        // 解析xml定义的背景
        initDrawable(array);
        
        array.recycle();
    }

    private void initDrawable(TypedArray array) {
        int solidColor = array.getColor(R.styleable.ColorfulTextView_bgSolidColor,
                Color.TRANSPARENT);
        if (solidColor != Color.TRANSPARENT) {
            xmlDrawable = new GradientDrawable();
            xmlDrawable.setColor(solidColor);
            xmlDrawable.setShape(GradientDrawable.RECTANGLE);
            int strokeWidth = array.getDimensionPixelOffset(
                    R.styleable.ColorfulTextView_bgStrokeWidth, 0);
            if (strokeWidth > 0) {
                int strokeColor = array
                        .getColor(R.styleable.ColorfulTextView_bgStrokeColor, solidColor);
                xmlDrawable.setStroke(strokeWidth, strokeColor);
            }
            int radius = array
                    .getDimensionPixelOffset(R.styleable.ColorfulTextView_bgRadius, 0);
            if (radius > 0) {
                xmlDrawable.setCornerRadius(radius);
            }
            
            super.setBackground(xmlDrawable);
        }
    }

    // 修正背景为正圆形radius
    private void touchUpRadius() {
        int height = getMeasuredHeight();
        if (height == 0) {
            return;
        }
        if (roundRadius) {
            Drawable background = getBackground();
            if(background instanceof StateListDrawable || background instanceof GradientDrawable || background instanceof LayerDrawable) {
                touchUpRadius = height / 2;
                handleDrawable(background);
            }
        }
    }
    
    private void handleDrawable(Drawable drawable) {
        if (drawable instanceof StateListDrawable) {
            DrawableContainer.DrawableContainerState drawableContainerState = (DrawableContainer.DrawableContainerState) drawable
                    .getConstantState();
            if (drawableContainerState != null) {
                Drawable[] children = drawableContainerState.getChildren();
                if (children.length > 0) {
                    for (Drawable d : children) {
                        if (d != null) {
                            handleDrawable(d);
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
                    handleDrawable(childDrawable);
                }
            }
        }
        else if (drawable instanceof GradientDrawable) {
            ((GradientDrawable) drawable).setCornerRadius(touchUpRadius);
        }
        else {
            Log.w(TAG, "invalid drawable type : " + drawable != null
                    ? drawable.getClass().getSimpleName() : "null");
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        
        if (roundRadius && touchUpRadius == 0) {
            int height = getHeight();
            if (height > 0) {
                touchUpRadius();
            }
        }
        
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public void setBackground(Drawable background) {
        if (xmlDrawable != null && background == xmlDrawable) {
            return;
        }
        xmlDrawable = null;

        super.setBackground(background);
    }

    @Override
    public Drawable getBackground() {
        if (xmlDrawable != null) {
            return xmlDrawable;
        }
        return super.getBackground();
    }

    public boolean isRoundRadius() {
        return roundRadius;
    }

    /**
     * 设置圆角是否为正圆形
     */
    public void setRoundRadius(boolean roundRadius) {
        this.roundRadius = roundRadius;
    }

    public void buildBackground(DrawableBuilder builder){
        if(builder.solidColor != 0) {
            xmlDrawable = new GradientDrawable();
            xmlDrawable.setColor(builder.solidColor);
            xmlDrawable.setShape(GradientDrawable.RECTANGLE);

            if (builder.strokeWidth > 0) {
                xmlDrawable.setStroke(builder.strokeWidth, builder.strokeColor != 0
                        ? builder.strokeColor : builder.solidColor);
            }
            if (builder.radius > 0) {
                xmlDrawable.setCornerRadius(builder.radius);
            }

            setBackground(xmlDrawable);
        }
    }
    
    public static class DrawableBuilder {
        protected int solidColor;
        protected int strokeColor;
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
        
        public DrawableBuilder setRoundRaddius(boolean round) {
            roundRadius = round;
            return this;
        }
    }
}
