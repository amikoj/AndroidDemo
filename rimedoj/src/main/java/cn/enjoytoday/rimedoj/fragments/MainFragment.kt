package cn.enjoytoday.rimedoj.fragments

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
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
import devlight.io.library.ntb.NavigationTabBar
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.item_list.view.*
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

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
            mParam2 = arguments.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initUI()
    }


    private  fun initUI(){
        vp_horizontal_ntb.adapter=object : PagerAdapter(){
            override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
                return view == `object`
            }

            override fun getCount(): Int {
                return 5
            }
            override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
                (container as ViewPager).removeView(`object` as View)
            }


            override fun instantiateItem(container: ViewGroup?, position: Int): Any {
                val view = LayoutInflater.from(context).inflate(R.layout.item_vp_list, null, false)

                val recyclerView = view.findViewById(R.id.rv) as RecyclerView
                recyclerView.setHasFixedSize(true)
                recyclerView.layoutManager = LinearLayoutManager(
                        context, LinearLayoutManager.VERTICAL, false
                )
                recyclerView.adapter = RecycleAdapter()

                container!!.addView(view)
                return view
            }
        }


        val colors = resources.getStringArray(R.array.default_preview)


//        val navigationTabBar = findViewById(R.id.ntb_horizontal) as NavigationTabBar
        val models = ArrayList<NavigationTabBar.Model>()
        models.add(NavigationTabBar.Model.Builder(resources.getDrawable(R.drawable.ic_first), Color.parseColor(colors[0])).title("Heart").build())
        models.add(NavigationTabBar.Model.Builder(resources.getDrawable(R.drawable.ic_second), Color.parseColor(colors[1])).title("Cup").build())
        models.add(NavigationTabBar.Model.Builder(resources.getDrawable(R.drawable.ic_third), Color.parseColor(colors[2])).title("Diploma").build())
        models.add(NavigationTabBar.Model.Builder(resources.getDrawable(R.drawable.ic_fourth), Color.parseColor(colors[3])).title("Flag").build())
        models.add(NavigationTabBar.Model.Builder(resources.getDrawable(R.drawable.ic_fifth), Color.parseColor(colors[4])).title("Medal").build())
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


//    override fun onResume() {
//        super.onResume()
//
//    }



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
     * RecycleView about test.
     */

    class  RecycleAdapter:RecyclerView.Adapter<RecycleAdapter.ViewHolder>(){

        override fun getItemCount(): Int {

            return 20
        }

        override fun onBindViewHolder(holder:ViewHolder?, position: Int) {
            holder!!.txt.text = String.format("Navigation Item #%d", position);
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent!!.context).inflate(R.layout.item_list, parent, false)
            return ViewHolder(view!!)
        }

        inner class ViewHolder(view:View) : RecyclerView.ViewHolder(view) {
            var txt: TextView =view.txt_vp_item_list

        }

    }


}// Required empty public constructor
