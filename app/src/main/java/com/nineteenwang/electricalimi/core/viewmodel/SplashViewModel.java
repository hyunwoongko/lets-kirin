package com.nineteenwang.electricalimi.core.viewmodel;

import android.view.View;
import com.nineteenwang.electricalimi.base.BaseViewModel;
import com.nineteenwang.electricalimi.utill.RxSchedulers;
import com.nineteenwang.electricalimi.utill.SingleLiveEvent;
import rx.Observable;

import java.util.concurrent.TimeUnit;

/**
 * @Author : Hyunwoong
 * @When : 2018-08-24 오전 3:09
 * @Homepage : https://github.com/gusdnd852
 */
public class SplashViewModel extends BaseViewModel {

    public SingleLiveEvent splashEvent = new SingleLiveEvent();

    public void showSplash(View view) {
        eventQueue.add(Observable.just(0)
                .delay(4, TimeUnit.SECONDS)
                .observeOn(RxSchedulers.get().androidThread())
                .subscribe(n -> splashEvent.call()));
    }

}
