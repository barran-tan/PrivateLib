package com.barran.lib.thirdlib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.barran.lib.test.R;
import com.barran.lib.utils.time.SelectTimeUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.zhouchaoyuan.excelpanel.ExcelPanel;

/**
 * test ExcelPanel
 * 
 * Created by tanwei on 2018/10/16.
 */

public class TestExcelActivity extends AppCompatActivity {
    
    private static final int DAYS_COUNT = 31;
    private static final int HOURS_COUNT = 24 - 6;
    
    private List<String> rowHeaders;
    private List<String> colHeaders;
    private List<List<String>> cells;
    private ExcelPanel excelPanel;
    private ExcelAdapter mAdapter;
    private View.OnClickListener blockListener;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_test_excel);
        
        prepareData();
        
        excelPanel = findViewById(R.id.content_container);
        mAdapter = new ExcelAdapter(this);
        excelPanel.setAdapter(mAdapter);
        mAdapter.setAllData(rowHeaders, colHeaders, cells);
    }
    
    private void prepareData() {
        rowHeaders = new ArrayList<>(HOURS_COUNT);
        for (int i = 0; i < HOURS_COUNT; i++) {
            rowHeaders.add((i + 6) + "-" + (i + 7));
        }
        
        colHeaders = new ArrayList<>(DAYS_COUNT);
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < DAYS_COUNT; i++) {
            colHeaders.add(SelectTimeUtils.getWeekDay(calendar.getTime()).toString()
                    + "\n" + calendar.get(Calendar.DAY_OF_MONTH));
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        
        // 纵向的[list][list]
        cells = new ArrayList<>(HOURS_COUNT);
        for (int i = 0; i < HOURS_COUNT; i++) {
            ArrayList<String> list = new ArrayList<>(DAYS_COUNT);
            
            for (int j = 0; j < DAYS_COUNT; j++) {
                list.add(i + "," + j);
            }
            
            cells.add(list);
        }
    }
}
