package com.nineteenwang.electricalimi.core.view;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import com.nineteenwang.electricalimi.R;
import com.nineteenwang.electricalimi.base.BaseActivity;
import com.nineteenwang.electricalimi.utill.PreferenceHelper;

import java.util.Arrays;
import java.util.List;

/**
 * @Author : Hyunwoong
 * @When : 2018-08-25 오전 4:03
 * @Homepage : https://github.com/gusdnd852
 */
public class TutorialActivity extends BaseActivity {

    private List<Drawable> imgList;
    private int count = 0;

    @Override protected void constructView() {
        imgList = Arrays.asList(
                getResources().getDrawable(R.drawable.t00),
                getResources().getDrawable(R.drawable.t01),
                getResources().getDrawable(R.drawable.t02),
                getResources().getDrawable(R.drawable.t03),
                getResources().getDrawable(R.drawable.t04),
                getResources().getDrawable(R.drawable.t05),
                getResources().getDrawable(R.drawable.t06),
                getResources().getDrawable(R.drawable.t07),
                getResources().getDrawable(R.drawable.t08),
                getResources().getDrawable(R.drawable.t09),
                getResources().getDrawable(R.drawable.t10),
                getResources().getDrawable(R.drawable.t11));
        setContentView(R.layout.tutorial_view);
        findViewById(R.id.tutorial).setBackground(imgList.get(count));
        count++;
        findViewById(R.id.tutorial).setOnClickListener(v -> {
            if (count < 12) {
                findViewById(R.id.tutorial).setBackground(imgList.get(count));
                count++;
            } else {
                startActivity(new Intent(this, MainActivity.class));
                finish();
                PreferenceHelper.getInstance(this).setBoolean("firstUser", false);
            }
        });
    }

    @Override protected void addObserver() {

    }
}
