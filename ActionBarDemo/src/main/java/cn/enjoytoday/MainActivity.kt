package cn.enjoytoday


import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_main.*
import android.view.animation.ScaleAnimation
import dalvik.system.DexClassLoader


class MainActivity : Activity() {

    private val sato0 = ScaleAnimation(1f, 0f, 1f, 1f,
            Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_PARENT, 0.5f)

    private val sato1 = ScaleAnimation(0f, 1f, 1f, 1f,
            Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_PARENT, 0.5f)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        showUp()
        sato0.duration=500
        sato1.duration=500

//        sato0.fillAfter = true
//        sato1.fillAfter=true

        ClassLoader.getSystemClassLoader()

        sato0.setAnimationListener(object :Animation.AnimationListener{
            override fun onAnimationRepeat(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                if (image_icon.visibility==View.VISIBLE){
//                    image_icon.visibility=View.GONE
                    image_icon.clearAnimation()
                    showBack()
                    image_back_icon.startAnimation(sato1)
                }else{
//                    image_back_icon.visibility=View.GONE
                    image_back_icon.clearAnimation()
                    showUp()
                    image_icon.startAnimation(sato1)
                }

            }

            override fun onAnimationStart(animation: Animation?) {

            }

        })



        sato1.setAnimationListener(object :Animation.AnimationListener{
            override fun onAnimationRepeat(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                if (image_back_icon.visibility==View.VISIBLE){
                    image_back_icon.clearAnimation()
                    showUp()
                    image_icon.startAnimation(sato0)
                }else{
                    image_icon.clearAnimation()
                    showBack()
                    image_back_icon.startAnimation(sato0)
                }

            }

            override fun onAnimationStart(animation: Animation?) {

            }

        })

        if (image_icon.visibility==View.VISIBLE){
            image_icon.startAnimation(sato0)
        }else{
            image_back_icon.startAnimation(sato1)
        }

//
//        roate_layout.setOnClickListener({
//
//
//
//
//        })


//        val animation=AnimationUtils.loadAnimation(this,R.anim.roate)
//        image_icon.animation=animation
//        animation.start()


    }








    private fun showUp(){
        image_icon.visibility=View.VISIBLE
        image_back_icon.visibility=View.GONE
    }


    private fun showBack(){
        image_icon.visibility=View.GONE
        image_back_icon.visibility=View.VISIBLE

    }



    fun onClick(view:View){
       var id:Int= view.id
        var number=0
        when (id) {
            R.id.theme1 -> number=1
            R.id.theme2 -> number=2
            R.id.theme3 -> number=3
            R.id.theme4 -> number=4
        }
        var intent = Intent(this,ThemeActivity::class.java)
        intent.putExtra("number",number)
        startActivity(intent)

    }





}
