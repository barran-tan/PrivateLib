package com.barran.lib.ui.recycler;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * description
 * <p>
 * create by tanwei@bigo.sg
 * on 2019/7/26
 */
public class DualSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int mSpace;

    public DualSpaceItemDecoration(int space) {
        mSpace = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        outRect.left = outRect.right = mSpace;

    }
}
