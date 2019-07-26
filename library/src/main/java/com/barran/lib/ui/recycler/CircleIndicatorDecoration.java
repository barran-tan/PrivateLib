package com.barran.lib.ui.recycler;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.barran.lib.R;
import com.barran.lib.utils.DisplayUtil;

/**
 * 使用ItemDecoration实现RecyclerView底部的圆形指示器
 * <p>
 * create by tanwei
 * on 2019/7/26
 */
public class CircleIndicatorDecoration extends RecyclerView.ItemDecoration {


    private Paint paint;
    /**
     * 圆点默认颜色  默认 R.color.white_transparent_30
     */
    private int defaultColor;

    /**
     * 圆点选中颜色 默认 R.color.white
     */
    private int selectedColor;

    /**
     * indicator和item底部的间距  默认15dp
     */
    private int indicatorTopMargin = DisplayUtil.dp2px(15f);
    /**
     * 圆点间距 默认10dp
     */
    private int indicatorSpace = DisplayUtil.dp2px(10f);
    /**
     * 圆点直径 默认6dp
     */
    private int indicatorDiam = DisplayUtil.dp2px(6f);


    public CircleIndicatorDecoration(Context context) {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);

        defaultColor = context.getResources().getColor(R.color.white_transparent_30);
        selectedColor = context.getResources().getColor(R.color.white);
    }

    public void setDefaultColor(int defaultColor) {
        this.defaultColor = defaultColor;
    }

    public void setSelectedColor(int selectedColor) {
        this.selectedColor = selectedColor;
    }

    public void setIndicatorTopMargin(int indicatorTopMargin) {
        this.indicatorTopMargin = indicatorTopMargin;
    }

    public void setIndicatorSpace(int indicatorSpace) {
        this.indicatorSpace = indicatorSpace;
    }

    public void setIndicatorDiam(int indicatorDiam) {
        this.indicatorDiam = indicatorDiam;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        outRect.bottom = indicatorTopMargin + indicatorDiam;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        int itemCount = parent.getAdapter().getItemCount();

        if (itemCount > 0) {

            int indicatorTotalWidth = itemCount * (indicatorSpace + indicatorDiam) - indicatorSpace;
            float indicatorStartX = (parent.getWidth() - indicatorTotalWidth) / 2f;
            float indicatorStartY = parent.getHeight() - indicatorDiam / 2f;

            drawInactiveIndicators(c, indicatorStartX, indicatorStartY, itemCount);

            // find active item
            LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
            int activePosition = layoutManager.findFirstVisibleItemPosition();
            if (activePosition == RecyclerView.NO_POSITION) {
                return;
            }

            // find offset of active item
            View activeChild = layoutManager.findViewByPosition(activePosition);
            if (activeChild != null) {
                int left = activeChild.getLeft();
                int width = activeChild.getWidth();
                float margin = (DisplayUtil.getScreenWidth() - width) / 2f;

                float progress = (left - margin) * -1 / width;
                Log.v("circleDecoration", "activePosition:" + activePosition + ", left:" + left + ", progress:" + progress);
                drawHighlightIndicator(c, indicatorStartX, indicatorStartY, activePosition, progress);
            }
        }
    }

    private void drawInactiveIndicators(Canvas c, float indicatorStartX, float indicatorPosY, int itemCount) {
        paint.setColor(defaultColor);

        // width of item indicator including padding
        int itemWidth = indicatorDiam + indicatorSpace;

        float start = indicatorStartX;
        float radius = indicatorDiam / 2f;
        for (int i = 0; i < itemCount; i++) {
            c.drawCircle(start + radius, indicatorPosY, radius, paint);
            start += itemWidth;
        }
    }

    private void drawHighlightIndicator(Canvas c, float indicatorStartX, float indicatorPosY,
                                        int highlightPosition, float progress) {
        paint.setColor(selectedColor);

        // width of item indicator including padding
        int itemWidth = indicatorDiam + indicatorSpace;
        float radius = indicatorDiam / 2f;

        if (progress == 0f) {
            float highlightStart = indicatorStartX + itemWidth * highlightPosition;
            c.drawCircle(highlightStart + radius, indicatorPosY, radius, paint);
        } else {
            float highlightStart = indicatorStartX + itemWidth * highlightPosition + itemWidth * progress;

            c.drawCircle(highlightStart + radius, indicatorPosY, radius, paint);
        }
    }
}
