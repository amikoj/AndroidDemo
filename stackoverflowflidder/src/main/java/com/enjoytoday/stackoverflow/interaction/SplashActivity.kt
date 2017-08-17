package com.enjoytoday.stackoverflow.interaction

import android.os.Bundle
import android.os.Handler
import com.enjoytoday.stackoverflow.R
import com.enjoytoday.stackoverflow.frameworks.BasicActivity

class SplashActivity : BasicActivity() {

   private var handler:Handler= Handler()







    override fun createLayout(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_splash)
        handlerJump()
    }


    fun handlerJump(){
        handler.postDelayed({
            this.startActivity(android.content.Intent(this, MainActivity::class.java))
        },3000)
    }


    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}

