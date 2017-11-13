package cn.enjoytoday.rimedoj

import android.app.Application
import cn.enjoytoday.rimedoj.source.DataSource
import cn.enjoytoday.rimedoj.source.DataTabSource

/**
 * @date 17-11-13.
 * @className RimedojApplication
 * @serial 1.0.0
 */
class RimedojApplication:Application() {


    companion object {
        var dataSource: DataSource=DataSource()    //数据源
        var dataTabSources:MutableList<DataTabSource> = mutableListOf()


        init {
            dataSource.loadDataSource()  //加载基本配置资源
            dataTabSources= dataSource.dataItemSources
        }





    }


    override fun onCreate() {
        super.onCreate()
    }
}