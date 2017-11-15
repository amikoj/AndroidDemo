package cn.enjoytoday.rimedoj.source

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import cn.enjoytoday.rimedoj.R
import cn.enjoytoday.rimedoj.callbacks.DataHandlerCallback
import cn.enjoytoday.rimedoj.log
import org.json.JSONObject

/**
 * @date 17-11-10.
 * @className DataSource
 * @serial 1.0.0
 * 首页的整体风格设置
 */
class DataSource(var context:Context) {



    companion object {
        var DATA_LOADED=false
        const val UPDATE_DATA_SOURCE="cn.enjoytoday.rimedoj.update_tab_sources" //数据源更新通知广播




    }







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
     * 加载数据源
     */
    fun loadDataSource(){
        if (!DATA_LOADED) {
            loadDefaultDataSource()
        }else{
            /**
             *更新数据源
             */
            for (dataTabSource in dataItemSources){
                dataTabSource.refreshDatas()
            }
        }
    }


    /**
     * 加载默认数据源
     */
    private fun loadDefaultDataSource(){

        DATA_LOADED=true


        if (dataItemSources.size>0){
            dataItemSources.clear()
        }

        val name= arrayListOf<String>("知乎","csdn","stackOverflow","gitHub")
        var urls = arrayListOf<String>("http://news-at.zhihu.com/api/4/news/latest",
                "http://news-at.zhihu.com/api/4/news/latest",
                "http://news-at.zhihu.com/api/4/news/latest",
                "http://news-at.zhihu.com/api/4/news/latest")

        val drawables= arrayListOf<Drawable>(context.resources.getDrawable(R.drawable.ic_first),
                context.resources.getDrawable(R.drawable.ic_second),
                context.resources.getDrawable(R.drawable.ic_third),
                context.resources.getDrawable(R.drawable.ic_fourth))

        val colors= arrayListOf<Int>(context.resources.getColor(R.color.normal_background_color),
                context.resources.getColor(R.color.normal_background_color),
                context.resources.getColor(R.color.normal_background_color),
                context.resources.getColor(R.color.normal_background_color))





        (0..3)
                .map {
                    DataTabSource.getDataTabSource(name[it],urls[it],drawables[it],DataTabSource.TYPE_LINE_2,colors[it],object :DataHandlerCallback{
                        override fun parseText(text: String): MutableList<ItemTab> {
                            log("parse text,and text:$text")
                            val items= mutableListOf<ItemTab>()
                            try {
                                val json = JSONObject(text)
                                val jsonArray=json.getJSONArray("stories")
                                for (i in 0 until jsonArray.length()){

                                    val item=ItemTab(i)
                                    val itemJson=jsonArray.getJSONObject(i)
                                    item.paramsMap.put("item_line_2_title",itemJson.getString("title"))
//                                    item.paramsMap.put("item_line_2_title",itemJson.getString("ga_prefix"))
                                    val imageUrls=itemJson.getJSONArray("images")
                                    item.paramsMap.put("item_line_2_image",imageUrls.getString(0))
                                    items.add(item)
                                }


                            }catch (e:Exception){
                                e.printStackTrace()
                                log("parse failed")
                            }

                            return items
                        }

                    })
                }
                .forEach { dataItemSources.add(it) }


    }













}