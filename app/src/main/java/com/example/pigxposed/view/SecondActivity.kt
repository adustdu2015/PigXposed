package com.example.pigxposed.view

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast

//实现接口的时候，使用一个,即可
//open标示不final
//构造方法
class SecondActivity(int:Int) :AppCompatActivity(),View.OnClickListener{
//    如果想在构造方法中存在一些变换的话，需要一个init代码快
    init {
    if(int == 1){
        Toast.makeText(this,"ins",Toast.LENGTH_SHORT).show()
    }
        println("mmmmmmmmmm")
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

//静态类
    companion object {
        fun goggods(str:String){

        }
    }
//    如果存在多个构造方法需要显示声明
}