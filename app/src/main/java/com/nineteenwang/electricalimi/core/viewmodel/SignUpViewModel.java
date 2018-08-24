package com.nineteenwang.electricalimi.core.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.view.View;
import com.nineteenwang.electricalimi.base.BaseViewModel;
import com.nineteenwang.electricalimi.cmp.InfiniteService;
import com.nineteenwang.electricalimi.utill.RxSchedulers;
import com.nineteenwang.electricalimi.utill.SingleLiveEvent;
import rx.Observable;

/**
 * @Author : Hyunwoong
 * @When : 2018-08-24 오전 8:20
 * @Homepage : https://github.com/gusdnd852
 */
public class SignUpViewModel extends BaseViewModel {


    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();

    public SingleLiveEvent signUpEvenet = new SingleLiveEvent();

    public void onSingUpButtonClicked(View view) {
        eventQueue.add(Observable.just(0)
                .subscribeOn(RxSchedulers.get().ioThread())
                .observeOn(RxSchedulers.get().androidThread())
                .subscribe(n -> signUpEvenet.call()));
    }

}
