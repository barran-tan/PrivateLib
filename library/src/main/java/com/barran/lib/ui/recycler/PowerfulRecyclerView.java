package com.barran.lib.ui.recycler;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * 增强型 RecyclerView,目前支持最大高度
 * <p>
 * Created by tanwei on 2018/4/13.
 */

public class PowerfulRecyclerView extends RecyclerView {

    private InternalClickListener mInternalClickListener;

    private InternalLongClickListener mInternalLongClickListener;

    private ItemClickListener mItemClickListener;

    private ItemLongClickListener mItemLongClickListener;

    private int maxHeight;

    public PowerfulRecyclerView(Context context) {
        super(context);
        init();
    }

    public PowerfulRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PowerfulRecyclerView(Context context, @Nullable AttributeSet attrs,
                                int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mInternalClickListener = new InternalClickListener();
        mInternalLongClickListener = new InternalLongClickListener();
    }

    public PowerfulRecyclerView setMaxHeight(int height) {
        maxHeight = height;
        return this;
    }

    public PowerfulRecyclerView setItemClickListener(ItemClickListener listener) {
        mItemClickListener = listener;
        return this;
    }

    public PowerfulRecyclerView setItemLongClickListener(ItemLongClickListener listener) {
        mItemLongClickListener = listener;
        return this;
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        if (maxHeight > 0 && getMeasuredHeight() > maxHeight) {
            setMeasuredDimension(getMeasuredWidth(), maxHeight);
        }
    }

    @Override
    public void onChildAttachedToWindow(View child) {
        child.setOnClickListener(mInternalClickListener);
        child.setOnLongClickListener(mInternalLongClickListener);
    }

    @Override
    public void onChildDetachedFromWindow(View child) {
        child.setOnClickListener(null);
        child.setOnLongClickListener(null);
    }

    private class InternalClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(PowerfulRecyclerView.this, v, getChildLayoutPosition(v));
            }
        }
    }

    private class InternalLongClickListener implements View.OnLongClickListener {
        @Override
        public boolean onLongClick(View v) {
            if (mItemLongClickListener != null) {
                mItemLongClickListener.onItemLongClick(PowerfulRecyclerView.this, v, getChildLayoutPosition(v));
                return true;
            }
            return false;
        }
    }

    public interface ItemClickListener {
        void onItemClick(RecyclerView parent, View view, int position);
    }

    public interface ItemLongClickListener {
        void onItemLongClick(RecyclerView parent, View view, int position);
    }
}
