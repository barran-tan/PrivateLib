package com.barran.lib.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * test entry
 *
 * Created by tanwei on 2017/11/1.
 */

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.activity_main_test_lottie:
                intent.setClass(MainActivity.this, TestLottieActivity.class);
                break;

            case R.id.activity_main_test_view:
                intent.setClass(MainActivity.this, TestViewActivity.class);
                break;
        }
        
        startActivity(intent);
    }
}
