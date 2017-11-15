package cn.enjoytoday.rimedoj.source

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.Icon
import android.support.annotation.IntDef
import android.support.annotation.StringDef
import android.view.LayoutInflater
import android.view.View
import cn.enjoytoday.rimedoj.R
import cn.enjoytoday.rimedoj.RimedojApplication
import cn.enjoytoday.rimedoj.callbacks.DataHandlerCallback
import cn.enjoytoday.rimedoj.callbacks.DownloadCallback
import cn.enjoytoday.rimedoj.callbacks.RequestCallback
import cn.enjoytoday.rimedoj.log
import cn.enjoytoday.rimedoj.net.NetRequest
import kotlinx.android.synthetic.main.item_line_1.view.*
import kotlinx.android.synthetic.main.item_line_2.view.*
import okhttp3.*
import java.io.File
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

/**
 * @date 17-11-10.
 * @className DataTabSource
 * @serial 1.0.0
 *
 *
 * 设置数据源信息,包括基本的格式和数据设置方式,对于单类型数据进行设置
 */
class DataTabSource(){

    companion object {
        /**
         * 应该还需要一些其他的数据暂时还没想到
         */


        /**
         *
         * @param title 标题
         * @param url 访问路径
         * @param drawable icon
         *
         */
        fun getDataTabSource(title: String,url:String,drawable: Drawable,@type type:Long,tabSelectedColor: Int,callback:DataHandlerCallback):DataTabSource{
            val dataTabSource=DataTabSource()
            dataTabSource.title=title
            dataTabSource.baseUrl=url
            dataTabSource.icon=drawable
            dataTabSource.childItemList.clear()
            dataTabSource.dataHandlerCallback=callback
            dataTabSource.tabSelectedColor=tabSelectedColor
           val viewType= when(type){
                 TYPE_LINE_1 ->{
                     /**
                      * type line one
                      */
                     object :DataViewType() {
                         override fun inflate(context: Context): View {
                             return LayoutInflater.from(context).inflate(R.layout.item_line_1, null)
                         }

                         override fun onItemClickListener(view: View, position: Int) {
                             /**
                              * webview,网页处理
                              */

                             log(msg = "onItemClick,and position:$position")
                         }

                         override fun onItemLongClickListener(view: View, position: Int): Boolean {
                             log(msg = "onItemLongClickListener,and position:$position")
                             return false
                         }

                         override fun assignView(position: Int, holder: DataViewHolder) {
                             println( "assignView,position:$position")
                             /**
                              * 绑定数据
                              */
                             val itemTab=dataTabSource.childItemList[position]
                             println("itemTab :${itemTab.toString()}")
                             /**
                              * 绑定数据
                              */
                             holder.itemView.item_line_1_title.text=itemTab.paramsMap["item_line_1_title"]
                             holder.itemView.item_line_1_description.text=itemTab.paramsMap["item_line_1_description"]

                             NetRequest().download(itemTab.paramsMap["item_line_1_icon"]!!,dataTabSource.title,object :DownloadCallback{
                                 override fun onSuccess(bytes: ByteArray) {
                                     log("download image success.")
//                                     val imageFile=File(saveFile)
//                                     val bytes=imageFile.readBytes()
                                     val drawable=BitmapDrawable(BitmapFactory.decodeByteArray(bytes,0,bytes.size))
                                     holder.itemView.item_line_1_icon.setImageDrawable(drawable)

                                 }

                                 override fun onFailed(errorMsg: String) {
                                     log("load image success.")
                                 }

                                 override fun onProcess(process: Long) {

                                 }

                                 override fun onStart() {

                                 }

                             })

                         }
                     }




                }

                TYPE_LINE_2 ->{

                    object :DataViewType() {
                        override fun inflate(context: Context): View {
                            return LayoutInflater.from(context).inflate(R.layout.item_line_2, null)
                        }

                        override fun onItemClickListener(view: View, position: Int) {
                            log(msg = "onItemClick,and position:$position")
                        }

                        override fun onItemLongClickListener(view: View, position: Int): Boolean {
                            log(msg = "onItemLongClickListener,and position:$position")
                            return false
                        }

                        override fun assignView(position: Int, holder: DataViewHolder) {

                            val itemTab=dataTabSource.childItemList[position]
                            val params=itemTab.paramsMap

                            /**
                             * 绑定数据
                             */
                            holder.itemView.item_line_2_title.text=params["item_line_1_title"]


                        }
                    }





                }

                TYPE_CARD_1 ->{

                    object :DataViewType() {
                        override fun inflate(context: Context): View {
                            return LayoutInflater.from(context).inflate(R.layout.item_card_1, null)
                        }

                        override fun onItemClickListener(view: View, position: Int) {
                            log(msg = "onItemClick,and position:$position")
                        }

                        override fun onItemLongClickListener(view: View, position: Int): Boolean {
                            log(msg = "onItemLongClickListener,and position:$position")
                            return false
                        }

                        override fun assignView(position: Int, holder: DataViewHolder) {

                            /**
                             * 绑定数据
                             */

                        }
                    }





                }

                else ->{

                    object :DataViewType() {
                        override fun inflate(context: Context): View {
                            return LayoutInflater.from(context).inflate(R.layout.item_card_2, null)
                        }

                        override fun onItemClickListener(view: View, position: Int) {
                            log(msg = "onItemClick,and position:$position")
                        }

                        override fun onItemLongClickListener(view: View, position: Int): Boolean {
                            log(msg = "onItemLongClickListener,and position:$position")
                            return false
                        }

                        override fun assignView(position: Int, holder: DataViewHolder) {

                            /**
                             * 绑定数据
                             */

                        }
                    }



                }

           }

            dataTabSource.viewType=viewType
            dataTabSource.loadDatas()


            return dataTabSource


        }


        /**
         * 布局类型
         */
        const val TYPE_LINE_1=1000L
        const val TYPE_LINE_2=1001L
        const val TYPE_CARD_1=1002L
        const val TYPE_CARD_2=1003L


        @IntDef(TYPE_LINE_1, TYPE_LINE_2, TYPE_CARD_1, TYPE_CARD_2)
        @Retention(AnnotationRetention.SOURCE)
        annotation class type




    }

    /**
     * 内容资源标题
     */
    var title:String="Blank"

    /**
     * 内容资源icon
     */
    var icon:Drawable?=null


    /**
     * 展示的item布局样式
     */
    var viewType:DataViewType?=null

    /**
     * 数据源获取数据接口地址
     */
    var baseUrl:String?=null


    /**
     * 请求获取的原始数据
     */
    var text:String?=null


    /**
     * 数据规则创建,对数据格式的约束和过滤
     */
    var rule:DataRule?=null


    /**
     * 格式字段,非必要,获取需要扩展
     */
    var dataPattern:String?=null


    /**
     * 获取数据格式内容
     */
    var dataSourceType:DataSourceType=DataSourceType()



    /**
     * 收藏信息
     */
    var collectionsDataList:MutableList<ItemTab> = mutableListOf()


    /**
     * 所有获取的信息集合
     */
    var childItemList:MutableList<ItemTab> = mutableListOf()


    /**
     * 正常情况下的背景色
     */
    var tabNormalColor:Int?=null


    /**
     * 被选中后的背景色
     */
    var tabSelectedColor:Int?=null


    var dataHandlerCallback:DataHandlerCallback?=null

    /**
     * 构造器,构造基础的tab资源
     */
    constructor(title:String,viewType: DataViewType,baseUrl:String,icon: Drawable,tabSelectedColor:Int):this(){
        this.title=title
        this.viewType=viewType
        this.baseUrl=baseUrl
        this.icon=icon
        this.tabSelectedColor=tabSelectedColor
        this.viewType!!.tabSource=this
//        this.loadDatas()
    }






    /**
     * 加载在线数据
     */
    private fun loadDatas(){
        NetRequest().request(baseUrl!!,callback = object :RequestCallback{
                    override fun onSuccess(response: String) {
                        text=response
                        childItemList=    dataHandlerCallback!!.parseText(text!!)
                        log(msg = "request success! response string:$response")

                    }

                    override fun onFailed(code: Int, errmsg: String?) {
                        log(msg = "request failed! code:$code,and errmsg:$errmsg")
                    }

                })


    }


    /**
     * 解析json数据
     */
    private fun parseJson(text:String){

    }


    /**
     * 解析xml数据
     */
    private fun parseXml(text: String){

    }






    fun refreshDatas(){

    }





}