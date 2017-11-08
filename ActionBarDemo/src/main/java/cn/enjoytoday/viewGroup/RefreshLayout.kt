package cn.enjoytoday.viewGroup

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

/**
 *完成基本的上拉刷新和下拉加载功能
 */
class RefreshLayout:ViewGroup{


    constructor(mContext: Context):super(mContext)

    constructor(mContext: Context,attributeSet: AttributeSet):super(mContext,attributeSet)


    constructor(mContext: Context,attributeSet: AttributeSet,defStyle:Int):super(mContext,attributeSet,defStyle)

    constructor(mContext: Context,attributeSet: AttributeSet,defStyle: Int,defRes:Int):super(mContext,attributeSet,defStyle,defRes)


    /**
     * 测量子控件大小和自身大小
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChildren(widthMeasureSpec, heightMeasureSpec)  //测量子布局控件大小

        setMeasuredDimension(View.getDefaultSize(suggestedMinimumWidth,widthMeasureSpec),
                View.getDefaultSize(suggestedMinimumHeight,heightMeasureSpec))   //测量自身大小
    }


    /**
     *置放位置
     */
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

    }





















    /**
     * 自定义RefreshLayout布局参数
     */
    class RefreshLayoutParams:MarginLayoutParams{
        constructor(mContext: Context,attributeSet: AttributeSet):super(mContext,attributeSet)


    }



}

