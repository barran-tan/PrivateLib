package com.barran.lib.test;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.barran.lib.ui.recycler.CircleIndicatorDecoration;
import com.barran.lib.ui.recycler.DualSpaceItemDecoration;
import com.barran.lib.utils.DisplayUtil;

/**
 * description
 * <p>
 * create by tanwei@bigo.sg
 * on 2019/7/26
 */
public class TestViewPagerActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private int mItemWidth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test_view_pager);

        mRecyclerView = findViewById(R.id.activity_test_view_pager);

        init();
    }

    private void init() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        mRecyclerView.setAdapter(new Adapter());

        new PagerSnapHelper().attachToRecyclerView(mRecyclerView);

        int margin = DisplayUtil.dp2px(50);
        mItemWidth = DisplayUtil.getScreenWidth() - margin * 2;
        mRecyclerView.setPadding(margin, 0, margin, 0);
        mRecyclerView.setClipToPadding(false);
        mRecyclerView.addItemDecoration(new CircleIndicatorDecoration(this));
        mRecyclerView.addItemDecoration(new DualSpaceItemDecoration(DisplayUtil.dp2px(10)));
    }

    private class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(LayoutInflater.from(TestViewPagerActivity.this).inflate(R.layout.item_page_card, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((Holder) holder).update(position);
        }

        @Override
        public int getItemCount() {
            return 4;
        }
    }

    private class Holder extends RecyclerView.ViewHolder {

        private ImageView mImageView;

        private TextView tvTitle, tvDesc;

        public Holder(View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.item_page_card_image);
            tvTitle = itemView.findViewById(R.id.item_page_card_title);
            tvDesc = itemView.findViewById(R.id.item_page_card_desc);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) itemView.getLayoutParams();
            if (params == null) {
                params = new RecyclerView.LayoutParams(mItemWidth, RecyclerView.LayoutParams.WRAP_CONTENT);
                itemView.setLayoutParams(params);
            } else {
                params.width = mItemWidth;
            }
        }

        private void update(int position) {
            switch (position) {
                case 0:
                    mImageView.setImageResource(R.mipmap.icon_head);
                    break;
                case 1:
                    mImageView.setImageResource(R.mipmap.pic_card_1);
                    break;
                case 2:
                    mImageView.setImageResource(R.mipmap.pic_card_2);
                    break;
                case 3:
                    mImageView.setImageResource(R.mipmap.pic_card_3);
                    break;
            }
        }
    }
}
