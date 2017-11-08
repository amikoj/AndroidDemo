package cn.enjoytoday.plugins

import android.app.Activity
import android.content.res.AssetManager
import android.content.res.Resources
import android.os.Bundle
import android.os.PersistableBundle

/**
 * @date 17-10-30.
 * @className PluginsManager
 * @serial 1.0.0
 */
class PluginsManager:Activity() {


    var mAssetManger:AssetManager?=null
    var mResources:Resources?=null

    var mDexPath=""
    var mTheme:Resources.Theme?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    fun loadResources(){

        try {
            val assetsManager = AssetManager::class.java.newInstance()
            val addAssetPath = assetsManager.javaClass.getMethod("addAssetPath", String.javaClass)
            addAssetPath.invoke(assetsManager, mDexPath)
            mAssetManger=assetsManager
        }catch (e:Exception){
            e.printStackTrace()
        }


        resources
        val superRes=super.getResources()
        mResources= Resources(mAssetManger,superRes.displayMetrics,superRes.configuration)

        mTheme= mResources!!.newTheme()
        mTheme!!.setTo(super.getTheme())

    }
}