package com.nineteenwang.electricalimi.core.view;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.nineteenwang.electricalimi.R;
import com.nineteenwang.electricalimi.base.BaseActivity;
import com.nineteenwang.electricalimi.core.viewmodel.SignUpViewModel;
import com.nineteenwang.electricalimi.databinding.SignUpView;
import es.dmoral.toasty.Toasty;


/**
 * @Author : Hyunwoong
 * @When : 2018-08-24 오전 8:20
 * @Homepage : https://github.com/gusdnd852
 */
public class SignUpActivity extends BaseActivity implements LifecycleOwner {

    private SignUpView view;
    private SignUpViewModel viewModel;

    @Override protected void constructView() {
        viewModel = ViewModelProviders.of(this).get(SignUpViewModel.class);
        view = DataBindingUtil.setContentView(this, R.layout.sign_up_view);
        view.setViewModel(viewModel);

    }

    @Override protected void addObserver() {
        viewModel.signUpEvenet.observe(this, SignUpObserver());
    }


    public Observer SignUpObserver() {
        return o -> {
            liveDataInit();
            showProgress();
            view.progress.setVisibility(View.VISIBLE);
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(viewModel.email.getValue(), viewModel.password.getValue())
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            Toasty.success(this, "회원가입에 성공했습니다.", Toast.LENGTH_SHORT).show();
                            view.progress.setVisibility(View.GONE);
                            finish();
                        } else {
                            if (viewModel.password.getValue().trim().length() < 6) {
                                Toasty.error(this, "비밀번호는 6자 이상 입력해주세요.", Toast.LENGTH_SHORT).show();
                                view.progress.setVisibility(View.GONE);
                            }else {
                                Toasty.error(this, "네트워크 불량 혹은 중복된 이메일입니다.", Toast.LENGTH_SHORT).show();
                                view.progress.setVisibility(View.GONE);
                            }
                        }
                    });
        };
    }

    private void liveDataInit() {
        if (viewModel.email.getValue() == null || viewModel.password.getValue() == null) {
            viewModel.email.setValue(" ");
            viewModel.password.setValue(" ");
        }
    }


    public void showProgress() {
        String uriPath = "android.resource://" + getPackageName() + "/" + R.raw.progress;
        Uri uri = Uri.parse(uriPath);
        view.progress.setVideoURI(uri);
        view.progress.requestFocus();
        view.progress.setZOrderOnTop(true);//this line solve the problem
        view.progress.start();
    }
}
