package cn.enjoytoday.pluginsapp.plugin

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import dalvik.system.DexClassLoader

/**
 * @date 17-11-1.
 * @className ProxyActivity
 * @serial 1.0.0
 */
open class ProxyActivity :Activity() {

    companion object {
        val EXTRA_DEX_PATH="extra_dex_path"
        val EXTRA_CLASS="extra_class"
        val EXTRA_FROM="extra_from"
        val TAG="ProxyActivity"
        val FROM_EXTERNAL=0
        val FROM_INTERNAL=1
    }


    private var mClass:String?=null
    private var mDexPath:String?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mDexPath=intent.getStringExtra(EXTRA_DEX_PATH)
        mClass=intent.getStringExtra(EXTRA_CLASS)

        Log.e(TAG,"mClass = $mClass ,mDexPath:$mClass")

        if (mClass == null){

            launchTargetActivity()
        }else{
            launchTargetActivity(mClass!!)
        }

    }



    private fun launchTargetActivity(className:String){



        val packageInfo=packageManager.getPackageArchiveInfo(mDexPath,PackageManager.GET_ACTIVITIES)

        val dexOutputDir=this.getDir("dex",MODE_PRIVATE)

        val dexOutputPath=dexOutputDir.absolutePath
        val localClassLoader=ClassLoader.getSystemClassLoader() //PathClassLoader
        val dexClassLoader=DexClassLoader(mDexPath,dexOutputPath,null,localClassLoader)


        try {

            val localClass=dexClassLoader.loadClass(className)

            val localConstructor=localClass.getConstructor()
            val obj=localConstructor.newInstance()

            val setProxy=localClass.getMethod("setProxy",Activity::class.java)
            setProxy.isAccessible=true
            setProxy.invoke(obj,this)

            val onCreate=localClass.getDeclaredMethod("onCreate",Bundle::class.java)
            onCreate.isAccessible=true

            val bundle=Bundle()
            bundle.putInt(EXTRA_FROM, FROM_EXTERNAL)
            onCreate.invoke(obj,bundle)
        }catch (e:Exception){
            e.printStackTrace()
        }


    }


    private fun launchTargetActivity(){
        val packageInfo=packageManager.getPackageArchiveInfo(mDexPath,PackageManager.GET_ACTIVITIES)

        if (packageInfo.activities!=null && packageInfo.activities.isNotEmpty()){
            val activityName=packageInfo.activities[0].name
            mClass=activityName
            launchTargetActivity(mClass!!)
        }
    }




}