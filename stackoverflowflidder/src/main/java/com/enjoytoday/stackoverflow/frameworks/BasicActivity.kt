package com.enjoytoday.stackoverflow.frameworks

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.enjoytoday.stackoverflow.R
import com.enjoytoday.stackoverflow.utils.DynamicResource
import kotlinx.android.synthetic.main.customer_action_bar.*

/**
 * Created by hfcai on 17/08/17.
 *所有activity的父类,用于处理一些共性问题
 *
 */
 open abstract class BasicActivity:Activity() {

    /**
     * 统一根目录
     */
    var rootView: ViewGroup?=null


    /**
     * 左侧图标
     */
    lateinit var categoryIcon: ImageView
    /**
     * 标题
     */
   lateinit var titleTextView: TextView


    lateinit var statusView:View


    /**
     * 子类实现
     */
    abstract fun  createLayout(savedInstanceState: Bundle?)


    /**
     * 不可重写.
     */
    @SuppressLint("NewApi")
    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createLayout(savedInstanceState)
        rootView=findViewById(android.R.id.content) as ViewGroup
        rootView?.setBackgroundColor(DynamicResource.Color.MAIN_TONES)

        if (actionBar!=null) {
            categoryIcon = category_home
            titleTextView = actionbar_title
        }


        /**
         * status bar.
         */

        statusView=View(this);
        statusView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,getStatusBarHeight(this))
        rootView?.addView(statusView)

    }









    fun getStatusBarHeight(mActivity:Activity):Int{
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        val height = resources.getDimensionPixelSize(resourceId)
        Log.e("dbw", "Status height:" + height)
        return height;

    }











}