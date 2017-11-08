package cn.enjoytoday.pluginsapp

import android.content.Context
import android.util.Log
import android.widget.Toast

/**
 * 土司
 */
fun Context.toast(msg:String){
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}

/**
 * log信息
 */
fun Any.log(msg: String){
    log(this::class.java.simpleName,msg)
}

fun Any.log(tag:String, msg: String){
    Log.e(tag, msg)
}