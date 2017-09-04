package cn.enjoytoday

import android.app.Activity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.PagerAdapter
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.TextView
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration
import kotlinx.android.synthetic.main.activity_recycler_grid_view.*
import kotlinx.android.synthetic.main.item.*
import kotlinx.android.synthetic.main.item.view.*
import kotlinx.android.synthetic.main.item_layout.view.*
import kotlinx.android.synthetic.main.recyleview_layout.*
import java.util.ArrayList


class RecyclerGridViewActivity : Activity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_grid_view)


         var mLayoutManager: LinearLayoutManager =LinearLayoutManager(this)
        mLayoutManager.orientation=LinearLayoutManager.HORIZONTAL

         var arrays= arrayListOf<String>("cai","hai","fei","123","456","789","hfcai","caihaifei","fhc","chf")
        var adapter=MyAdapter(arrays)

//        recycler_view.layoutManager=mLayoutManager
//
//        recycler_view.adapter=adapter



        recycler_view.adapter=MyViewPagerAdapter(arrays)


        recycler_view.currentItem=0








    }












    inner class MyViewPagerAdapter(val mListViews: ArrayList<String>): PagerAdapter() {


        override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
            return view==`object`  //官方提示这样写
        }

        override fun getCount(): Int {
            return  Math.ceil(mListViews.size/6.toDouble()).toInt()//返回页卡的数量
        }


        override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
            container!!.removeViewAt(position)
        }


        override fun instantiateItem(container: ViewGroup?, position: Int): Any {
            val view=LayoutInflater.from(this@RecyclerGridViewActivity).inflate(R.layout.item,null)
            view.item_recycler_view.layoutManager=GridLayoutManager(this@RecyclerGridViewActivity,3)
            val startIndex=position*6
            val endIndex=if ((position+1)*6>mListViews.size) mListViews.size else (position+1)*6
            if (endIndex<=startIndex){
                return super.instantiateItem(container,position)
            }
            view.item_recycler_view.addItemDecoration(DefaultItemDecoration(ContextCompat.getColor(this@RecyclerGridViewActivity, R.color.divider_color)))
            var itemArrays= mListViews.subList(startIndex,endIndex)
            var adapter=ItemAdapter(itemArrays)

            view.item_recycler_view.adapter=adapter

            container!!.addView(view,0)
            return view
        }

    }






    inner class MyAdapter(arrays: ArrayList<String>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

        val arrays: ArrayList<String> =arrays
        override fun getItemCount(): Int {
            return  Math.ceil(arrays.size/6.toDouble()).toInt()
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
            if (holder is MyViewHolder){
                holder.textView.layoutManager=GridLayoutManager(holder.view.context,3)
                val startIndex=position*6
                val endIndex=if ((position+1)*6>arrays.size) arrays.size else (position+1)*6
                if (endIndex<=startIndex){
                    return
                }




                holder.textView.addItemDecoration(DefaultItemDecoration(ContextCompat.getColor(holder.view.context, R.color.divider_color)))

                var itemArrays= arrays.subList(startIndex,endIndex)
                var adapter=ItemAdapter(itemArrays)
                holder.textView.adapter=adapter

            }

        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
            return MyViewHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.item,parent,false))
        }


    }

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view){

        var view=view
        var textView: RecyclerView=view.item_recycler_view

    }







    inner class ItemAdapter(arrays:MutableList<String>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

        val arrays:MutableList<String> =arrays
        override fun getItemCount(): Int {
            return  arrays.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
            if (holder is ItemViewHolder){
                holder.textView.text=arrays[position]
                holder.view.setOnClickListener {

                    Log.e("action_down","setOnClickListener")
                }


            }

        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
            return ItemViewHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.item_layout,parent,false))
        }


    }

    inner class ItemViewHolder(view: View):RecyclerView.ViewHolder(view){

        var view=view
        var textView:TextView=view.text

    }













}
