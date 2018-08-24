package com.nineteenwang.electricalimi.utill;

import android.databinding.BindingAdapter;
import android.view.View;

/**
 * @Author : Hyunwoong
 * @When : 2018-08-24 오전 3:45
 * @Homepage : https://github.com/gusdnd852
 */
public class CustomBinder {

    @BindingAdapter("onCreate")
    public static void onCreate(View view, View.OnClickListener listener) {
        if (view.getVisibility() == View.VISIBLE) {
            listener.onClick(view);
        }
    } // 뷰가 인플레이트 되자마자 실행하는 액션임
}
