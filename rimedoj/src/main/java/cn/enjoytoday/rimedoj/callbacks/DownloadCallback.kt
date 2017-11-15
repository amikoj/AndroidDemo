package cn.enjoytoday.rimedoj.callbacks

import android.graphics.drawable.Drawable
import java.io.File

/**
 * @date 17-11-15.
 * @className DownloadCallback
 * @serial 1.0.0
 * 文件下载回调接口
 */
interface DownloadCallback:Callback {


    fun onSuccess(saveFile: Drawable)  //成功返回保存的文件路径

    fun onFailed(errorMsg:String) //失败,返回失败原因


    fun onProcess(process:Long)  //正在下载,下载进度

    fun onStart()      //开始下载


}