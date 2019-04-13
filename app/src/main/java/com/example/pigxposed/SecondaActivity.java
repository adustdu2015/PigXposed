package com.example.pigxposed;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.aitangba.swipeback.SwipeBackActivity;

public class SecondaActivity extends SwipeBackActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }
}
