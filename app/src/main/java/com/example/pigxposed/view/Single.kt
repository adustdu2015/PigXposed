package com.example.pigxposed.view

import android.util.Log

//单例写法
class Single {
    companion object {
        fun getSingle():Single{
            return Holder.instance
        }
    }
    private object Holder{
        var instance = Single()
    }
    fun getOne(str:String){
        when(str){
            "11"-> Log.d("ok","ok")
            "11"-> Log.d("ok","ok")
            "11"-> Log.d("ok","ok")
            "11"-> Log.d("ok","ok")
        }
    }
}
//类的动态代理 by实现
//数据类 data