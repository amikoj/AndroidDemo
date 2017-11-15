package cn.enjoytoday.rimedoj.source

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import devlight.io.library.ntb.NavigationTabBar

/**
 * @date 17-11-10.
 * @className DataViewType
 * @serial 1.0.0
 * 展示的item的布局样式
 */
abstract class DataViewType{


    var tabSource: DataTabSource?=null
    /**
     * 等待实现的导入
     */
    abstract fun inflate(context: Context):View

    /**
     * 单击事件
     */
    abstract  fun onItemClickListener(view: View,position: Int)


    abstract fun onItemLongClickListener(view: View,position: Int):Boolean


    /**
     * 赋值
     */
    abstract fun assignView(position: Int,holder:DataViewHolder)


    //绑定数据数据接口
    fun bindData(position: Int,holder:RecyclerView.ViewHolder){
        if (holder is DataViewHolder){
            assignView(position,holder)
        }else{
            TODO("ViewHolder Type is incorrect,please check your viewHolder at first.")
        }
    }


    /**
     * 导入布局
     */
    fun inflateLayout(context: Context):RecyclerView.ViewHolder{
        val view=inflate(context)
        return DataViewHolder(view,this)
    }


    /**
     * viewholder
     */
    class DataViewHolder(view: View, private val dataViewType: DataViewType?=null):RecyclerView.ViewHolder(view),View.OnClickListener,View.OnLongClickListener{
        init {
            this.itemView.setOnClickListener(this)
        }


        override fun onClick(v: View?) {
            dataViewType?.onItemClickListener(v!!,layoutPosition)
        }

        override fun onLongClick(v: View?): Boolean{
            return dataViewType?.onItemLongClickListener(v!!,layoutPosition)?:false

        }

    }



}