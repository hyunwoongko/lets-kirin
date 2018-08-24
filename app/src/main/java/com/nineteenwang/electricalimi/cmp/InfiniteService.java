package com.nineteenwang.electricalimi.cmp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import rx.Observable;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.nineteenwang.electricalimi.cmp.OpenMicService.speechRecognizer;

/**
 * @Author : Hyunwoong
 * @When : 2018-08-25 오전 1:21
 * @Homepage : https://github.com/gusdnd852
 */
public class InfiniteService extends Service {

    TextToSpeech tts;

    @Override public void onCreate() {
        super.onCreate();
        tts = new TextToSpeech(getApplicationContext(), status -> {

            if (status != TextToSpeech.ERROR) {
                tts.setLanguage(Locale.KOREAN);
            }
        });

    }

    @Nullable @Override public IBinder onBind(Intent intent) {
        return null;
    }

    @Override public int onStartCommand(Intent intent, int flags, int startId) {
        FirebaseDatabase.getInstance()
                .getReference().addValueEventListener(new ValueEventListener() {
            @Override public void onDataChange(DataSnapshot dataSnapshot) {

                Map<String, Long> map = new HashMap<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    map.put(snapshot.getKey(), (Long) snapshot.getValue());
                }

                Long coffee = map.get("COFFEE_STATUS");
                Long distance = map.get("DISTANCE_STATUS");
                Long led = map.get("LED_STATUS");

                if (led == 1 && coffee == 1 && distance == 1) {
                    tts.speak("테스트 1 2 3", TextToSpeech.QUEUE_FLUSH, null);
                    Observable.just(0)
                            .delay(3, TimeUnit.SECONDS)
                            .subscribe(n -> startService(new Intent(InfiniteService.this, OpenMicService.class)));
                }
            }

            @Override public void onCancelled(DatabaseError databaseError) {
            }
        });
        return START_STICKY;
    }
}
