package com.nineteenwang.electricalimi.cmp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

/**
 * @Author : Hyunwoong
 * @When : 2018-08-24 오후 11:50
 * @Homepage : https://github.com/gusdnd852
 */
public class BootReceiver extends BroadcastReceiver {
    @Override public void onReceive(Context context, Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(new Intent(context, InfiniteService.class));
            Log.d("SDSDSDSDSDSD","SDSDSDSDSDSDSDSD");
        } else {
            context.startService(new Intent(context, InfiniteService.class));
            Log.d("SDSDSDSDSDSD","SDSDSDSDSDSDSDSD");
        }
    }
}