package cn.enjoytoday.test

import android.app.Activity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager

import cn.enjoytoday.R

class TestActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_test)


        var loader=classLoader
        while (loader != null) {
            println("加载器类型:${loader.toString()}")
            loader = loader.parent
        }






    }
}
