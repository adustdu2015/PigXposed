package com.example.pigxposed;

import android.util.Log;
import android.widget.Toast;

import de.robv.android.xposed.XposedBridge;

public class demo {
    private final static int sMoney = 200;
    String ages = "age";
//    构造方法
    public demo() {
        Log.d("TAG","调用构造方法");
    }
//    内部私有方法
    private void hidden_function(){
        ages = "name";
    }

    Dog dog = new Dog(new Animal() {
        @Override
        public void eat() {
            Log.d("TAG","Dog Eat");
        }
    });
//    内部类
    class innerClass {
       private int pMoney = 100;
       String scret(String arg1 , boolean b0){
           return "Screct";
       }

    }
}
