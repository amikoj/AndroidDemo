package com.enjoytoday.stackoverflow

import android.app.Activity
import android.os.Bundle
import android.os.Handler

class SplashActivity : Activity() {
    var handler:Handler= Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        handlerJump()
    }

    fun handlerJump(){
        handler.postDelayed({
            this.startActivity(android.content.Intent(this,MainActivity::class.java))
        },3000)
    }


    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}
