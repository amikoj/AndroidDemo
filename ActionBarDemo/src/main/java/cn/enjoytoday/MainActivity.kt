package cn.enjoytoday;

import android.app.ActionBar
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View

class MainActivity : Activity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
    }






    fun onClick(view:View){
       var id:Int= view.id
        var number=0
        if (id==R.id.theme1){
            number=1
        }else if (id==R.id.theme2){
            number=2
        }else if (id==R.id.theme3){
            number=3
        }else if (id==R.id.theme4){
            number=4
        }
        var intent: Intent = Intent(this,ThemeActivity::class.java)
        intent.putExtra("number",number)
        startActivity(intent)

    }





}
