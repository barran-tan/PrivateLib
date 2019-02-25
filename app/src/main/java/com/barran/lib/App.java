package com.barran.lib;

import android.app.Application;

import com.barran.lib.utils.DisplayUtil;
import com.barran.lib.utils.log.Logs;

/**
 * Created by tanwei on 2019/2/25.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        
        Logs.init(this);
        Logs.setDebug(true);
        DisplayUtil.init(this);
    }
}
