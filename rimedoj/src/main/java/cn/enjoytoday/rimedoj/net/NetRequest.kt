package cn.enjoytoday.rimedoj.net

import android.support.annotation.NonNull
import cn.enjoytoday.rimedoj.callbacks.Callback
import cn.enjoytoday.rimedoj.callbacks.DownloadCallback
import cn.enjoytoday.rimedoj.callbacks.RequestCallback
import cn.enjoytoday.rimedoj.log
import cn.enjoytoday.rimedoj.source.DataSourceType
import okhttp3.*
import okio.ByteString.read
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.TimeUnit

/**
 * @date 17-11-14.
 * @className NetRequest
 * @serial 1.0.0
 *
 * 网络请求处理工具
 */
class NetRequest {

    companion object {

        /**
         * 本地缓存路径
         */
        private val  cacheDir:File=File("/mnt/sdcard/cn.enjoytoday.rimedoj")
        private var openCached=true
        init {
            try {
                if (!cacheDir.exists()){
                    cacheDir.mkdir()
                }
            }catch (e:Exception){
                e.printStackTrace()
                print("no permission.")
                openCached=false
            }

        }


    }

    /**
     * okhttpClient 客户端
     */
    var httpClient:OkHttpClient?=null



    /**
     * 协议类型,支持http,https.默认为http协议
     */
    var protocol:String="http"

    /**
     * 默认缓存大小
     */
    var cacheMemory:Long=10*1024*1024


    val CONNECTION_TIME_OUT=10000L
    val READ_TIME_OUT=10000L

    /**
     * 缓存
     */
    var cache:Cache?=null




    constructor(){
        val   build=OkHttpClient.Builder()
                .connectTimeout(CONNECTION_TIME_OUT,TimeUnit.MILLISECONDS)
                .readTimeout(READ_TIME_OUT,TimeUnit.MILLISECONDS)
        if (openCached){
            cache=Cache(cacheDir,cacheMemory)
            build.cache(cache)
        }

        httpClient=build.build()
    }


    /**
     * @param url 请求url
     * @param method 请求方法
     * @param params 请求参数
     * @param callback 回调接口
     */
    @Synchronized fun request(url: String,@DataSourceType.Companion.method method:String=DataSourceType.GET,
                              params:MutableMap<String,String> = mutableMapOf(), callback: RequestCallback){

        if (method==DataSourceType.GET){
            get(url, params, callback)
        }else{
            post(url, params, callback)
        }

    }


    /**
     * @param url
     * @return
     *
     */
    @NonNull
    private fun getNameFromUrl( url:String):String{
        return url.substring(url.lastIndexOf("/") + 1);
    }


    /***
     * @param url 下载文件资源定位符
     * @param saveDir 资源缓存地址
     */
    @Synchronized fun download(url: String,saveDir:String,callback:DownloadCallback?=null){

//        val request=Request.Builder().url(url).build()
//
//        val call=httpClient!!.newCall(request)

        Thread {
            try {

                val destDir = File(cacheDir, saveDir)
                val exists=destDir.exists()
                log("exists:$exists")
               val isSuccess= destDir.mkdirs()
                log("mkdir is success:$isSuccess")
//                if (!destDir.exists()) {
//                    destDir.mkdirs()
//                }

                val destFile = File(destDir, getNameFromUrl(url))
                val requestUrl = URL(url)
                val bytes=requestUrl.readBytes()
//                destFile.writeBytes(bytes)

                callback?.onSuccess(bytes)

//            val response=  call.execute()
//
//            if (response!=null) {
//
//                if (response.isSuccessful) {
//                    val inputStream = response.body()!!.byteStream()
//                    val destDir = File(cacheDir, saveDir)
//                    if (!destDir.exists()) {
//                        destDir.mkdir()
//                    }
//                    val destFile = File(destDir, getNameFromUrl(url))
//
//                    val outPutStream = FileOutputStream(destFile)
//                    var buf = ByteArray(2048)
//                    var len = 0
//                    var sum: Long =
//
//
//                }else{
//                    callback?.onFailed(response.message())
//                }


//            }else{
//                callback?.onFailed("access network failed.")
//            }


            } catch (e: Exception) {
                e.printStackTrace()
                log("download file failed")
                callback?.onFailed("access network failed.")
            }
        }.start()

    }




    /**
     * get请求
     * @param url 请求地址
     * @param params 请求参数
     * @param callback 回调返回
     */
   @Synchronized private fun get(url: String, params:MutableMap<String,String> = mutableMapOf(), callback: RequestCallback){
        var requestUrl=url
        if (params.isNotEmpty()){
            requestUrl+="?"
            params.forEach{
                requestUrl+=it.key+"="+it.value
            }
        }

        val request=Request.Builder().url(requestUrl).build()

        val call=httpClient!!.newCall(request)
        Thread{

            try {
                val response=call.execute()
                if (response!=null && response.isSuccessful){
                    log("request according to GET, and request success.")
                    callback.onSuccess(response.body()!!.string())
                }

            }catch (e:Exception){
                e.printStackTrace()
                log("request get method,and request failed,no response return.")
            }

        }.start()

    }


    /**
     * post请求
     *
     * @param url 请求url
     * @param params 请求参数
     * @param callback 回调返回接口
     *
     */
    @Synchronized private fun post(url: String, params:MutableMap<String,String> = mutableMapOf(), callback: RequestCallback){
        val formBody = FormBody.Builder()

        if (params.isNotEmpty()){
            params.forEach{
                formBody.add(it.key,it.value)
            }
        }

        val request = Request.Builder()
                .url(url)
                .post(formBody.build())
                .build()

        val call = httpClient!!.newCall(request)
        try {
            val response=   call.execute()
            if (response!=null) {
                if (response.isSuccessful) {
                    /**
                     * 请求成功
                     */
                    callback.onSuccess(response.body()!!.string())
                } else {
                    /**
                     * 请求失败
                     */
                    callback.onFailed(response.code(), response.message())
                }
            }else{

                log(msg = "unknown error when request:$url")
            }

        }catch (e:Exception){
            log(msg = "net work exception:${e.message}")
            e.printStackTrace()
        }


    }


}