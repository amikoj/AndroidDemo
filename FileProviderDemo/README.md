[BLOG](http://www.enjoytoday.cn/archives/196)

### 说明
Android系统为了安全的缘故，从android 7.0以后已经取消了通过**file://** 文件方式或Sharepreference的**MODE_WORLD_READABLE** 或 **MODE_WORLD_WRITEABLE**方式进行应用间数据分享了，同意采用Android系统提供的FileProvider共享授权访问的方式进行数据共享。

### 使用
使用FileProvide实现应用间的数据共享，需要完成三个步骤：注册、路径配置、共享Uri生成。

1. **注册**
其实FileProvider本身就是一个继承ContentProvider的子类，其本质就是一个ContentProvider，所以在使用ContentProvider之前我们需要在manifest清单文件中对它进行注册：

```
 <manifest>
    ...
    <application>
        ...
     <provider
           android:name="android.support.v4.content.FileProvider"
            android:authorities="com.hfcai.fileprovider"
            android:exported="false"
            android:grantUriPermissions="true">

    <meta-data
          android:name="android.support.FILE_PROVIDER_PATHS"
          android:resource="@xml/file_paths" />
     </provider>
        ...
    </application>
</manifest>

```

如下对于，provider设置的几个属性进行介绍:

- **android:name**
    name对应设置需要注册的provider的完整类名，可以使用v4包里的FileProvider也可以自定义继承自FileProvider的provider。

- **android:authorities**
   为provider的签名，可以自己指定，没有特别要求。

-  **android:grantUriPermissions***
   一般设置为true,默认分享的app可以获取其访问权限。

- **meta-data**
  meta-data中设置可供访问的文件路径配置文件的位置。


2. **路径配置**

注册完provider后需要对已经注册的provider的可访问路径进行配置，如上的meta-data只想的文件。

```
<?xml version="1.0" encoding="utf-8"?>
<paths xmlns:android="http://schemas.android.com/apk/res/android">
      <!--Context.getFilesDir().-->
    <files-path path="images" name="images"/>
    <!-- getCacheDir().-->
    <cache-path name="images1" path="images" />
    <!-- Environment.getExternalStorageDirectory().-->
    <external-path name="images2" path="images" />
    <!--Context#getExternalFilesDir(String) Context.getExternalFilesDir(null).   -->
    <external-files-path name="images3" path="images" />
    <!-- Context.getExternalCacheDir().  -->
    <external-cache-path name="images4" path="images" />
</paths>
```

如上，paths提供了集中不同的元素指代不同路径前缀的目录：

- files-path
  Context.getFilesDir()为目录前缀
- cache-path
  getCacheDir()为目录前缀
- external-path
   Environment.getExternalStorageDirectory()为目录前缀
- external-files-path
  Context#getExternalFilesDir(String) Context.getExternalFilesDir(null).w为目录前缀
- external-cache-path
   Context.getExternalCacheDir()为目录前缀

3. **共享Uri生成**

如上配置完成使用环境接下来就是生成共享使用的uri了，如下为一个微信分享的功能代码：

```
               Uri imgUriOri;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    imgUriOri = Uri.fromFile(new File(filePath));
                } else {
                    imgUriOri = FileProvider.getUriForFile(this,
                            this.getPackageName() + ".provider", tmpFile);
                }

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_STREAM, imgUriOri);
                intent.putExtra(Intent.EXTRA_SUBJECT, "测试标题");
                intent.putExtra(Intent.EXTRA_TEXT, "测试文本");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("sms_body", "测试短信文本");
                intent.putExtra("Kdescription", "测试微信朋友圈文本");
                intent.putExtra(Intent.EXTRA_TEXT, "分享到微信的内容");
                startActivity(intent);
```

如上就完成了一个简单的应用间数据共享的功能。








