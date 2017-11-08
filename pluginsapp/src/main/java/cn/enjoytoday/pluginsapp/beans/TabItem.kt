package cn.enjoytoday.pluginsapp.beans

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.widget.TabHost

/**
 * @date 17-11-8.
 * @className TabItem
 * @serial 1.0.0
 *
 * 记录每个tab的基本内容
 */
class TabItem {

    var tabType:TabType=TabType.BLOG //默认为博文类型
    var tabName:String="Blog"    //tab名称
    var tabColor:String="#df5a55"  //背景色
    var tabICon: Drawable?=null  //tab的ICON
    var view: View?=null        //tab的布局view


    //tab的基本信息
    var baseUrl:String?=null //访问url



}