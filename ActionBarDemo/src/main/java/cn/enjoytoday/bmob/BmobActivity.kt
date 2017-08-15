package cn.enjoytoday.bmob

import android.app.Activity
import android.content.Intent

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import cn.bmob.v3.Bmob

import cn.enjoytoday.R

import android.widget.EditText

import cn.bmob.v3.datatype.BmobFile
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import cn.bmob.v3.listener.UploadFileListener
import com.smile.filechoose.api.ChooserType
import com.smile.filechoose.api.ChosenFile
import com.smile.filechoose.api.FileChooserListener
import com.smile.filechoose.api.FileChooserManager
import java.io.File



/**
 * bmob test activity.
 */
class BmobActivity constructor():Activity(),FileChooserListener{


    constructor(name:String,type:String):this() {
        Log.e("name", name)
    }



    var TAG:String="BmobActivity"

    var BMOB_APP_ID:String="291b15675a92224a9170e6410fca8ff2"

    var fragment: FrameLayout?=null
    var main_layout: View?=null
    var register_layout: View?=null
    var sign_in_layout:View?=null
    var sign_out_layout:View?=null
    var upload_layout:View?=null
    var fileChooserManager:FileChooserManager?=null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmob)
        Bmob.initialize(this,BMOB_APP_ID)
        initView()
    }


    /**
     * init view.
     */
    fun initView(){
        fragment= this.findViewById(R.id.content) as FrameLayout?
        main_layout=LayoutInflater.from(this).inflate(R.layout.bmob_main,null)
        register_layout=LayoutInflater.from(this).inflate(R.layout.bmob_register_layout,null)
        sign_in_layout=LayoutInflater.from(this).inflate(R.layout.bmob_sign_in_layout,null)
        sign_out_layout=LayoutInflater.from(this).inflate(R.layout.bmob_sign_out_layout,null)
        upload_layout=LayoutInflater.from(this).inflate(R.layout.bmob_upload_layout,null)

        /**
         * 当fragment为空时抛出异常
         * fragment!!.removeAllViews()
         *
         * 当fragment为空时返回为空
         */
        fragment?.removeAllViews()
        fragment?.addView(main_layout)




    }


    fun onClick(view:View){
        when(view.id){
            R.id.register ->{
                fragment?.removeAllViews()
                fragment?.addView(register_layout)

            }

            R.id.sing_in -> {
                fragment?.removeAllViews()
                fragment?.addView(sign_in_layout)
            }

            R.id.sign_out -> {
                fragment?.removeAllViews()
                fragment?.addView(sign_out_layout)
            }

            R.id.upload -> {
                fragment?.removeAllViews()
                fragment?.addView(upload_layout)
            }


            R.id.file_select -> {
                selectFile()

            }


            R.id.upload_file ->{
                toast("upload "+uploadFile())
            }



            else -> {

                toast("No found this id.")
            }
        }


    }


    var  file_get_code:Int= ChooserType.REQUEST_PICK_FILE

    /**
     * 选择等待上传的文件
     */
    fun selectFile(){
//        val intent = Intent(Intent.ACTION_GET_CONTENT)
//        intent.type = "*/*"
//        intent.addCategory(Intent.CATEGORY_OPENABLE)
//        try {
//            startActivityForResult(Intent.createChooser(intent, "selectFile"), file_get_code)
//        } catch (ex: android.content.ActivityNotFoundException) {
//            Log.e(TAG,"not found fileManager application.")
//        }
        fileChooserManager = FileChooserManager(this)
        fileChooserManager?.setFileChooserListener(this)
        try {
            fileChooserManager?.choose()
        } catch (e: Exception) {
            e.printStackTrace()
        }


    }


    fun  uploadFile():Boolean{
        var filePath:String= (this.findViewById(R.id.filename) as EditText).text.toString()

        var file:File= File(filePath)

        var bmobFile:BmobFile=BmobFile(file)


        /**
         * bmob 3.5
         */
        bmobFile.upload( object : UploadFileListener(){
            override fun done(p0: BmobException?) {
                if (p0==null){
                    toast("上传文件成功,"+bmobFile.fileUrl)
                    Log.e(TAG,"upload success")

                    var uploadInfo:UploadInfo= UploadInfo()
                    uploadInfo.userId="demo1"
                    uploadInfo.databaseUrl=bmobFile.fileUrl
                    uploadInfo.fileName=bmobFile.filename
                    uploadInfo.recordNumber=1000

                    uploadInfo.save(object : SaveListener<String>() {

                        override fun done(p0: String?, p1: BmobException?) {
                            Log.e(TAG,"done ")
                            if (p1==null){
                                Log.e(TAG,"create data success,and objectId is:$p0")
                            }else{
                                Log.e(TAG,"create data failed,and errorcode is ${p1.errorCode},errormsg is:${p1.message}")
                            }

                        }
                        override fun onStart() {
                            Log.e(TAG,"onStart ")
                            super.onStart()
                        }


                        override fun onFinish() {
                            Log.e(TAG,"onFinish ")
                            super.onFinish()
                        }
                    })






                }else{
                    toast("上传文件失败，,"+ p0?.message)
                }

            }

            override fun onProgress(value: Int?) {
                Log.e(TAG,"upload progress is $value")
            }

            override fun doneError(code: Int, msg: String?) {
                super.doneError(code, msg)
            }

            override fun onFinish() {
                super.onFinish()
            }
        })


        return bmobFile!=null
    }


    var chooserFile:ChosenFile?=null


    override fun onFileChosen(p0: ChosenFile?) {
        Log.e(TAG,"chosen success:${p0?.filePath}")
        chooserFile=p0

        runOnUiThread {
            (this.findViewById(R.id.filename) as EditText).setText(chooserFile!!.filePath)
        }

    }

    override fun onError(p0: String?) {
        Log.e(TAG,"chosen error:$p0")

    }






    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.e(TAG,"get requestCode is：$requestCode,and data is:$data")
        if (resultCode != Activity.RESULT_OK) {
            Log.e(TAG, "onActivityResult() error, resultCode: " + resultCode)
            super.onActivityResult(requestCode, resultCode, data)
            return
        }
        if (requestCode === file_get_code) {

            if (fileChooserManager == null) {
                fileChooserManager = FileChooserManager(this)
                fileChooserManager?.setFileChooserListener(this)
            }
            Log.e(TAG, "Probable file size: " + fileChooserManager?.queryProbableFileSize(data?.getData(), this))
            fileChooserManager?.submit(requestCode, data)

        }
        super.onActivityResult(requestCode, resultCode, data)
    }





    /**
     * toast add.
     */
    fun Activity.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, message, duration).show()
    }



}


