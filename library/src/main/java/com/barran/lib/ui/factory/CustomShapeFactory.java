package com.barran.lib.ui.factory;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;

import com.barran.lib.R;

/**
 * 解析xml并构造GradientDrawable
 *
 * Created by tanwei on 2019/2/25.
 */

public class CustomShapeFactory {
    
    static GradientDrawable newShape(Context ctx, AttributeSet attrs) {
        GradientDrawable gd = new GradientDrawable();
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.Custom_Shape);
        gd.setShape(
                a.getInt(R.styleable.Custom_Shape_shapeType, GradientDrawable.RECTANGLE));
        
        final int size = a.getDimensionPixelSize(R.styleable.Custom_Shape_shapeSolidSize,
                -1);
        gd.setSize(
                a.getDimensionPixelSize(R.styleable.Custom_Shape_shapeSolidWidth, size),
                a.getDimensionPixelSize(R.styleable.Custom_Shape_shapeSolidHeight, size));
        
        gd.setGradientType(a.getInt(R.styleable.Custom_Shape_shapeGradientType, 0));
        gd.setGradientRadius(
                a.getDimension(R.styleable.Custom_Shape_shapeGradientRadius, 0));
        gd.setColors(new int[] {
                a.getColor(R.styleable.Custom_Shape_shapeGradientStartColor, 0),
                a.getColor(R.styleable.Custom_Shape_shapeGradientCenterColor, 0),
                a.getColor(R.styleable.Custom_Shape_shapeGradientEndColor, 0) });
        final int orientationIndex = a.getInt(R.styleable.Custom_Shape_shapeGradientAngle,
                0);
        gd.setOrientation(GradientDrawable.Orientation.values()[orientationIndex]);
        
        if (a.hasValue(R.styleable.Custom_Shape_shapeSolidColor)) {
            gd.setColor(
                    a.getColor(R.styleable.Custom_Shape_shapeSolidColor, Color.WHITE));
        }
        
        gd.setStroke(
                a.getDimensionPixelSize(R.styleable.Custom_Shape_shapeStrokeWidth, -1),
                a.getColor(R.styleable.Custom_Shape_shapeStrokeColor, Color.WHITE));
        final float radius = a.getDimension(R.styleable.Custom_Shape_shapeCornerRadius,
                0);
        gd.setCornerRadii(new float[] {
                a.getDimension(R.styleable.Custom_Shape_shapeCornerRadiusTopLeft, radius),
                a.getDimension(R.styleable.Custom_Shape_shapeCornerRadiusTopLeft, radius),
                a.getDimension(R.styleable.Custom_Shape_shapeCornerRadiusTopRight,
                        radius),
                a.getDimension(R.styleable.Custom_Shape_shapeCornerRadiusTopRight,
                        radius),
                a.getDimension(R.styleable.Custom_Shape_shapeCornerRadiusBottomRight,
                        radius),
                a.getDimension(R.styleable.Custom_Shape_shapeCornerRadiusBottomRight,
                        radius),
                a.getDimension(R.styleable.Custom_Shape_shapeCornerRadiusBottomLeft,
                        radius),
                a.getDimension(R.styleable.Custom_Shape_shapeCornerRadiusBottomLeft,
                        radius), });
        a.recycle();
        return gd;
    }
    
    static GradientDrawable newShape1(Context ctx, AttributeSet attrs) {
        GradientDrawable gd = new GradientDrawable();
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.Custom_Shape1);
        gd.setShape(a.getInt(R.styleable.Custom_Shape1_shape1Type,
                GradientDrawable.RECTANGLE));
        
        final int size = a
                .getDimensionPixelSize(R.styleable.Custom_Shape1_shape1SolidSize, -1);
        gd.setSize(
                a.getDimensionPixelSize(R.styleable.Custom_Shape1_shape1SolidWidth, size),
                a.getDimensionPixelSize(R.styleable.Custom_Shape1_shape1SolidHeight,
                        size));
        
        gd.setGradientType(a.getInt(R.styleable.Custom_Shape1_shape1GradientType, 0));
        gd.setGradientRadius(
                a.getDimension(R.styleable.Custom_Shape1_shape1GradientRadius, 0));
        gd.setColors(new int[] {
                a.getColor(R.styleable.Custom_Shape1_shape1GradientStartColor, 0),
                a.getColor(R.styleable.Custom_Shape1_shape1GradientCenterColor, 0),
                a.getColor(R.styleable.Custom_Shape1_shape1GradientEndColor, 0) });
        final int orientationIndex = a
                .getInt(R.styleable.Custom_Shape1_shape1GradientAngle, 0);
        gd.setOrientation(GradientDrawable.Orientation.values()[orientationIndex]);
        
        if (a.hasValue(R.styleable.Custom_Shape1_shape1SolidColor)) {
            gd.setColor(
                    a.getColor(R.styleable.Custom_Shape1_shape1SolidColor, Color.WHITE));
        }
        
        gd.setStroke(
                a.getDimensionPixelSize(R.styleable.Custom_Shape1_shape1StrokeWidth, -1),
                a.getColor(R.styleable.Custom_Shape1_shape1StrokeColor, Color.WHITE));
        final float radius = a.getDimension(R.styleable.Custom_Shape1_shape1CornerRadius,
                0);
        gd.setCornerRadii(new float[] {
                a.getDimension(R.styleable.Custom_Shape1_shape1CornerRadiusTopLeft,
                        radius),
                a.getDimension(R.styleable.Custom_Shape1_shape1CornerRadiusTopLeft,
                        radius),
                a.getDimension(R.styleable.Custom_Shape1_shape1CornerRadiusTopRight,
                        radius),
                a.getDimension(R.styleable.Custom_Shape1_shape1CornerRadiusTopRight,
                        radius),
                a.getDimension(R.styleable.Custom_Shape1_shape1CornerRadiusBottomRight,
                        radius),
                a.getDimension(R.styleable.Custom_Shape1_shape1CornerRadiusBottomRight,
                        radius),
                a.getDimension(R.styleable.Custom_Shape1_shape1CornerRadiusBottomLeft,
                        radius),
                a.getDimension(R.styleable.Custom_Shape1_shape1CornerRadiusBottomLeft,
                        radius), });
        a.recycle();
        return gd;
    }
    
    static GradientDrawable newShape2(Context ctx, AttributeSet attrs) {
        GradientDrawable gd = new GradientDrawable();
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.Custom_Shape2);
        gd.setShape(a.getInt(R.styleable.Custom_Shape2_shape2Type,
                GradientDrawable.RECTANGLE));
        
        final int size = a
                .getDimensionPixelSize(R.styleable.Custom_Shape2_shape2SolidSize, -1);
        gd.setSize(
                a.getDimensionPixelSize(R.styleable.Custom_Shape2_shape2SolidWidth, size),
                a.getDimensionPixelSize(R.styleable.Custom_Shape2_shape2SolidHeight,
                        size));
        
        gd.setGradientType(a.getInt(R.styleable.Custom_Shape2_shape2GradientType, 0));
        gd.setGradientRadius(
                a.getDimension(R.styleable.Custom_Shape2_shape2GradientRadius, 0));
        gd.setColors(new int[] {
                a.getColor(R.styleable.Custom_Shape2_shape2GradientStartColor, 0),
                a.getColor(R.styleable.Custom_Shape2_shape2GradientCenterColor, 0),
                a.getColor(R.styleable.Custom_Shape2_shape2GradientEndColor, 0) });
        final int orientationIndex = a
                .getInt(R.styleable.Custom_Shape2_shape2GradientAngle, 0);
        gd.setOrientation(GradientDrawable.Orientation.values()[orientationIndex]);
        
        if (a.hasValue(R.styleable.Custom_Shape2_shape2SolidColor)) {
            gd.setColor(
                    a.getColor(R.styleable.Custom_Shape2_shape2SolidColor, Color.WHITE));
        }
        
        gd.setStroke(
                a.getDimensionPixelSize(R.styleable.Custom_Shape2_shape2StrokeWidth, -1),
                a.getColor(R.styleable.Custom_Shape2_shape2StrokeColor, Color.WHITE));
        final float radius = a.getDimension(R.styleable.Custom_Shape2_shape2CornerRadius,
                0);
        gd.setCornerRadii(new float[] {
                a.getDimension(R.styleable.Custom_Shape2_shape2CornerRadiusTopLeft,
                        radius),
                a.getDimension(R.styleable.Custom_Shape2_shape2CornerRadiusTopLeft,
                        radius),
                a.getDimension(R.styleable.Custom_Shape2_shape2CornerRadiusTopRight,
                        radius),
                a.getDimension(R.styleable.Custom_Shape2_shape2CornerRadiusTopRight,
                        radius),
                a.getDimension(R.styleable.Custom_Shape2_shape2CornerRadiusBottomRight,
                        radius),
                a.getDimension(R.styleable.Custom_Shape2_shape2CornerRadiusBottomRight,
                        radius),
                a.getDimension(R.styleable.Custom_Shape2_shape2CornerRadiusBottomLeft,
                        radius),
                a.getDimension(R.styleable.Custom_Shape2_shape2CornerRadiusBottomLeft,
                        radius), });
        a.recycle();
        return gd;
    }
}
