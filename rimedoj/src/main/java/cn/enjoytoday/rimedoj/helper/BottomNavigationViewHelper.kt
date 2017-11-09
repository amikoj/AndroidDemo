package cn.enjoytoday.rimedoj.helper

import android.annotation.SuppressLint
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView


/**
 * @date 17-11-9.
 * @className BottomNavigationViewHelper
 * @serial 1.0.0
 */
class BottomNavigationViewHelper {

    companion object {

        @SuppressLint("RestrictedApi")
        fun disableShiftMode(navigationView: BottomNavigationView){
            val menuView = navigationView.getChildAt(0) as BottomNavigationMenuView
            try {
                val shiftingMode = menuView.javaClass.getDeclaredField("mShiftingMode")
                shiftingMode.isAccessible = true
                shiftingMode.setBoolean(menuView, false)
                shiftingMode.isAccessible = false
                for (i in 0 until menuView.childCount) {
                    val item = menuView.getChildAt(i) as BottomNavigationItemView
                    item.setShiftingMode(false)
                    // set once again checked value, so view will be updated
                    item.setChecked(item.itemData.isChecked)
                }
            }catch (e:Exception){
                e.printStackTrace()

            }
        }
    }

}