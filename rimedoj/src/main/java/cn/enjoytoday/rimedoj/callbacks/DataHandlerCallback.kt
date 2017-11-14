package cn.enjoytoday.rimedoj.callbacks

import android.telecom.Call
import cn.enjoytoday.rimedoj.source.ItemTab

/**
 * @date 17-11-14.
 * @className DataHandlerCallback
 * @serial 1.0.0
 */
interface DataHandlerCallback:Callback {

    /**
     * 解析返回json
     */
    fun parseText(text:String):MutableList<ItemTab>
}