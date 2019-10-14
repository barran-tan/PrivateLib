package com.barran.lib.thirdlib;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.barran.lib.test.R;
import com.barran.lib.utils.DisplayUtil;
import com.barran.lib.utils.log.Logs;
import com.barran.lib.utils.time.SelectTimeUtils;
import com.cleveroad.adaptivetablelayout.BaseDataAdaptiveTableLayoutAdapter;
import com.cleveroad.adaptivetablelayout.ViewHolderImpl;

import java.util.Calendar;

/**
 * adapter for AdaptiveTableLayout
 *
 * Created by tanwei on 2018/10/17.
 */

public class AdaptiveAdapter extends BaseDataAdaptiveTableLayoutAdapter<ViewHolderImpl> {
    
    private static final int DAYS_COUNT = 31;
    private static final int HOURS_COUNT = 24 - 6;
    
    private Context context;
    
    private String[] rowHeaders, columnHeaders;
    
    private String[][] contents;
    
    private int leftWidth, cellWidth, cellHeight;
    
    public AdaptiveAdapter(Context context) {
        this.context = context;
        
        prepareData();
        
        leftWidth = context.getResources().getDimensionPixelOffset(R.dimen.dimen_48);
        int screenWidth = DisplayUtil.getScreenWidth();
        cellHeight = cellWidth = (screenWidth - leftWidth) / 5;
        
        Logs.v("width:" + cellWidth + ", height:" + cellHeight);
    }
    
    private void prepareData() {
        rowHeaders = new String[HOURS_COUNT];
        for (int i = 0; i < HOURS_COUNT; i++) {
            rowHeaders[i] = (i + 6) + "-" + (i + 7);
        }
        
        columnHeaders = new String[DAYS_COUNT];
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < DAYS_COUNT; i++) {
            columnHeaders[i] = SelectTimeUtils.getWeekDay(calendar.getTime()).toString()
                    + "\n" + calendar.get(Calendar.DAY_OF_MONTH);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        
        contents = new String[HOURS_COUNT][DAYS_COUNT];
        for (int i = 0; i < HOURS_COUNT; i++) {
            contents[i] = new String[DAYS_COUNT];
            for (int j = 0; j < DAYS_COUNT; j++) {
                contents[i][j] = i + "," + j;
            }
        }
    }
    
    @Override
    protected Object[][] getItems() {
        return contents;
    }
    
    @Override
    protected Object[] getRowHeaders() {
        return rowHeaders;
    }
    
    @Override
    protected Object[] getColumnHeaders() {
        return columnHeaders;
    }
    
    @Override
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        
    }
    
    @Override
    public void onRestoreInstanceState(@NonNull Bundle bundle) {
        
    }
    
    @Override
    public int getRowCount() {
        // 需要加上header
        return HOURS_COUNT + 1;
    }
    
    @Override
    public int getColumnCount() {
        // 需要加上header
        return DAYS_COUNT + 1;
    }
    
    @NonNull
    @Override
    public ViewHolderImpl onCreateItemViewHolder(@NonNull ViewGroup parent) {
        return new CellHolder(new TextView(context));
    }
    
    @NonNull
    @Override
    public ViewHolderImpl onCreateColumnHeaderViewHolder(@NonNull ViewGroup parent) {
        return new TopHolder(new TextView(context));
    }
    
    @NonNull
    @Override
    public ViewHolderImpl onCreateRowHeaderViewHolder(@NonNull ViewGroup parent) {
        return new LeftHolder(new TextView(context));
    }
    
    @NonNull
    @Override
    public ViewHolderImpl onCreateLeftTopHeaderViewHolder(@NonNull ViewGroup parent) {
        return new LeftTopHolder(new TextView(context));
    }
    
    @Override
    public void onBindViewHolder(@NonNull ViewHolderImpl viewHolder, int row,
            int column) {
        Logs.v("cell row:" + row + "\ncol:" + column);
        String position = "r" + row + ",c" + column;
        
        ((CellHolder) viewHolder).update(contents[row][column] + "\n" + position);
    }
    
    @Override
    public void onBindHeaderColumnViewHolder(@NonNull ViewHolderImpl viewHolder,
            int column) {
        ((TopHolder) viewHolder).update(columnHeaders[column]);
    }
    
    @Override
    public void onBindHeaderRowViewHolder(@NonNull ViewHolderImpl viewHolder, int row) {
        ((LeftHolder) viewHolder).update(rowHeaders[row]);
    }
    
    @Override
    public void onBindLeftTopHeaderViewHolder(@NonNull ViewHolderImpl viewHolder) {
        ((LeftTopHolder) viewHolder).update("9月");
    }
    
    @Override
    public int getColumnWidth(int column) {
        return cellWidth;
    }
    
    @Override
    public int getHeaderColumnHeight() {
        return cellHeight;
    }
    
    @Override
    public int getRowHeight(int row) {
        return cellHeight;
    }
    
    @Override
    public int getHeaderRowWidth() {
        return leftWidth;
    }
    
    public class CellHolder extends ViewHolderImpl {
        
        private TextView textView;
        
        public CellHolder(View itemView) {
            super(itemView);
            itemView.setBackgroundColor(
                    itemView.getContext().getResources().getColor(R.color.gray_light));
            textView = (TextView) itemView;
            textView.setGravity(Gravity.CENTER);
        }
        
        public void update(String string) {
            textView.setText(string);
        }
    }
    
    public class TopHolder extends ViewHolderImpl {
        
        private TextView textView;
        
        public TopHolder(View itemView) {
            super(itemView);
            itemView.setBackgroundColor(
                    itemView.getContext().getResources().getColor(R.color.colorPrimary));
            
            textView = (TextView) itemView;
            textView.setGravity(Gravity.CENTER);
        }
        
        public void update(String string) {
            textView.setText(string);
        }
    }
    
    public class LeftHolder extends ViewHolderImpl {
        
        private TextView textView;
        
        public LeftHolder(View itemView) {
            super(itemView);
            itemView.setBackgroundColor(
                    itemView.getContext().getResources().getColor(android.R.color.white));
            
            textView = (TextView) itemView;
            textView.setGravity(Gravity.CENTER);
        }
        
        public void update(String string) {
            textView.setText(string);
        }
    }
    
    public class LeftTopHolder extends ViewHolderImpl {
        
        private TextView textView;
        
        public LeftTopHolder(View itemView) {
            super(itemView);
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
