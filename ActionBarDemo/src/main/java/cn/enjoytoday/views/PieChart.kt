package cn.enjoytoday.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import kotlin.coroutines.experimental.EmptyCoroutineContext.plus

/**
 * @date 17-9-4.
 * @className PieChart
 * @serial 1.0.0
 *
 *
 *自定义扇形图表
 */
class PieChart(context: Context,attributeset: AttributeSet?,defStyleAttr:Int):View(context,attributeset,defStyleAttr){

    init {
        requestFocus()
        isClickable=true
    }

    constructor(context: Context, attributeset: AttributeSet):this(context,attributeset,0)

    constructor(context: Context):this(context,null,0)


    var listBar:MutableList<PartModel> = mutableListOf()


    var width=0f
    var height=0f

    var max:Float=0f
    var currentBarIndex=0
    var padding=dip2px(context,8f)
    /**
     * 圆心x轴坐标
     */
    var center_x=0f
    /**
     * 圆心y轴坐标
     */
    var center_y=0f

    /**
     * 外部圆环半径
     */
    var outer_ring_radius=0f
    /**
     * 内部圆环半径
     */
    var inter_ring_radius=0f


    /**
     * 延伸除去的半径
     */
    var offerset_radius=dip2px(context,10f)


    var onSelectedListener:OnSelectedListener?=null


    var rectF:RectF= RectF()

    val REFRESH_UI=1001

    val postHandler=object : Handler() {
        override fun handleMessage(msg: Message?) {
            if (msg!!.what==1001){
                invalidate()
            }

        }
    }




    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(getMeasureWidth(widthMeasureSpec), getMeasureHeight(heightMeasureSpec))

    }


    override fun onFinishInflate() {
        super.onFinishInflate()



    }





    /**

     * @param widthMeasureSpec
     * *
     * @return
     * * 测量 width
     */
    private fun getMeasureWidth(widthMeasureSpec: Int): Int {
        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
        var width = 0
        when (widthMode) {
            View.MeasureSpec.AT_MOST -> width = this.width.toInt()
            View.MeasureSpec.EXACTLY -> {
                this.width = widthSize.toFloat()
                width = widthSize
            }
            View.MeasureSpec.UNSPECIFIED -> width = this.width.toInt()
        }
        return width
    }

    /**

     * @param heightMeasureSpec
     * *
     * @return
     * * 测量 height
     */
    private fun getMeasureHeight(heightMeasureSpec: Int): Int {
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)
        val height: Int
        if (heightMode == View.MeasureSpec.EXACTLY) {
            height = heightSize
            this.height = height.toFloat()
        } else {
            this.height = getHeight().toFloat()
            height = this.height.toInt()
        }
        return height
    }


    /**
     * 添加类别
     */
    fun  addModel(model: PartModel){
        listBar.add(model)
        max += model.value
        calcuateCooridnate(false,null,currentBarIndex)
    }


    /**
     * 添加一个列表
     */
    fun addList(list:MutableList<PartModel>){
        listBar.addAll(list)
        list.forEach { max+=it.value }
        calcuateCooridnate(false,null,currentBarIndex)

    }

    /**
     * 更新一个新的List
     */
    fun setList(list:MutableList<PartModel>){
        listBar=list
        max=0f
        listBar.forEach {
            max+=it.value
        }
        calcuateCooridnate(false,null,currentBarIndex)

    }


    override fun onDraw(canvas: Canvas) {

        if (center_x==0f) {
            center_x = width / 2f
            center_y = height / 2f
            outer_ring_radius = (Math.min(width, height) - 2f * padding)/2f
            inter_ring_radius = outer_ring_radius / 3f * 2f
        }

        val paint:Paint= Paint()
        paint.isAntiAlias=true
        paint.style=Paint.Style.STROKE
        paint.strokeWidth=outer_ring_radius/3f
        Log.e("onDraw","get width:$width,height:$height,outer_ring-radius:$outer_ring_radius,inner_ring_radius:$inter_ring_radius")

        listBar.forEachIndexed { index, partModel ->

            Log.e("onDraw","get onDraw index:$index,and startAngle:${partModel.startAngle},and sweep:${partModel.sweep},color:${partModel.color}," +
                    "and value:${partModel.value}")
            paint.color=partModel.color

            if (90f in (partModel.startAngle)..(partModel.startAngle+partModel.startAngle)){
                /**
                 * 被选中
                 */
                val offerset_angle=partModel.startAngle+partModel.sweep/2f
                val offset_center_x=center_x+offerset_radius*Math.cos(Math.toRadians(offerset_angle.toDouble())).toFloat()
                val offset_center_y=center_y+offerset_radius*Math.sin(Math.toRadians(offerset_angle.toDouble())).toFloat()
                rectF.set(offset_center_x-inter_ring_radius,offset_center_y-inter_ring_radius,
                        offset_center_x+inter_ring_radius,offset_center_y+inter_ring_radius)
//                canvas.drawArc(rectF, partModel.startAngle,partModel.sweep,false,paint)
                currentBarIndex=index
                onSelectedListener?.onSelectedListener(index,partModel)

            }else{
                /**
                 * 一般的未被选中的
                 */
                rectF.set(center_x-inter_ring_radius,center_y-inter_ring_radius,center_x+inter_ring_radius,center_y+inter_ring_radius)
            }

            canvas.drawArc(rectF, partModel.startAngle,partModel.sweep,false,paint)
        }






    }


    /**
     * 设置当前选择
     */
    fun setCurrentBarIndex(currentIndex: Int?){
        currentIndex?:return
        if (this.currentBarIndex!=currentIndex){
            this.currentBarIndex= currentIndex
            calcuateCooridnate(false,null,currentIndex)
        }
    }


    /**
     * 根据x,y轴坐标求相对坐标,范围(0,360)
     */
    fun getAngelByCooridnate(cooridnate_x:Float,cooridnate_y: Float):Float{


        val offerset_y=center_y-cooridnate_y
        val distance=Math.sqrt(((cooridnate_x-center_x)*(cooridnate_x-center_x)+(cooridnate_y-center_y)*(cooridnate_y-center_y)).toDouble())
        var angle=Math.asin(offerset_y/distance)/2f/Math.PI*360
        if (cooridnate_x<center_x){
            angle=180-angle
        }
        return (360+angle.toFloat())%360f
    }





    /**
     * 计算角度坐标
     */
    fun calcuateCooridnate(isMove:Boolean,moved:Float?,currentIndex:Int?){

        if (isMove) {
            /**
             * 滑动时移动处理,手指未离开屏幕
             */

            listBar.forEachIndexed { index, partModel ->
                val angle=(partModel.startAngle+moved!!)%360f
                partModel.startAngle=angle
            }

            invalidate()

        }else{

            /**
             * 开始或者滑动结束时
             */



            val newlist=listBar.subList(currentBarIndex,listBar.size)
            if (currentIndex!!>0) {
                newlist.addAll(listBar.subList(0, currentBarIndex + 1))
            }
            var totalAngle=0f

            newlist.forEachIndexed { index, partModel ->
                var sweep=partModel.value/max*360f
                if (totalAngle+sweep>360){
                    sweep=360-totalAngle
                }
                partModel.sweep=sweep
                if (index==0){
                    /**
                     * 刚开始
                     */
                    partModel.startAngle=((90f-sweep/2f)+360)%360f
                }else{
                    /**
                     *
                     */
                    partModel.startAngle=((newlist[0].startAngle+totalAngle)+360)%360f
                }
                totalAngle+=partModel.sweep

            }

//            listBar=newlist.subList(listBar.size-currentBarIndex,listBar.size)
//            listBar.addAll(newlist.subList(0,listBar.size-currentBarIndex))


            postHandler.sendEmptyMessage(REFRESH_UI)

        }


    }











    var pre_x:Float?=null
    var pre_y:Float?=null


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.e("onDraw","onTouchEvent ,and action is:${event!!.action}")
        val x=event!!.x
        val y=event.y

        if (pre_x==null){
            pre_x=x
        }
        if (pre_y==null){
            pre_y=x
        }
        when(event.action){

            MotionEvent.ACTION_MOVE  ->{
                if (pre_x!=null && pre_y!=null && pre_x!=x && pre_y!=y){
                   val sweep= getAngelByCooridnate(x,y)-getAngelByCooridnate(pre_x!!,pre_y!!)
                    calcuateCooridnate(true,sweep,currentBarIndex)
                    pre_x=x
                    pre_y=y
                }
            }
            MotionEvent.ACTION_UP ->{
                if (pre_x!=null && pre_y!=null && pre_x!=x && pre_y!=y){
                    val sweep= getAngelByCooridnate(x,y)-getAngelByCooridnate(pre_x!!,pre_y!!)
                    calcuateCooridnate(true,sweep,currentBarIndex)
                    calcuateCooridnate(false,null,currentBarIndex)
                    pre_x=null
                    pre_y=null
                }


            }


            MotionEvent.ACTION_DOWN ->{


            }

        }


        return super.onTouchEvent(event)
    }






}


class PartModel(var color:Int,var value:Float){

    var startAngle:Float=0f
    var sweep:Float=0f


}

/**
 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
 */
fun View.dip2px(context: Context, dpValue: Float): Float {
    val scale = context.resources.displayMetrics.density
    return dpValue * scale + 0.5f
}

/**
 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
 */
fun View.px2dip(context: Context, pxValue: Float): Float {
    val scale = context.resources.displayMetrics.density
    return pxValue / scale + 0.5f
}


interface OnSelectedListener{

    /**
     * 被选中的model内容
     */
    fun onSelectedListener(index:Int,partModel: PartModel)
}