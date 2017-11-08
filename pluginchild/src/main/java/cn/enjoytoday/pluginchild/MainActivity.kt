package cn.enjoytoday.pluginchild

import android.app.Activity
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import cn.enjoytoday.dynamiclib.IProxy
import cn.enjoytoday.dynamiclib.IProxyActivity
import cn.enjoytoday.dynamiclib.log
import cn.enjoytoday.dynamiclib.toast
import com.ryg.dynamicload.DLBasePluginActivity

//import com.ryg.dynamicload.DLBasePluginActivity

class MainActivity : DLBasePluginActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }






    fun onClick(view: View){

        when(view.id){

            R.id.du_video ->{
                /**
                 *du_video sdk.
                 */
            }

            R.id.du_swipe ->{
                /**
                 * du_swipe sdk.
                 */



            }
            R.id.du_caller ->{
                /**
                 * du_caller sdk.
                 */


            }

            R.id.du_trigger ->{
                /**
                 * du_trigger sdk.
                 */


            }

            R.id.du_weather ->{
                /**
                 * du_weather sdk.
                 */




            }



        }


    }
}
