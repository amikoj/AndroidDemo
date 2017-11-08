package cn.enjoytoday

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import cn.enjoytoday.R
import cn.enjoytoday.R.styleable.NavigationTabStrip
import cn.enjoytoday.views.PartModel
import com.gigamole.navigationtabstrip.NavigationTabStrip
import kotlinx.android.synthetic.main.activity_magic_tab.*
import kotlinx.android.synthetic.main.item_layout.view.*

class MagicTabActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_magic_tab)

        graphic_view_pager.adapter=object : PagerAdapter() {
            override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
                return view == `object`
            }

            override fun getCount(): Int {
                return 3

            }


            override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
                container?.removeView(`object` as View?)

            }


            override fun instantiateItem(container: ViewGroup?, position: Int): Any {
                val view: View = layoutInflater.inflate(R.layout.item_layout, null)
                view.text.text = "this is pager $position"

                view.pie_chart.setList(mutableListOf(PartModel(120f),PartModel(140f),PartModel(120f)))


                container?.addView(view)
                return view
            }

        }

        nts.setViewPager(graphic_view_pager,0)

        nts.setOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
//                Log.e("onPageScrollStateChanged","page onPageScrollStateChanged and state is :$state")
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
//                Log.e("onPageScrolled","page onPageScrolled and position is :$position")

            }

            override fun onPageSelected(position: Int) {
                Log.e("pageChanged","page changed and position is :$position")
                Toast.makeText(this@MagicTabActivity,"position is:$position",Toast.LENGTH_LONG).show()

            }

        })



    }
}
