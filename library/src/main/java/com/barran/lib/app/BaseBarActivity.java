package com.barran.lib.app;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;

import com.barran.lib.R;

/**
 * 封装{@linkplain androidx.appcompat.widget.Toolbar ToolBar}
 *
 * Created by tanwei on 2017/10/20.
 */

public class BaseBarActivity extends BaseActivity {
    
    protected Toolbar mToolbar;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_bar);
        mToolbar = (Toolbar) findViewById(R.id.activity_base_toolBar);
        setSupportActionBar(mToolbar);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
