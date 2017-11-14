package cn.enjoytoday.rimedoj.fragments

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import cn.enjoytoday.rimedoj.R
import cn.enjoytoday.rimedoj.RimedojApplication
import cn.enjoytoday.rimedoj.source.DataSource
import cn.enjoytoday.rimedoj.source.DataTabSource
import devlight.io.library.ntb.NavigationTabBar
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.item_list.view.*
import kotlinx.android.synthetic.main.item_vp_list.*
import kotlinx.android.synthetic.main.item_vp_list.view.*
import java.util.ArrayList

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): MainFragment {
            val fragment = MainFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }

    /**
     * 需要添加一个数据源更新通知的广播
     */
    private val updateReceiver=object :BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent!!.action==DataSource.UPDATE_DATA_SOURCE){
                //update data resource





            }

        }

    }


    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    private var mListener: OnFragmentInteractionListener? = null

    //tab适配器
    private var tabAdapter:PagerAdapter?=null
    private var models:MutableList<NavigationTabBar.Model> = mutableListOf()








    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
            mParam2 = arguments.getString(ARG_PARAM2)
        }
        if (activity!=null){
            activity.registerReceiver(updateReceiver, IntentFilter(DataSource.UPDATE_DATA_SOURCE))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initUI()

    }


    private  fun initUI(){

        loadDataSource()



        vp_horizontal_ntb.adapter=tabAdapter

        val colors = resources.getStringArray(R.array.default_preview)

//        val navigationTabBar = findViewById(R.id.ntb_horizontal) as NavigationTabBar

        ntb_horizontal.models = models
        ntb_horizontal.setViewPager(vp_horizontal_ntb, 2)

        ntb_horizontal.post {
            (vp_horizontal_ntb.layoutParams as ViewGroup.MarginLayoutParams).topMargin = (-ntb_horizontal.badgeMargin).toInt()
            vp_horizontal_ntb.requestLayout()
        }

        ntb_horizontal.onTabBarSelectedIndexListener = object : NavigationTabBar.OnTabBarSelectedIndexListener {
            override fun onStartTabSelected(model: NavigationTabBar.Model, index: Int) {

            }
            override fun onEndTabSelected(model: NavigationTabBar.Model, index: Int) {
                model.hideBadge()
            }
        }






    }


    /**
     * 加载数据
     */
    private fun loadDataSource(){
        tabAdapter=TabAdapter(activity)      //tab adapter
        val tabSources=(activity.application as RimedojApplication).dataTabSources
        models.clear()
        for (dataTabSource in tabSources){
            models.add(NavigationTabBar.Model.Builder(dataTabSource.icon!!,dataTabSource.tabSelectedColor!!)
                    .title(dataTabSource.title)
                    .build())

        }
    }











    override fun onDestroy() {
        /**
         * 解除绑定
         */
        if (activity!=null){
            activity.unregisterReceiver(updateReceiver)
        }
        super.onDestroy()
    }



    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri,this)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }


    /**
     * item_adapter type
     */
    class  RecycleAdapter(private val tabSource: DataTabSource,private val context: Context):RecyclerView.Adapter<RecyclerView.ViewHolder>(){


        override fun getItemCount(): Int {
            return tabSource.childItemList.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
            //bind view data.
//            tabSource.viewType!!.bindData(position,holder!!)
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
            /**
             * return layout
             *
             */
            return tabSource.viewType!!.inflateLayout(context,parent!!)
        }

    }


    /**
     * tab适配器
     */
    class TabAdapter(val context: Activity):PagerAdapter(){
        val tabSources=(context.application as RimedojApplication).dataTabSources
        override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
            return view == `object`
        }

        override fun getCount(): Int {
            return tabSources.size
        }
        override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
            (container as ViewPager).removeView(`object` as View)
        }


        override fun instantiateItem(container: ViewGroup?, position: Int): Any {
            /**
             * 添加布局,属于list布局,不做改变,需要添加加载和加载失败处理操作
             */
            val view = LayoutInflater.from(context).inflate(R.layout.item_vp_list, null, false)
            val recyclerView = view.recyclerView
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(
                    context, LinearLayoutManager.VERTICAL, false
            )
            val tabSource=tabSources[position]
            recyclerView.adapter = RecycleAdapter(tabSource,context)
            container!!.addView(view)
            return view
        }
    }




}// Required empty public constructor
