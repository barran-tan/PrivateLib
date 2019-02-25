package com.barran.lib.thirdlib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.barran.lib.test.R;
import com.barran.lib.utils.log.Logs;
import com.cleveroad.adaptivetablelayout.AdaptiveTableLayout;
import com.cleveroad.adaptivetablelayout.OnItemClickListener;

/**
 * 
 * test AdaptiveTableLayout
 * 
 * Created by tanwei on 2018/10/17.
 */

public class TestAdaptiveTableLayoutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_test_adaptive_table_layout);
        
        AdaptiveTableLayout mTableLayout = findViewById(R.id.tableLayout);
        
        AdaptiveAdapter mTableAdapter = new AdaptiveAdapter(this);
        mTableAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int row, int column) {
                Logs.v("onItemClick,row:" + row + ",column:" + column);
            }
            
            @Override
            public void onRowHeaderClick(int row) {
                Logs.v("onRowHeaderClick,row:" + row);
            }
            
            @Override
            public void onColumnHeaderClick(int column) {
                Logs.v("onColumnHeaderClick,column:" + column);
            }
            
            @Override
            public void onLeftTopHeaderClick() {
                
            }
        });
        // mTableAdapter.setOnItemLongClickListener();
        mTableLayout.setAdapter(mTableAdapter);
        
    }
    
}
