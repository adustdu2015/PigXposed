package com.example.pigxposed.view

import java.io.File

open class PigApplication {
    var name = "age"
    val age = 28
    val name2: String? = null
    fun test(){
        name = name2!!
        Pig.good(name)
        Pig.good(name)
    }

    //默认值
    fun  goodss(str:String ="张涛"):String{
        println("$str")
        return ""
    }

     object Pig{
//        kotlin中没有静态方法和静态属性 用@JvmStatic 注解标示
         //Pig.good()
        @JvmStatic
        fun good(str:String):String{
            return "good"
        }
    }
//    扩展函数
    fun File.getOne(str:String):String = str
    //lambda 闭包
    var echo = { str:String->{
        println(str)
    }}
    fun goo(){
    var file = File("PigmentApplicatyion.kt")
        file.getOne("one")//    扩展函数
        echo.invoke("ok")     //lambda 闭包
        echo("echo")
    }
}
