package com.example.pigxposed;

import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {
    @Inject
    LocationManager locationManager;
    private DaggerApplicationComponent component;
    TextView tv_bac;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_bac = findViewById(R.id.tv_bac);
        tv_bac.setBackgroundColor(-1);
        Log.e("TAG",Color.parseColor("#000000")+"");
        component = (DaggerApplicationComponent) DaggerApplicationComponent.builder().androidModule(new AndroidModule(getApplication()))
                .build();
        component.inject(this);
        tv_bac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SecondaActivity.class));
            }
        });
        tv_bac.performClick();
        Toast.makeText(MainActivity.this,stringFromJNI(),Toast.LENGTH_SHORT).show();
        Toast.makeText(MainActivity.this,hello(),Toast.LENGTH_SHORT).show();
    }

    public native String stringFromJNI();
    public native String hello();
    static {
        System.loadLibrary("native-lib");
    }
}
