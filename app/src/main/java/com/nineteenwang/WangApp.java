package com.nineteenwang;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import com.nineteenwang.electricalimi.cmp.InfiniteService;
import com.nineteenwang.electricalimi.core.view.TutorialActivity;
import com.nineteenwang.electricalimi.utill.PreferenceHelper;

/**
 * @Author : Hyunwoong
 * @When : 2018-08-24 오후 7:05
 * @Homepage : https://github.com/gusdnd852
 */
public class WangApp extends Application {


    @Override public void onCreate() {
        if (PreferenceHelper.getInstance(this).getBoolean("firstUser", true)) {
            startService(new Intent(this, InfiniteService.class));
        }
        super.onCreate();
    }

    public static String Permission[] = {
            Manifest.permission.RECORD_AUDIO
    };

    public static boolean hasPermission(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }  // multiple permission
}
