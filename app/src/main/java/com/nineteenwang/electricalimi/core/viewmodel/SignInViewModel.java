package com.nineteenwang.electricalimi.core.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.view.View;
import com.nineteenwang.electricalimi.base.BaseViewModel;
import com.nineteenwang.electricalimi.utill.RxSchedulers;
import com.nineteenwang.electricalimi.utill.SingleLiveEvent;
import rx.Observable;

/**
 * @Author : Hyunwoong
 * @When : 2018-08-24 오전 4:19
 * @Homepage : https://github.com/gusdnd852
 */
public class SignInViewModel extends BaseViewModel {

    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();

    public SingleLiveEvent signInEvent = new SingleLiveEvent();
    public SingleLiveEvent signUpEvent = new SingleLiveEvent();

    public void onSignInButtonClicked(View view) {
        eventQueue.add(Observable.just(0)
                .observeOn(RxSchedulers.get().androidThread())
                .subscribeOn(RxSchedulers.get().ioThread())
                .subscribe(n -> signInEvent.call()));
    }

    public void onSignUpButtonClicked(View view) {
        eventQueue.add(Observable.just(0)
                .observeOn(RxSchedulers.get().androidThread())
                .subscribeOn(RxSchedulers.get().ioThread())
                .subscribe(n -> signUpEvent.call()));
    }
}
