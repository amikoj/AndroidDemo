package com.enjoytoday.stackoverflow.frameworks

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.View
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
    var rootView: View?=null


    /**
     * 左侧图标
     */
    var categoryIcon: ImageView=category_home

    /**
     * 标题
     */
    var titleTextView: TextView=actionbar_title


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
        rootView=findViewById(android.R.id.content)
        rootView?.setBackgroundColor(DynamicResource.Color.MAIN_TONES)



    }











}