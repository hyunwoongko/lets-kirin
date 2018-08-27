package com.nineteenwang.electricalimi.core.view;

import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nineteenwang.electricalimi.R;
import com.nineteenwang.electricalimi.base.BaseActivity;
import es.dmoral.toasty.Toasty;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author : Hyunwoong
 * @When : 2018-08-25 오전 4:12
 * @Homepage : https://github.com/gusdnd852
 */
public class HomeActivity extends BaseActivity {
    @Override protected void constructView() {
        setContentView(R.layout.home_view);
        findViewById(R.id.airc).setOnClickListener(v -> FirebaseDatabase.getInstance().getReference()
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override public void onDataChange(DataSnapshot dataSnapshot) {
                        Map<String, Long> map = new HashMap<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            map.put(snapshot.getKey(), (Long) snapshot.getValue());
                        }
                        long air = map.get("AIR_STATUS");
                        if (air == 1) {
                            Toasty.info(HomeActivity.this, "에어컨은 현재 켜져있습니다.", Toast.LENGTH_SHORT).show();
                        } else if (air == 0) {
                            Toasty.info(HomeActivity.this, "에어컨은 현재 꺼져있습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override public void onCancelled(DatabaseError databaseError) {

                    }
                }));

        findViewById(R.id.light).setOnClickListener(v -> FirebaseDatabase.getInstance().getReference()
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override public void onDataChange(DataSnapshot dataSnapshot) {
                        Map<String, Long> map = new HashMap<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            map.put(snapshot.getKey(), (Long) snapshot.getValue());
                        }
                        long led = map.get("LED_STATUS");
                        if (led == 1) {
                            Toasty.info(HomeActivity.this, "전등은 현재 켜져있습니다.", Toast.LENGTH_SHORT).show();
                        } else if (led == 0) {
                            Toasty.info(HomeActivity.this, "전등은 현재 꺼져있습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override public void onCancelled(DatabaseError databaseError) {
                    }
                }));

        findViewById(R.id.ref).setOnClickListener(v -> Toasty.info(this, "냉장고는 현재 켜져있습니다. ", Toast.LENGTH_SHORT).show());
    }

    @Override protected void addObserver() {
    }
}
