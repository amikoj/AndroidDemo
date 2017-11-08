package cn.enjoytoday.dynamiclib

import android.content.Context
import android.content.pm.PackageManager
import dalvik.system.DexClassLoader
import java.io.File

/**
 * @date 17-11-6.
 * @className IPackageParse
 * @serial 1.0.0
 */
class IPackageParse(private val mContext: Context) {


    companion object {
        private var PLUGIN_APK_PATH="/mnt/sdcard/pultti"      //插件apk存放位置
        var dexClassLoader:DexClassLoader?=null
        var packageManager:PackageManager?=null
        var packageName:String?=null
        var className:String?=null

    }


    private  var pluginPathFile: File?=null




    /**
     * 加载插件apk.
     */
    fun loadPluginApk(pluginPath:String){
        try {
            PLUGIN_APK_PATH=pluginPath
            pluginPathFile=File(PLUGIN_APK_PATH)
            if (pluginPathFile!!.exists()) {
                log(msg = "pluginApks:$pluginPathFile")
                val pluginApks=pluginPathFile!!.listFiles()

                val dexPath=mContext.getDir("dex",android.content.Context.MODE_PRIVATE).absolutePath
                val libPath=mContext.getDir("libs",android.content.Context.MODE_PRIVATE).absolutePath
                dexClassLoader= DexClassLoader(pluginApks[0].absolutePath,dexPath,libPath,mContext.classLoader)
                log(msg = "dexPath:$dexPath,libPath:$libPath,pluginAPks 0:${pluginApks[0].absolutePath}")

                packageManager=mContext.packageManager
                val packageInfo=packageManager!!.getPackageArchiveInfo(pluginApks[0].absolutePath,PackageManager.GET_ACTIVITIES)

                if (packageInfo!=null){
                    packageName=packageInfo.packageName
                    className=packageInfo.activities[0].name

                }
            }else{
                mContext.toast(msg = "插件目录不存在或没有权限访问")
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
        if (pluginPathFile==null){
            mContext.toast(msg = "加载失败")
            return
        }
    }
}