package cn.enjoytoday

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.*
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.ProgressBar

import android.widget.TextView
import android.widget.Toast
import cn.enjoytoday.views.SuperSwipeRefreshLayout
import com.yanzhenjie.recyclerview.swipe.*
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration
import kotlinx.android.synthetic.main.item.view.*
import kotlinx.android.synthetic.main.item_layout.view.*
import kotlinx.android.synthetic.main.layout_footer.*
import kotlinx.android.synthetic.main.layout_footer.view.*
import kotlinx.android.synthetic.main.layout_head.*
import kotlinx.android.synthetic.main.layout_head.view.*
import kotlinx.android.synthetic.main.recyleview_layout.*
import java.util.ArrayList

class RecyclerTestActivity : Activity() {
    internal var mLayoutManager: RecyclerView.LayoutManager? = null
    internal var mAdapter: RecyclerView.Adapter<*>? = null

    internal var arrays= arrayListOf<String>()
    var adapter=MyAdapter(arrays)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyleview_layout)


        refresh_layout.setHeaderViewBackgroundColor(Color.parseColor("#ffaaaaaa"))
        refresh_layout.setHeaderView(createHeaderView())
        refresh_layout.setFooterView(createFooterView())



        refresh_layout.isTargetScrollWithLayout=true

        refresh_layout.setOnPullRefreshListener(object :SuperSwipeRefreshLayout.OnPullRefreshListener{
            override fun onRefresh() {
                text_view!!.text = "正在刷新"
                image_view!!.visibility = View.GONE
                pb_view!!.visibility = View.VISIBLE
                Handler().postDelayed({
                    refresh_layout.isRefreshing = false
                    pb_view!!.visibility = View.GONE
                }, 1000)
            }

            override fun onPullDistance(distance: Int) {

            }

            override fun onPullEnable(enable: Boolean) {
                text_view!!.text = if (enable) "松开刷新" else "下拉刷新"
                image_view!!.visibility = View.VISIBLE
                image_view!!.rotation = (if (enable) 180 else 0).toFloat()
            }
        })




        refresh_layout.setOnPushLoadMoreListener(object: SuperSwipeRefreshLayout.OnPushLoadMoreListener{

            override fun onLoadMore() {
                footer_text_view!!.text = "正在加载..."
                footer_image_view!!.visibility = View.GONE
                footer_pb_view!!.visibility = View.VISIBLE

                Handler().postDelayed({

                    footer_image_view!!.visibility= View.VISIBLE
                    footer_pb_view!!.visibility= View.GONE
                    refresh_layout.setLoadMore(false)
                },1000)
            }


            override fun onPushDistance(distance: Int) {

            }


            override fun onPushEnable(enable: Boolean) {
                footer_text_view!!.text=if(enable)"松开加载" else "上拉加载"
                footer_image_view!!.visibility = View.VISIBLE
                footer_image_view!!.rotation=(if (enable) 0 else 180).toFloat()
            }



        })



        if (mLayoutManager == null)
            mLayoutManager = LinearLayoutManager(this)
        recyler_view.layoutManager=mLayoutManager
        recyler_view.addItemDecoration(DefaultItemDecoration(ContextCompat.getColor(this, R.color.divider_color)))


        recyler_view.setSwipeMenuCreator(swipeMenuCreator)
        recyler_view.setSwipeMenuItemClickListener(mMenuItemClickListener)
        recyler_view.setSwipeItemClickListener { itemView, position ->
            Toast.makeText(this, "第" + position + "个", Toast.LENGTH_SHORT).show()
        }

//        recyler_view.useDefaultLoadMore()


        recyler_view.setLoadMoreListener(mLoadMoreListener)
        recyler_view.adapter=adapter

        loadData()

    }


    var footer_pb_view:ProgressBar?=null
    var footer_image_view:ImageView?=null
    var footer_text_view:TextView?=null

    private fun createFooterView(): View {
        val footerView = LayoutInflater.from(this)
                .inflate(R.layout.layout_footer, null)

        footer_pb_view=footerView.footer_pb_view
        footer_image_view=footerView.footer_image_view
        footer_text_view=footerView.footer_text_view


        footer_pb_view!!.visibility = View.GONE
        footer_image_view!!.visibility = View.VISIBLE
        footer_image_view!!.setImageResource(R.drawable.down_arrow)
        footer_text_view!!.text = "上拉加载更多..."
        return footerView
    }


    var pb_view:ProgressBar?=null
    var image_view:ImageView?=null
    var text_view:TextView?=null

    private fun createHeaderView(): View {
        val headerView = LayoutInflater.from(this)
                .inflate(R.layout.layout_head, null)
        pb_view=headerView.pb_view
        image_view=headerView.image_view
        text_view=headerView.text_view


        text_view!!.text = "下拉刷新"
        image_view!!.visibility = View.VISIBLE
        image_view!!.setImageResource(R.drawable.down_arrow)
        pb_view!!.visibility = View.GONE
        return headerView
    }














    fun getItemList(start: Int): ArrayList<String> {
        var strings = arrayListOf<String>()
        (start..start + 5 - 1).mapTo(strings) { "第" + it + "个Item" }
        return strings
    }

    /**
     * 加载更多。
     */
    private val mLoadMoreListener = SwipeMenuRecyclerView.LoadMoreListener {
        recyler_view.postDelayed( {
            val strings = getItemList(adapter.itemCount)
//            arrays.removeAll(arrays)
             arrays.addAll(strings)
//            mItemList.addAll(strings)

//            adapter.notifyDataSetChanged()
             adapter.notifyItemRangeInserted(arrays.size - strings.size, strings.size)


            // 数据完更多数据，一定要掉用这个方法。
            // 第一个参数：表示此次数据是否为空。
            // 第二个参数：表示是否还有更多数据。
            recyler_view.loadMoreFinish(false, true)

            // 如果加载失败调用下面的方法，传入errorCode和errorMessage。
            // errorCode随便传，你自定义LoadMoreView时可以根据errorCode判断错误类型。
            // errorMessage是会显示到loadMoreView上的，用户可以看到。
            // mRecyclerView.loadMoreError(0, "请求网络失败");
        }, 1000)
    }


    /**
     * 菜单创建器，在Item要创建菜单的时候调用。
     */
    private val swipeMenuCreator = SwipeMenuCreator { swipeLeftMenu, swipeRightMenu, viewType ->
        val width = resources.getDimensionPixelSize(R.dimen.dp_70)

        // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
        // 2. 指定具体的高，比如80;
        // 3. WRAP_CONTENT，自身高度，不推荐;
        val height = ViewGroup.LayoutParams.MATCH_PARENT



        val deleteItem = SwipeMenuItem(this@RecyclerTestActivity)
                .setBackground(R.drawable.selector_red)
                .setText("删除")
                .setTextSize(20)
                .setTextColor(Color.WHITE)
                .setWidth(width)
                .setHeight(height)
        swipeRightMenu.addMenuItem(deleteItem)// 添加菜单到右侧。

    }


    /**
     * RecyclerView的Item的Menu点击监听。
     */
    private val mMenuItemClickListener = SwipeMenuItemClickListener { menuBridge ->
        menuBridge.closeMenu()

        val direction = menuBridge.direction // 左侧还是右侧菜单。
        val adapterPosition = menuBridge.adapterPosition // RecyclerView的Item的position。
        val menuPosition = menuBridge.position // 菜单在RecyclerView的Item中的Position。

        if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
            Toast.makeText(this@RecyclerTestActivity, "list第$adapterPosition; 右侧菜单第$menuPosition", Toast.LENGTH_SHORT).show()
        } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
            Toast.makeText(this@RecyclerTestActivity, "list第$adapterPosition; 左侧菜单第$menuPosition", Toast.LENGTH_SHORT).show()
        }
    }



    /**
     * 第一次加载数据。
     */
    private fun loadData() {
         arrays.clear()
         arrays.addAll(getItemList(0))
         adapter.notifyDataSetChanged()

        refresh_layout.isRefreshing = false

        // 第一次加载数据：一定要调用这个方法，否则不会触发加载更多。
        // 第一个参数：表示此次数据是否为空，假如你请求到的list为空(== null || list.size == 0)，那么这里就要true。
        // 第二个参数：表示是否还有更多数据，根据服务器返回给你的page等信息判断是否还有更多，这样可以提供性能，如果不能判断则传true。
        recyler_view.loadMoreFinish(false, true)
    }




    inner class MyAdapter(arrays:ArrayList<String>): Adapter<RecyclerView.ViewHolder>(){

        val arrays:ArrayList<String> =arrays
        override fun getItemCount(): Int {
          return  arrays.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
            if (holder is MyViewHolder){
                holder.textView.text=arrays[position]
                holder.view.setOnClickListener {

                    Log.e("action_down","setOnClickListener")
                }


            }

        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
           return MyViewHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.item_layout,parent,false))
        }


    }

    inner class MyViewHolder(view: View):RecyclerView.ViewHolder(view){

        var view=view
        var textView:TextView=view.text

    }



}
