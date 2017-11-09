package cn.enjoytoday.pluginsapp

import android.Manifest
import android.app.Activity
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment.getExternalStoragePublicDirectory
import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.TextView
import cn.enjoytoday.pluginsapp.R.id.parent
import devlight.io.library.ntb.NavigationTabBar

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.item_list.view.*
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.ArrayList


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    companion object {

        //权限集合
        val PERMISSION_MAP= arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE /** 文件写权限**/,
                Manifest.permission.READ_EXTERNAL_STORAGE /**文件读权限**/)
        private val mHandlerThread= HandlerThread("plugin_thread")
        private val BASE_PLUGIN_URL="http://192.168.3.166/Apks"
        private val ONLINE_PLUGIN_URL="$BASE_PLUGIN_URL/config.prop"


        private val GRANT_EXTERNAL_STORAGE=10000     //获取内存读写权限
        private val DENIED_EXTERNAL_STORAGE=10001    //拒绝内存读取权限

        private val PERMISSION_REQUEST_CODE=1200
        private val PLUGIN_LOAD_OVER=12001

        /**
         * 静态代码块
         */
        init {
            mHandlerThread.start()
        }



    }


    private var IS_GRANT_EXTERNAL_PERMISSION=false

    private val pluginUrlList= mutableListOf<String>()


    private  val mHandler=object :Handler(mHandlerThread.looper){

        override fun handleMessage(msg: Message?) {


            when(msg!!.what){
                GRANT_EXTERNAL_STORAGE ->{
                    /**
                     * 获取读写权限
                     */
                    log(msg = "granted_write_external_storage_permission")
                    IS_GRANT_EXTERNAL_PERMISSION=true
//                    loadPluginApk()

                }

                DENIED_EXTERNAL_STORAGE ->{
                    /**
                     * 拒绝文件读取权限的获取
                     */
                    log(msg = "write_external_storage_permission_denied")
                    toast(msg = "没有加载权限")

                }


                PLUGIN_LOAD_OVER ->{
                    runOnUiThread{

                    }



                }

            }

        }

    }


    private var downloadId:Long?=null

    private var pluginPath:String?=null
    /**
     * 插件下载监听
     */
    private val dowloadReceiver=object :BroadcastReceiver(){

        override fun onReceive(context: Context?, intent: Intent?) {
            when(intent!!.action){
                DownloadManager.ACTION_DOWNLOAD_COMPLETE ->{
                    /**
                     * 下载完成
                     */
                    val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1L)
                    log(msg = "下载id为:$id")


                    if (id==downloadId){
                        log(msg = "插件下载完成")
                        pluginPath=getExternalStoragePublicDirectory("/plugins/").absolutePath //插件位置

                        if (IS_GRANT_EXTERNAL_PERMISSION){
                            Thread{




                            }.start()

                        }
                    }
                }
            }

        }

    }







    /**
     * 权限请求处理
     */
   private fun  requestPermissions(){
        if (ContextCompat.checkSelfPermission(this@MainActivity,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            /**
             * 没有权限
             */
            ActivityCompat.requestPermissions(this@MainActivity, PERMISSION_MAP, PERMISSION_REQUEST_CODE)
        }else{
            mHandler.sendEmptyMessage(GRANT_EXTERNAL_STORAGE)
        }

    }



    /**
     * 权限申请结果回调
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        when(requestCode){
            PERMISSION_REQUEST_CODE ->{
                if (grantResults.isNotEmpty() &&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    mHandler.sendEmptyMessage(GRANT_EXTERNAL_STORAGE)
                }else{
                    mHandler.sendEmptyMessage(DENIED_EXTERNAL_STORAGE)
                }

            }


        }
    }


    private var itemAdapter:FragmentPagerAdapter?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(this,
                drawer_layout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)

        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)

        registerReceiver(dowloadReceiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))




        initUI()

    }








    private  fun initUI(){
        vp_horizontal_ntb.adapter=object :PagerAdapter(){
            override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
                return view == `object`

            }

            override fun getCount(): Int {
                return 5;
            }

            override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
                (container as ViewPager).removeView(`object` as View)
            }


            override fun instantiateItem(container: ViewGroup?, position: Int): Any {
                val view = LayoutInflater.from(baseContext).inflate(R.layout.item_vp_list, null, false)

                val recyclerView = view.findViewById(R.id.rv) as RecyclerView
                recyclerView.setHasFixedSize(true)
                recyclerView.layoutManager = LinearLayoutManager(
                        baseContext, LinearLayoutManager.VERTICAL, false
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
            var txt: TextView=view.txt_vp_item_list

        }

    }





    private fun loadTullppa(){
        val httpUrlConnection:HttpURLConnection=URL(ONLINE_PLUGIN_URL).openConnection() as HttpURLConnection
        httpUrlConnection.requestMethod="GET"
        httpUrlConnection.doOutput=false
        httpUrlConnection.doInput=true
        httpUrlConnection.connect()
        val readIn=BufferedReader(InputStreamReader(httpUrlConnection.inputStream,"UTF-8"))

        var lines=readIn.readLines()
        lines.forEach{
            pluginUrlList.add(BASE_PLUGIN_URL+ File.separator+it)
        }

        mHandler.sendEmptyMessage(PLUGIN_LOAD_OVER)
    }




    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {

            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)

        return true
    }








    override fun onDestroy() {
        super.onDestroy()

        mHandler.removeCallbacksAndMessages(null)      //清空所有的消息队列
    }




}
