package cn.enjoytoday.views

import android.content.Context
import android.support.annotation.StyleRes
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

/**
 * @date 17-10-25.
 * @className TestViewGroup
 * @serial 1.0.0
 */
class TestViewGroup(private val mContext/**不可使用context作为类成员变量,与View中context冲突 **/: Context):ViewGroup(mContext) {


    /**
     * 构造器
     */
    constructor(mContext: Context,attributeSet: AttributeSet):this(mContext)

    constructor(mContext: Context,attributeSet: AttributeSet,defStyleAttr:Int):this(mContext,attributeSet)

    constructor(mContext: Context,attributeSet: AttributeSet,defStyleAttr: Int,defStyleRes: Int):this(mContext,attributeSet,defStyleAttr)


    /**
     * 初始化代码块
     */
    init {





    }


    override fun generateLayoutParams(attrs: AttributeSet): LayoutParams {
        return TestLayoutParams(context,attrs)
    }


    override fun generateDefaultLayoutParams(): LayoutParams {
        return TestLayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT)
    }


    override fun generateLayoutParams(p: LayoutParams): LayoutParams {
        return TestLayoutParams(p)
    }

    override fun checkLayoutParams(p: LayoutParams?): Boolean {
        return p is TestLayoutParams
    }

    /**
     * 这个方法用于获取子view的绘制顺序，一般返回i,若需要修改绘制顺序,则需要重写该方法
     */
    override fun getChildDrawingOrder(childCount: Int, i: Int): Int {
        return super.getChildDrawingOrder(childCount, i)
    }


    /**
     * 测量
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChildren(widthMeasureSpec, heightMeasureSpec)
//        for (x in 0 until childCount) {
//            measureChild(getChildAt(x),widthMeasureSpec, heightMeasureSpec)
//        }
        setMeasuredDimension(View.getDefaultSize(suggestedMinimumWidth,widthMeasureSpec),
                View.getDefaultSize(suggestedMinimumHeight,heightMeasureSpec))
    }


    /**
     * 布局方法,设置子view
     */
     override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        var left = left
        var top = top
        var right = right
        var bottom = bottom
        val count = childCount
        var childMeasureWidth = 0
        var childMeasureHeight = 0
        var layoutWidth = 0    // 容器已经占据的宽度
        var layoutHeight = 0   // 容器已经占据的宽度
        var maxChildHeight = 0 //一行中子控件最高的高度，用于决定下一行高度应该在目前基础上累加多少
        for (i in 0 until count) {
            val child = getChildAt(i)
            //注意此处不能使用getWidth和getHeight，这两个方法必须在onLayout执行完，才能正确获取宽高
            childMeasureWidth = child.measuredWidth
            childMeasureHeight = child.measuredHeight
            if (layoutWidth < width) {
                //如果一行没有排满，继续往右排列
                left = layoutWidth
                right = left + childMeasureWidth
                top = layoutHeight
                bottom = top + childMeasureHeight
            } else {
                //排满后换行
                layoutWidth = 0
                layoutHeight += maxChildHeight
                maxChildHeight = 0

                left = layoutWidth
                right = left + childMeasureWidth
                top = layoutHeight
                bottom = top + childMeasureHeight
            }

            layoutWidth += childMeasureWidth  //宽度累加
            if (childMeasureHeight > maxChildHeight) {
                maxChildHeight = childMeasureHeight
            }

            //确定子控件的位置，四个参数分别代表（左上右下）点的坐标值
            child.layout(left, top, right, bottom)
        }
    }

}


class TestLayoutParams : ViewGroup.MarginLayoutParams{
    constructor(width:Int,height:Int):super(width,height)
    constructor(context: Context,attributeSet: AttributeSet):super(context,attributeSet){





    }
    constructor(source:ViewGroup.LayoutParams):super(source)

}