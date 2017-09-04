package cn.enjoytoday.views

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.Scroller

/**
 * 需要对RecyclerView进行定义,实现如下功能
 * 1.上拉刷新
 * 2.下拉加载更多
 * 3.左滑删除编辑功能
 * 4.开关控制如上功能实现
 */
class RefreshRecycleView(context:Context,attributes: AttributeSet) :RecyclerView(context,attributes){

    /**
     * 手机滑动速率
     */
    var mVelocityTracker:VelocityTracker?=null


    /**
     * 基本数据导入成功
     */
//    override fun onFinishInflate() {
//
//        this.addOnItemTouchListener(object :RecyclerView.OnItemTouchListener{
//
//
//            var maxWidth:Float=100f
//            var lastX:Float?=null
//            var lastY:Float?=null
//
//            var isMoving=false
//
//
//
//
//
//            override fun onTouchEvent(rv: RecyclerView?, e: MotionEvent?) {
//
//            }
//
//            override fun onInterceptTouchEvent(rv: RecyclerView?, ev: MotionEvent?): Boolean {
//
//                val x=ev!!.x
//                val y=ev.y
//                if (lastX==null){
//                    lastX=x
//                }
//                if (lastY==null){
//                    lastY=y
//                }
//
//                val view: View?=findChildViewUnder(x,y)
//
//                if (null==mVelocityTracker){
//                    mVelocityTracker= VelocityTracker.obtain()
//                }
//
//
//                mVelocityTracker!!.addMovement(ev)
//
//                when(ev!!.action){
//
//                    MotionEvent.ACTION_UP ->{
//                        mVelocityTracker!!.computeCurrentVelocity(1000)
//                        val scrollX = scrollX
//                        if (Math.abs(mVelocityTracker!!.xVelocity) > 100f) {
//                            isMoving = mVelocityTracker!!.xVelocity <=-100f
//                        }
//
//                        if (view!=null) {
//                            val a = lastX!! - x
//                            val b = lastY!! - y
//
//                            Log.e("action_down",(a + view.scrollX).toString())
//
//                            if (a + view!!.scrollX >=50) {
//                                view.scrollTo(100, 0)
//                            } else  {
//                                view.scrollTo(0, 0)
//                            }
//                        }
//                        if (null!=mVelocityTracker){
//                            mVelocityTracker!!.clear()
//                            mVelocityTracker!!.recycle()
//                            mVelocityTracker=null
//                        }
//                    }
//
//
//                    MotionEvent.ACTION_DOWN ->{
//                        //按下发生
//
//                    }
//
//
//                    MotionEvent.ACTION_MOVE ->{
//                        mVelocityTracker!!.computeCurrentVelocity(1000)
//
//                        val a=lastX!!-x
//                        val b=lastY!!-y
//                        if (view!=null) {
//                            if (a + view!!.scrollX <= 0) {
//                                view.scrollTo(0, 0)
//                            } else if (a + view.scrollX >= 100) {
//                                view.scrollTo(100, 0)
//                            } else {
//                                view.scrollBy(a!!.toInt(), 0)
//
//                            }
//
//                        }
//
//
//                    }
//
//
//                }
//
//
//                lastX=x
//                lastY=y
//
//                return  false
//            }
//
//            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
//            }
//        })
//    }








}