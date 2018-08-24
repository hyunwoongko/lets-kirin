package com.nineteenwang.electricalimi.core.view;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.nineteenwang.electricalimi.R;
import com.nineteenwang.electricalimi.base.BackPressActivity;
import com.nineteenwang.electricalimi.core.viewmodel.SignInViewModel;
import com.nineteenwang.electricalimi.databinding.SignInView;
import com.nineteenwang.electricalimi.utill.PreferenceHelper;
import es.dmoral.toasty.Toasty;

import static com.nineteenwang.WangApp.Permission;
import static com.nineteenwang.WangApp.hasPermission;


/**
 * @Author : Hyunwoong
 * @When : 2018-08-24 오전 3:50
 * @Homepage : https://github.com/gusdnd852
 */
public class SignInActivity extends BackPressActivity implements LifecycleOwner {

    private SignInView view;
    private SignInViewModel viewModel;

    @Override protected void constructView() {
        viewModel = ViewModelProviders.of(this).get(SignInViewModel.class);
        view = DataBindingUtil.setContentView(this, R.layout.sign_in_view);
        view.setViewModel(viewModel);
        getPermission();
    }

    public void showProgress() {
        String uriPath = "android.resource://" + getPackageName() + "/" + R.raw.progress;
        Uri uri = Uri.parse(uriPath);
        view.progress.setVideoURI(uri);
        view.progress.requestFocus();
        view.progress.setZOrderOnTop(true);//this line solve the problem
        view.progress.start();
    }

    @Override protected void addObserver() {
        viewModel.signInEvent.observe(this, signInObserver());
        viewModel.signUpEvent.observe(this, signUpObserver());
    }

    private Observer signInObserver() {
        return o -> {
            liveDataInit();
            showProgress();
            view.progress.setVisibility(View.VISIBLE);
            FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(viewModel.email.getValue(), viewModel.password.getValue())
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            Toasty.success(this, "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show();
                            view.progress.setVisibility(View.GONE);
                            if (PreferenceHelper.getInstance(this).getBoolean("firstUser", true)) {
                                startActivity(new Intent(this, TutorialActivity.class));
                                finish();
                            }else{
                                startActivity(new Intent(this, MainActivity.class));
                                finish();
                            }
                        } else {
                            Toasty.error(this, "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show();
                            view.progress.setVisibility(View.GONE);
                        }
                    });
        };
    }

    private Observer signUpObserver() {
        return o -> startActivity(new Intent(this, SignUpActivity.class));
    }

    private void liveDataInit() {
        if (viewModel.email.getValue() == null || viewModel.password.getValue() == null) {
            viewModel.email.setValue(" ");
            viewModel.password.setValue(" ");
        }
    }


    private void getPermission() {
        if (!hasPermission(this, Permission)) {
            ActivityCompat.requestPermissions(this, Permission, 1);
        }
    }
}
