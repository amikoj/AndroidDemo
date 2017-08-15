package cn.enjoytoday.bmob

import android.app.Activity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import cn.bmob.v3.Bmob

import cn.enjoytoday.R


/**
 * bmob test activity.
 */
class BmobActivity constructor():Activity() {




    constructor(name:String,type:String):this() {
        Log.e("name", name)
    }




    var BMOB_APP_ID:String="291b15675a92224a9170e6410fca8ff2"

    var fragment: FrameLayout?=null
    var main_layout: View?=null
    var register_layout: View?=null
    var sign_in_layout:View?=null
    var sign_out_layout:View?=null
    var upload_layout:View?=null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmob)
        Bmob.initialize(this,BMOB_APP_ID)
        initView()
    }


    /**
     * init view.
     */
    fun initView(){
        fragment= this.findViewById(R.id.content) as FrameLayout?
        main_layout=LayoutInflater.from(this).inflate(R.layout.bmob_main,null)
        register_layout=LayoutInflater.from(this).inflate(R.layout.bmob_register_layout,null)
        sign_in_layout=LayoutInflater.from(this).inflate(R.layout.bmob_sign_in_layout,null)
        sign_out_layout=LayoutInflater.from(this).inflate(R.layout.bmob_sign_out_layout,null)
        upload_layout=LayoutInflater.from(this).inflate(R.layout.bmob_upload_layout,null)

        /**
         * 当fragment为空时抛出异常
         * fragment!!.removeAllViews()
         *
         * 当fragment为空时返回为空
         */
        fragment?.removeAllViews()
        fragment?.addView(main_layout)


    }


    fun onClick(view:View){
        when(view.id){
            R.id.register ->{
                fragment?.removeAllViews()
                fragment?.addView(register_layout)

            }

            R.id.sing_in -> {
                fragment?.removeAllViews()
                fragment?.addView(sign_in_layout)
            }

            R.id.sign_out -> {
                fragment?.removeAllViews()
                fragment?.addView(sign_out_layout)
            }

            R.id.upload -> {
                fragment?.removeAllViews()
                fragment?.addView(upload_layout)
            }






            else -> {

                toast("No found this id.")
            }
        }


    }


    /**
     * toast add.
     */
    fun Activity.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, message, duration).show()
    }



}
