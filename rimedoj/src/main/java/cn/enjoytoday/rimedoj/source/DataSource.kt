package cn.enjoytoday.rimedoj.source

import android.graphics.Color

/**
 * @date 17-11-10.
 * @className DataSource
 * @serial 1.0.0
 * 首页的整体风格设置
 */
class DataSource {
    /**
     * 默认tab背景色设置
     */
    var defaultTabNormalColor:Int= Color.parseColor("#3F51B5")

    /**
     * 默认tab中选中item背景色
     */
    var defaultTabItemSelectedColor=Color.parseColor("#76afcf")

    /**
     * 默认背景色,主色调'白色'
     */
    var defaultPageColor=Color.WHITE

    /**
     * 所有数据源设置项
     */
    var dataItemSources:MutableList<DataTabSource> = mutableListOf()


    /**
     * 收藏信息
     */
    var collectionsDataList:MutableList<ItemTab> = mutableListOf()


    /**
     * 加载数据源
     */
    fun loadDataSource(){





    }


    /**
     * 加载默认数据源
     */
    private fun loadDefaultDataSource(){

    }






}