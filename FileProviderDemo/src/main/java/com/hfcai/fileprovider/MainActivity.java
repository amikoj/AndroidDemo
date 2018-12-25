package com.hfcai.fileprovider;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Process;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {


    private static final String TAG=MainActivity.class.getSimpleName();
    private File imagesPath;
    private File newFile;
    private TextView fromFileTextView;
    private TextView parseStringTextView;
    private TextView fileProviderTextView;
    private Button openFromFile;
    private Button openParseString;
    private Button openWithFileProvider;
    private Button copyFile;
    private final int PHOTO_REQUEST_CODE=10001;
    private final int PHOTO_REQUEST_CUT=10002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e(TAG,"this is test.");

        fromFileTextView= (TextView) findViewById(R.id.parse_file_textview);
        parseStringTextView= (TextView) findViewById(R.id.parse_string_textview);
        fileProviderTextView= (TextView) findViewById(R.id.file_provider_uri_textview);

        openFromFile= (Button) findViewById(R.id.open_with_file);
        openParseString= (Button) findViewById(R.id.open_with_string);
        openWithFileProvider= (Button) findViewById(R.id.open_with_fileprovider);
        copyFile= (Button) findViewById(R.id.copy_file);


        imagesPath=new File(Environment.getExternalStorageDirectory(),"images");
        newFile=new File(imagesPath,"test.png");

        if (this.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.os.Process.myPid(),
                Process.myUid())!= PackageManager.PERMISSION_GRANTED){
            requestPermission();
        }
        if (!imagesPath.exists()){
            Log.e(TAG,"mkdir begin:"+imagesPath.getAbsolutePath());
            imagesPath.mkdirs();
        }
        refresh();
    }


    @TargetApi(23)
    public void requestPermission(){
        this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
    }




    public void refresh(){
        if (newFile.exists()){
            openFromFile.setEnabled(true);
            openParseString.setEnabled(true);
            openWithFileProvider.setEnabled(true);
            Log.e(TAG,"test file is exists.");
            Uri uri=null;
            uri=Uri.fromFile(newFile);
            fromFileTextView.setText(uri.toString());
            Log.e(TAG,"uri from file:"+uri.toString());

            uri=Uri.parse(newFile.getAbsolutePath());
            parseStringTextView.setText(uri.toString());
            Log.e(TAG,"uri parse string:"+uri.toString());

            uri=FileProvider.getUriForFile(this,"com.hfcai.fileprovider",newFile);
            fileProviderTextView.setText(uri.toString());
            Log.e(TAG,"fileprovider uri string:"+uri.toString());

        }else{
            Log.e(TAG,"test file is not exists.");
            fromFileTextView.setText("test file not exists.");
            parseStringTextView.setText("test file not exists.");
            fileProviderTextView.setText("test file not exists.");
            openFromFile.setEnabled(false);
            openParseString.setEnabled(false);
            openWithFileProvider.setEnabled(false);
        }
    }




    /**
     * 点击处理
     * @param view
     */
    public void click(View view){
        Log.e(TAG,"click handle...");
        Intent intent;

        switch (view.getId()){
            case R.id.open_with_file:
                intent=new Intent(Intent.ACTION_VIEW);
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(Uri.fromFile(newFile),"image/*");
                startActivity(intent);
                break;

            case R.id.open_with_string:
                intent=new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse("file://"+newFile.getAbsolutePath()),"image/*");
                startActivity(intent);

                break;


            case R.id.open_with_fileprovider:
                intent=new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(FileProvider.getUriForFile(this,"com.hfcai.fileprovider",newFile),"image/*");
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                startActivity(intent);
                break;

            case R.id.copy_file:
                intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,PHOTO_REQUEST_CODE);
                break;


            case R.id.take_photo:
                String cachePath = getApplicationContext().getExternalCacheDir().getPath()+File.separator+"images";
                File picFile = new File(cachePath, "test.jpg");
                Uri picUri = Uri.fromFile(picFile);
                Log.e(TAG,"picUri:"+picUri);
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
                startActivityForResult(intent, 100);
                break;

            case R.id.take_photo_new:
                String cachePath1 = getApplicationContext().getExternalCacheDir().getPath()+File.separator+"images";
                File picFile1 = new File(cachePath1, "test.jpg");
                Uri picUri1 = FileProvider.getUriForFile(this,"com.hfcai.fileprovider",picFile1);
                Log.e(TAG,"picUri:"+picUri1);
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, picUri1);
                startActivityForResult(intent, 100);
                break;
            default:
                break;
        }



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * handle 图片处理
         */

        Log.e(TAG,"onActivityResult");
        if (requestCode==PHOTO_REQUEST_CODE){
            if (data!=null){
                Uri uri=data.getData();
                //获取相册的原图片
                Intent intent=new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(uri,"image/*");
                intent.putExtra("crop",true);
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("outputX", 250);
                intent.putExtra("outputY", 250);
                // 图片格式
                intent.putExtra("outputFormat", "PNG");
                // 取消人脸识别
                intent.putExtra("noFaceDetection", true);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, PHOTO_REQUEST_CUT);
            }
        }else  if (requestCode==PHOTO_REQUEST_CUT){

            if (data!=null){
                Bitmap bitmap = data.getParcelableExtra("data");
                if (newFile.exists()){
                    newFile.delete();
                }


                if (bitmap==null){
                    return;
                }

                BufferedOutputStream bufferedOutputStream=null;
                try {

                    if (newFile.exists()){
                        newFile.createNewFile();
                    }
                    bufferedOutputStream = new BufferedOutputStream( new FileOutputStream(newFile));
                    if (bitmap.compress(Bitmap.CompressFormat.PNG,90,bufferedOutputStream)){
                        bufferedOutputStream.flush();
                        bufferedOutputStream.close();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

                bitmap.recycle();
                bitmap=null;

            }
        }

        refresh();

    }








}
