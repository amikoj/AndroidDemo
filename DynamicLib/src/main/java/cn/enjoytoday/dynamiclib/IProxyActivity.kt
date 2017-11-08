package cn.enjoytoday.dynamiclib

import android.app.Activity
import android.content.res.AssetManager
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import dalvik.system.DexClassLoader

/**
 * @date 17-11-6.
 * @className IProxyActivity
 * @serial 1.0.0
 * 作为代理类实现插件界面的跳转处理
 */
open class IProxyActivity: Activity() {

    companion object {
        val EXTRA_DATA="extra_data"
        val EXTRA_DEX_PATH="extra_dex_path"
        val EXTRA_CLASS="extra_class"
        val EXTRA_FROM="extra_from"
        val TAG="ProxyActivity"
        val FROM_EXTERNAL=0
        val FROM_INTERNAL=1
    }


    private var activity:Activity?=null

    private var className:String?=null


    protected var classLoader:DexClassLoader?=null        //dex加载classLoader.


    private var targetActivity:IProxyActivity?=null

    protected var pluginActivity:IProxyActivity?=null


     var mResource:Resources?=null

    protected var mAssetManager:AssetManager?=null

    protected var dexPath:String?=null



    override fun onCreate(savedInstanceState: Bundle?) {
        log(msg = "onCreate")

        if (intent!=null && targetActivity==null) {
            log(msg = "intent is not null ,and targetActivity is null.")
            super.onCreate(savedInstanceState)
            val className = intent.getStringExtra("extra_class")
            if (className != null) {

                try {
                    val localClass = IPackageParse.dexClassLoader!!.loadClass(className)
                    val localConstructor = localClass.getConstructor()
                    val localObject = localConstructor.newInstance() as IProxyActivity
                    dexPath = intent.getStringExtra(EXTRA_DEX_PATH)
                    this.setActivity(localObject, dexPath!!, IPackageParse.dexClassLoader!!)
                } catch (e: Exception) {
                    e.printStackTrace()
                    finish()
                }

            }
        }

        if (targetActivity!=null){
            targetActivity!!.onCreate(savedInstanceState)
        }

    }





    fun setActivity(targetActivity: IProxyActivity,dexPath: String,classLoader: DexClassLoader){
        this.targetActivity=targetActivity
        this.classLoader=classLoader
        this.dexPath=dexPath
        log(msg = "dexpath:$dexPath")
        val asset=createAssetManager(dexPath)
        val res=createResources(asset!!)
        this.mAssetManager=asset
        this.mResource=res
        targetActivity.classLoader=classLoader
        targetActivity.dexPath=dexPath
        targetActivity.mAssetManager=asset
        targetActivity.mResource=res

        targetActivity.pluginActivity=this

    }


    override fun setContentView(layoutResID: Int) {
        if(pluginActivity!=null) {
            log(msg = "set Content View.----------------id:$layoutResID")
            this.pluginActivity!!.setContentView(layoutResID)
        }else{
            super.setContentView(layoutResID)
        }
    }


    override fun setContentView(view: View?) {
        if(pluginActivity!=null) {

            this.pluginActivity!!.setContentView(view)
        }else{
            super.setContentView(view)
        }
    }


    private fun createAssetManager(dexPath: String): AssetManager? {
        return try {
            val assetManager = AssetManager::class.java.newInstance()
            val addAssetPath = assetManager.javaClass.getMethod("addAssetPath", String::class.java)
            addAssetPath.isAccessible=true
            log(msg = "add AssetPath:$dexPath")
            addAssetPath.invoke(assetManager, dexPath)
            assetManager
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

    }


    override fun getAssets(): AssetManager{
        return if (targetActivity!=null && mAssetManager!=null){
                 mAssetManager!!
        }else {
             super.getAssets()
        }
    }


    private fun createResources(assetManager: AssetManager): Resources {
        val superRes = this.resources
        return Resources(assetManager, superRes.displayMetrics, superRes.configuration)
    }





    override fun getResources(): Resources {
        return  if (targetActivity!=null && mResource!=null) {
            log(msg = "getResources")
            mResource!!
        } else{
            super.getResources()
        }
    }



    override fun onResume() {
        if (pluginActivity==null) {
            super.onResume()
        }
        if (targetActivity!=null){
            targetActivity!!.onResume()
        }
    }


    override fun onDestroy() {
        if (pluginActivity==null) {
            super.onDestroy()
        }
        if (targetActivity!=null){
            targetActivity!!.onDestroy()
        }
    }


}