package cn.enjoytoday.rimedoj

import android.net.Uri
import android.os.Bundle
import android.support.annotation.IntDef
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import cn.enjoytoday.rimedoj.fragments.*
import cn.enjoytoday.rimedoj.helper.BottomNavigationViewHelper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.fragment_main.*

class MainActivity : AppCompatActivity(), OnFragmentInteractionListener {



    companion object {

        @IntDef(SHOW, UNSHORN)
        @Retention(AnnotationRetention.SOURCE)
        annotation class Visibility

        const val SHOW = 0L
        const val UNSHORN = 1L

    }



    /**
     * Activities that contain this fragment must implement the
     * [OnFragmentInteractionListener] interface
     */
    override fun onFragmentInteraction(uri: Uri, fragment: Fragment) {
        log(msg = "get message from fragment.")
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        log(msg = "mOnNavigationItemSelectedListener and item info:${item.title}")
        when (item.itemId) {
            R.id.navigation_home -> {
                onFragmentIndexChanged(0)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                onFragmentIndexChanged(1)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                onFragmentIndexChanged(2)

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_settings ->{
                onFragmentIndexChanged(3)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }


    private fun onFragmentIndexChanged(index:Int){

        when(index){
            0 -> showToolBar(UNSHORN,0)
            1 -> showToolBar(SHOW,1,getString(R.string.title_dashboard),false)
            2 -> showToolBar(SHOW,2,getString(R.string.title_notifications),false)
            3 ->  showToolBar(SHOW,3,getString(R.string.title_settings),true)

        }
    }


    /**
     * 工具栏控制
     */
    private fun showToolBar(@Visibility visibility:Long= UNSHORN, position: Int=0, title:String=getString(R.string.app_name), isShowBack:Boolean=false){
        viewpager.currentItem = position
        if (visibility== SHOW){
            toolbar.visibility=View.VISIBLE
            toolbar.title=title
            supportActionBar!!.setDisplayHomeAsUpEnabled(isShowBack)
        }else{
            toolbar.visibility=View.GONE
        }
    }









    private val mOnPageChangeListener = object :ViewPager.OnPageChangeListener{
        override fun onPageScrollStateChanged(state: Int) {

        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

        }

        override fun onPageSelected(position: Int) {
            /**
             * 更新tab选项
             */
            if (menuItem != null) {
                menuItem!!.isChecked = false
            } else {
                navigation.menu.getItem(0).isChecked = false
            }
            menuItem = navigation.menu.getItem(position)
            menuItem!!.isChecked = true
            onFragmentIndexChanged(position)

        }


    }



    private  var menuItem:MenuItem?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        BottomNavigationViewHelper.disableShiftMode(navigation)
        viewpager.addOnPageChangeListener(mOnPageChangeListener)
        setupViewPager(viewpager)


        toolbar.visibility=View.GONE

    }


    /**
     * 加载viewpager的适配器
     */
    private fun setupViewPager(viewPager: ViewPager){
        val adapter=ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(MainFragment.newInstance("0","main_fragment"))
        adapter.addFragment(DashboardFragment.newInstance("1","dashboard_fragment"))
        adapter.addFragment(NotificationFragment.newInstance("2","notification_fragment"))
        adapter.addFragment(SettingsFragment.newInstance("3","person_fragment"))
        viewPager.adapter=adapter


    }


    /**
     * viewpagerAdapter
     */
    inner  class ViewPagerAdapter(fragmentManager: FragmentManager):FragmentPagerAdapter(fragmentManager){


        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        private val mFragmentList= mutableListOf<Fragment>()


        override fun getCount(): Int {
            return mFragmentList.size
        }

        /**
         * 添加fragment
         */
        fun addFragment(fragment:Fragment){
            if (!mFragmentList.contains(fragment)){
                mFragmentList.add(fragment)
            }

        }


        /**
         * 去除fragment
         */
        fun removeFragment(fragment: Fragment){
            if (mFragmentList.contains(fragment)){
                mFragmentList.remove(fragment)
            }

        }

    }
}
