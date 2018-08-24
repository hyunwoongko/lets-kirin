package com.nineteenwang.electricalimi.core.view;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import com.nineteenwang.electricalimi.R;
import com.nineteenwang.electricalimi.base.BaseActivity;
import com.nineteenwang.electricalimi.core.viewmodel.SplashViewModel;
import com.nineteenwang.electricalimi.databinding.SplashView;

/**
 * @Author : Hyunwoong
 * @When : 2018-08-24 오전 3:06
 * @Homepage : https://github.com/gusdnd852
 */
public class SplashActivity extends BaseActivity implements LifecycleOwner {

    private SplashView view;
    private SplashViewModel splashViewModel;

    @Override protected void constructView() {
        splashViewModel = ViewModelProviders.of(this).get(SplashViewModel.class);
        view = DataBindingUtil.setContentView(this, R.layout.splash_view);
        view.setSplashViewModel(splashViewModel);
        String uriPath = "android.resource://" + getPackageName() + "/" + R.raw.splash;
        Uri uri = Uri.parse(uriPath);
        view.splashVideo.setVideoURI(uri);
        view.splashVideo.requestFocus();
        view.splashVideo.setZOrderOnTop(true);//this line solve the problem
        view.splashVideo.start();
    }

    @Override protected void addObserver() {
        splashViewModel.splashEvent.observe(this, splashObserver());
    }

    public Observer splashObserver() {
        return o -> {
            startActivity(new Intent(this, SignInActivity.class));
            finish();
        };
    }
}
