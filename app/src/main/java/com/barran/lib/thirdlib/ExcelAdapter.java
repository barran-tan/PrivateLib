package com.barran.lib.thirdlib;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.barran.lib.test.R;
import com.barran.lib.utils.DisplayUtil;
import com.barran.lib.utils.log.Logs;

import cn.zhouchaoyuan.excelpanel.BaseExcelPanelAdapter;

/**
 * adapter for ExcelPanel
 * 
 * Created by tanwei on 2018/10/16.
 */

public class ExcelAdapter extends BaseExcelPanelAdapter<String, String, String> {
    
    private Context mContext;
    
    private TextView leftTop;
    
    private int leftWidth, cellWidth, cellHeight;
    
    public ExcelAdapter(Context context) {
        super(context);
        mContext = context;
        leftWidth = 150;
        int screenWidth = DisplayUtil.getScreenWidth();
        cellHeight = cellWidth = (screenWidth - leftWidth) / 6;
        
        Logs.v("width:" + cellWidth + ", height:" + cellHeight);
    }
    
    private RecyclerView.LayoutParams createParams(int width) {
        return new RecyclerView.LayoutParams(width, cellHeight);
    }
    
    public void updateLeftTop(String string) {
        leftTop.setText(string);
    }
    
    @Override
    public RecyclerView.ViewHolder onCreateCellViewHolder(ViewGroup parent,
            int viewType) {
        return new CellHolder(new TextView(parent.getContext()));
    }
    
    @Override
    public void onBindCellViewHolder(RecyclerView.ViewHolder holder, int verticalPosition,
            int horizontalPosition) {
        Logs.v("excel ver:" + verticalPosition + "\nhor:" + horizontalPosition);
        String position = "v" + verticalPosition + ",h" + horizontalPosition;
        
        ((CellHolder) holder).update(
                getMajorItem(verticalPosition, horizontalPosition) + "\n" + position);
    }
    
    @Override
    public RecyclerView.ViewHolder onCreateTopViewHolder(ViewGroup parent, int viewType) {
        return new TopHolder(new TextView(parent.getContext()));
    }
    
    @Override
    public void onBindTopViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((TopHolder) holder).update(topData.get(position));
    }
    
    @Override
    public RecyclerView.ViewHolder onCreateLeftViewHolder(ViewGroup parent,
            int viewType) {
        return new LeftHolder(new TextView(parent.getContext()));
    }
    
    @Override
    public void onBindLeftViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((LeftHolder) holder).update(leftData.get(position));
    }
    
    @Override
    public View onCreateTopLeftView() {
        if (leftTop == null) {
            leftTop = new TextView(mContext);
            leftTop.setText("9月");
            leftTop.setGravity(Gravity.CENTER);
        }
        return leftTop;
    }
    
    public class CellHolder extends RecyclerView.ViewHolder {
        
        private TextView textView;
        
        public CellHolder(View itemView) {
            super(itemView);
            itemView.setLayoutParams(createParams(cellWidth));
            itemView.setBackgroundColor(
                    itemView.getContext().getResources().getColor(R.color.gray_light));
            textView = (TextView) itemView;
            textView.setGravity(Gravity.CENTER);
        }
        
        public void update(String string) {
            textView.setText(string);
        }
    }
    
    public class TopHolder extends RecyclerView.ViewHolder {
        
        private TextView textView;
        
        public TopHolder(View itemView) {
            super(itemView);
            itemView.setLayoutParams(createParams(cellWidth));
            itemView.setBackgroundColor(
                    itemView.getContext().getResources().getColor(R.color.colorPrimary));
            
            textView = (TextView) itemView;
            textView.setGravity(Gravity.CENTER);
        }
        
        public void update(String string) {
            textView.setText(string);
        }
    }
    
    public class LeftHolder extends RecyclerView.ViewHolder {
        
        private TextView textView;
        
        public LeftHolder(View itemView) {
            super(itemView);
            itemView.setLayoutParams(createParams(leftWidth));
            itemView.setBackgroundColor(
                    itemView.getContext().getResources().getColor(android.R.color.white));
            
            textView = (TextView) itemView;
            textView.setGravity(Gravity.CENTER);
        }
        
        public void update(String string) {
            textView.setText(string);
        }
    }
    
}
