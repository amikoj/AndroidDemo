package cn.enjoytoday

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView

/**
 * @date 17-8-9.
 * @className ThemeActivity
 * @serial 1.0.0
 */

 class ThemeActivity : Activity() {
    var name:String = "theme1";



    override fun  onCreate(savedInstanceState:Bundle?) {
        if (intent!=null){
           var  number: Int = intent.extras.getInt("number")
            println("number:"+number)
            var theme:Int=R.style.AppTheme_Theme1
            if (number==1){
                theme=R.style.AppTheme_Theme1
                name="theme1"
            }else if (number==2){
                theme=R.style.AppTheme_Theme2
                name="theme2"
            }else if (number==3){
                theme=R.style.AppTheme_Theme3
                name="theme3"
            }else if (number==4){
                theme=R.style.AppTheme_Theme4
                name="theme4"
            }
            setTheme(theme)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.theme_layout)
        var tipsTextView:TextView= findViewById(R.id.tips) as TextView

        tipsTextView.text="This is ${name} activity."

    }





}
