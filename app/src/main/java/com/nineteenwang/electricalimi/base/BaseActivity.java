package com.nineteenwang.electricalimi.base;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * @Author : Hyunwoong
 * @When : 2018-08-24 오전 3:06
 * @Homepage : https://github.com/gusdnd852
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected abstract void constructView();

    protected abstract void addObserver();

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewModelProviders.of(this)
                .get(BaseViewModel.class)
                .injectDependencies();

        constructView();
        addObserver();
    }

    @Override public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
