package com.barran.lib.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

/**
 * test lottie
 *
 * Created by tanwei on 2017/11/1.
 */

public class TestLottieActivity extends AppCompatActivity {

    private static final String TAG = "lottie";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_lottie);

        LottieAnimationView animationView = findViewById(R.id.animation_view);
        animationView.setAnimation("data.json");
        animationView.playAnimation();
    }
}
