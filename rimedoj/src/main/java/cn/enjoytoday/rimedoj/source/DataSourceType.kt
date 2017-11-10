package cn.enjoytoday.rimedoj.source

import android.support.annotation.StringDef


/**
 * @date 17-11-10.
 * @className DataSourceType
 * @serial 1.0.0
 * 数据类型相关,包含返回数据格式等
 */
class DataSourceType {



    companion object {


        /**
         * 数据源的格式类型
         */
        const val XML="xml"       //数据以xml格式返回
        const val JSON="json"     //数据以json格式返回


        /**
         * 数据排列方式,暂时只支持list和map两种方式
         */
        const val LIST="list"    //list数组类型
        const val MAP="map"      //map类型展示


        /**
         * 数据源获取的格式
         */
        @StringDef(XML, JSON)
        @Retention(AnnotationRetention.SOURCE)
        annotation class dataSourcePattern


        /**
         * 数据源排列方式
         */
        @StringDef(LIST, MAP)
        @Retention(AnnotationRetention.SOURCE)
        annotation class  arrangement

    }


    /**
     * 数据格式
     */
    @dataSourcePattern  var dataType = JSON

    /**
     * 数据排列方式
     */
    @arrangement var dataArrangement = MAP


    /**
     * 默认构造参数,默认设置
     */
    constructor()



    constructor(@dataSourcePattern dataType:String,@arrangement dataArrangement:String){
        this.dataArrangement=dataArrangement
        this.dataType=dataType
    }








}