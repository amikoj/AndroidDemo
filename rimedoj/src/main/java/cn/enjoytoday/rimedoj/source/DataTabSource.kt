package cn.enjoytoday.rimedoj.source

import android.graphics.drawable.Drawable
import android.graphics.drawable.Icon
import cn.enjoytoday.rimedoj.RimedojApplication
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
class DataTabSource() {

    companion object {
        /**
         * 应该还需要一些其他的数据暂时还没想到
         */
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
     * 正常情况下的背景色
     */
    var tabNormalColor:Int?=null


    /**
     * 被选中后的背景色
     */
    var tabSelectedColor:Int?=null


    /**
     * 构造器,构造基础的tab资源
     */
    constructor(title:String,viewType: DataViewType,baseUrl:String,icon: Drawable,tabSelectedColor:Int):this(){
        this.title=title
        this.viewType=viewType
        this.baseUrl=baseUrl
        this.icon=icon
        this.tabSelectedColor=tabSelectedColor
        this.loadDatas()
    }


    /**
     * 加载在线数据
     */
    private fun loadDatas(){

        Thread{

            text = if (dataSourceType.dataMethod==DataSourceType.GET){
                doGet(baseUrl!!)
            }else{
                doPost(baseUrl!!)
            }

            if (dataSourceType.dataType==DataSourceType.JSON){

            }








        }.start()



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



    /**
     * get请求返回
     */
    private fun doGet(url:String):String{

        return ""
    }


    /**
     * post请求返回
     */
    private fun doPost(url:String):String{

        return ""
    }



    fun refreshDatas(){

    }





}