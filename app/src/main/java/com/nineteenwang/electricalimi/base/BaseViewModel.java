package com.nineteenwang.electricalimi.base;

import android.arch.lifecycle.ViewModel;
import rx.subscriptions.CompositeSubscription;

/**
 * @Author : Hyunwoong
 * @When : 2018-08-24 오전 3:09
 * @Homepage : https://github.com/gusdnd852
 */
public class BaseViewModel extends ViewModel {
    protected CompositeSubscription eventQueue = new CompositeSubscription();

    protected void injectDependencies() {
        // 만약 모델에 대한 의존성이 필요하면 여기에서 Dagger 로 주입하면됨.
    }

    @Override protected void onCleared() {
        super.onCleared();
        if (eventQueue != null) {
            eventQueue.unsubscribe();
            eventQueue = null;
        }
    }
}
