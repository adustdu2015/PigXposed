package com.example.pigxposed

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_setting.*
import java.io.Closeable

class SettingActivity : AppCompatActivity() {
    lateinit var tv: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        startActivity(Intent(this@SettingActivity, MainActivity::class.java))
        tv = tv_one
        tv.setOnClickListener {
            run {
                startActivity(Intent(this@SettingActivity, MainActivity::class.java))
            }
        }
        button.setOnClickListener {
            run {
                startActivity(Intent(this@SettingActivity, MainActivity::class.java))
            }
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        if(tv != null){
        }
    }

}
