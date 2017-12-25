package com.barran.lib.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.barran.lib.view.UpView;

/**
 * test view display
 *
 * Created by tanwei on 2017/12/25.
 */

public class TestViewActivity extends AppCompatActivity {
    
    private UpView upView;
    
    private EditText edit;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_view);
        
        upView = findViewById(R.id.activity_test_view_upview);
        edit = findViewById(R.id.activity_test_view_edit);
        
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.activity_test_view_set:
                        String trim = edit.getText().toString().trim();
                        if (!TextUtils.isEmpty(trim)) {
                            upView.setCount(Integer.parseInt(trim));
                        }
                        break;
                }
            }
        };
        findViewById(R.id.activity_test_view_set).setOnClickListener(listener);
    }
}
