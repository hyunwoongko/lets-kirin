package com.nineteenwang.electricalimi.utill;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @Author : Hyunwoong
 * @When : 2018-08-24 오전 3:55
 * @Homepage : https://github.com/gusdnd852
 */
public class RxSchedulers {

    private volatile static RxSchedulers instance;

    private RxSchedulers() {
    }

    public synchronized static RxSchedulers get() {
        if (instance == null) {
            instance = new RxSchedulers();
        }
        return instance;
    } // singleton

    public Scheduler ioThread() {
        return Schedulers.io();
    }// 비동기 입출력 쓰레드

    public Scheduler computeThread() {
        return Schedulers.computation();
    }// 콜백 및 연산 쓰레드

    public Scheduler immediateThread() {
        return Schedulers.immediate();
    } // 즉시 수행 쓰레드

    public Scheduler trampolinThread() {
        return Schedulers.trampoline();
    } // 현재 작업 종료후 즉시수행 쓰레드

    public Scheduler androidThread() {
        return AndroidSchedulers.mainThread();
    }// 안드로이드 메인 쓰레드


}
