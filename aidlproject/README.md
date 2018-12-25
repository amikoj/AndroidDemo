AIDL(Android Interface Definition Language) 即Android 接口定义语言，是用来实现不同进程间通信的。AIDL同时也是另外两种进程通信方式Messager和ContentProvider的底层实现方法，所以了解aidl的使用显得尤为重要。

本案例可在 **[Github](https://github.com/fishly/AndroidDemo/tree/master/aidlproject)** 获取到Demo源码。

### 操作步骤

AIDL通信的实现是需要客户端和服务端的配合，具体的实现过程如下：

#### 创建aidl文件
aidl文件是客户端请求服务端操作获取交互信息的基础，只需要在Android Studio的**src/main**目录下创建一个aidl目录(或者也可以在gradle中指定aidl位置)，然后在该目录下创建一个aidl文件，android studio会自动生成一个包名并将aidl放在该包下。

```
// ITestInterface.aidl
package com.kubo.aidlproject;
import com.kubo.aidlproject.TestData;

// Declare any non-default types here with import statements
//设置客户端调用的接口
interface ITestInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
   void sendTest(in TestData testData);


   void sendTest2(out TestData testData,int a);

   void sendTest3(inout TestData testData,String a);

}

```
如上所示，aidl支持如下如下数据传递:
- Java所有的基本数据类型(int、long、short等)
- String和CharSequence
- List 子项中也必须是aidl支持的类型
- Map 子项中也必须是aidl支持的类型
- AIDL aidl本省也可作为传递的类型
- Parcelable 所有实现Parcelable接口的对象

AIDL中除了基本类型外其他类型需要设置数据流的方向(**in**、**out**,**inout**)：

- in
  in 代表为输入流方向，即client可修改,service端修改无效
- out
  out 代表输出流方向，即service端可修改,client修改无效
- inout
  inout代表双向流，即service和client端均可对其进行修改



如上，创建的aidl需要传递的参数TestData:


```
package com.kubo.aidlproject;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 * 测试数据序列化
 * @author hfcai
 */
public class TestData implements Parcelable {
    private int id;
    private String name;

    @Override
    public String toString() {
        return "TestData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tips='" + tips + '\'' +
                ", can=" + can +
                '}';
    }

    private String tips;
    private boolean can;

    public TestData(){}

    public TestData(int id, String name, String tips, boolean can) {
        this.id = id;
        this.name = name;
        this.tips = tips;
        this.can = can;
    }

    protected TestData(Parcel in) {
        id = in.readInt();
        name = in.readString();
        tips = in.readString();
        can = in.readByte()!=0;

    }


    public void readFromParcel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        tips = in.readString();
        can = in.readByte()!=0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(tips);
        dest.writeByte((byte) (can?1:0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TestData> CREATOR = new Creator<TestData>() {
        @Override
        public TestData createFromParcel(Parcel in) {
            return new TestData(in);
        }

        @Override
        public TestData[] newArray(int size) {
            return new TestData[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCan() {
        return can;
    }

    public void setCan(boolean can) {
        this.can = can;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }


}
```


同时还需要在aidl中声明一个parcelable对象:

```
//TestData .aidl
package com.kubo.aidlproject;

// Declare any non-default types here with import statements
parcelable TestData;
```

创建完aidl后,android sdk工具可将其自动转化为对应的继承**IInterface**的接口，其实也就是生成一个binder的过程，如上aidl对应的接口如下:

```
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.kubo.aidlproject;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ITestInterface extends IInterface {
    void sendTest(TestData var1) throws RemoteException;

    void sendTest2(TestData var1, int var2) throws RemoteException;

    void sendTest3(TestData var1, String var2) throws RemoteException;

    public abstract static class Stub extends Binder implements ITestInterface {
        private static final String DESCRIPTOR = "com.kubo.aidlproject.ITestInterface";
        static final int TRANSACTION_sendTest = 1;
        static final int TRANSACTION_sendTest2 = 2;
        static final int TRANSACTION_sendTest3 = 3;

        public Stub() {
            this.attachInterface(this, "com.kubo.aidlproject.ITestInterface");
        }

        public static ITestInterface asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            } else {
                IInterface iin = obj.queryLocalInterface("com.kubo.aidlproject.ITestInterface");
                return (ITestInterface)(iin != null && iin instanceof ITestInterface ? (ITestInterface)iin : new ITestInterface.Stub.Proxy(obj));
            }
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            String descriptor = "com.kubo.aidlproject.ITestInterface";
            TestData _arg0;
            switch(code) {
            case 1:
                data.enforceInterface(descriptor);
                if (0 != data.readInt()) {
                    _arg0 = (TestData)TestData.CREATOR.createFromParcel(data);
                } else {
                    _arg0 = null;
                }

                this.sendTest(_arg0);
                reply.writeNoException();
                return true;
            case 2:
                data.enforceInterface(descriptor);
                _arg0 = new TestData();
                int _arg1 = data.readInt();
                this.sendTest2(_arg0, _arg1);
                reply.writeNoException();
                if (_arg0 != null) {
                    reply.writeInt(1);
                    _arg0.writeToParcel(reply, 1);
                } else {
                    reply.writeInt(0);
                }

                return true;
            case 3:
                data.enforceInterface(descriptor);
                if (0 != data.readInt()) {
                    _arg0 = (TestData)TestData.CREATOR.createFromParcel(data);
                } else {
                    _arg0 = null;
                }

                String _arg1 = data.readString();
                this.sendTest3(_arg0, _arg1);
                reply.writeNoException();
                if (_arg0 != null) {
                    reply.writeInt(1);
                    _arg0.writeToParcel(reply, 1);
                } else {
                    reply.writeInt(0);
                }

                return true;
            case 1598968902:
                reply.writeString(descriptor);
                return true;
            default:
                return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ITestInterface {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "com.kubo.aidlproject.ITestInterface";
            }

            public void sendTest(TestData testData) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();

                try {
                    _data.writeInterfaceToken("com.kubo.aidlproject.ITestInterface");
                    if (testData != null) {
                        _data.writeInt(1);
                        testData.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }

                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }

            }

            public void sendTest2(TestData testData, int a) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();

                try {
                    _data.writeInterfaceToken("com.kubo.aidlproject.ITestInterface");
                    _data.writeInt(a);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    if (0 != _reply.readInt()) {
                        testData.readFromParcel(_reply);
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }

            }

            public void sendTest3(TestData testData, String a) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();

                try {
                    _data.writeInterfaceToken("com.kubo.aidlproject.ITestInterface");
                    if (testData != null) {
                        _data.writeInt(1);
                        testData.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }

                    _data.writeString(a);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    if (0 != _reply.readInt()) {
                        testData.readFromParcel(_reply);
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }

            }
        }
    }
}

```

#### 创建服务

如上完成了aidl的创建后还需要创建提供服务的服务类，从而实现提供服务的目的，如下:

```
public class AIDLService extends Service {
    private static final String TAG = "AIDLService";
    ITestAidlStub stub = new ITestAidlStub();
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG,"-----------创建AIDL服务-------------");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG,"----------------onStartCommand-----------------");
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        Log.e(TAG,"----------------------onDestroy---------------");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG,"----------------------onBind---------------");
        return stub;
    }
}

public class ITestAidlStub extends ITestInterface.Stub {
    private static final String TAG = "ITestAidlStub";


    @Override
    public void sendTest(TestData testData) throws RemoteException {
        Log.e(TAG,"sendTest,testData is in:"+testData.toString());
        testData.setName("test_service");

    }

    @Override
    public void sendTest2(TestData testData, int a) throws RemoteException {
        Log.e(TAG,"sendTest,testData is out:"+testData.toString());
        testData.setName("test_service");

    }

    @Override
    public void sendTest3(TestData testData, String a) throws RemoteException {
        Log.e(TAG,"sendTest,testData is inout:"+testData.toString());
        testData.setName("test_service");

    }
}

```

完成服务的编写还得需要在manifest中注册该服务:

```
    <service android:name=".AIDLService"
            android:exported="true"
            android:process=":test"/>
```

由于本次是为了测试多进程通信，且是在同一个app中，所以就需要我们为这个服务单独开一个进程


#### 请求服务
服务创建好后，我们就可以在我们app的主进程中创建一个服务连接并通过bindService绑定服务了。

```
public class MainActivity extends AppCompatActivity {

    ITestInterface iTestInterface;

    /**
     * 连接服务
     */
    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iTestInterface = ITestAidlStub.asInterface(service);
            Log.e("MainActivity","onServiceConnected");

        }

        /**
         * service异常终止
         * @param name
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {
            iTestInterface = null;
            Log.e("MainActivity","onServiceDisconnected");

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onClick(View view){
        TestData testData = new TestData(0,"hfcai","注意提示！",true);
        switch (view.getId()){
            case R.id.bind:
                //绑定服务
               Intent intent = new Intent(this,AIDLService.class);
               bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);
                break;

            case R.id.send_in:
                //发送测试数据
                if (iTestInterface!=null){
                    try {
                        iTestInterface.sendTest(testData);
                        Log.e("in","testData:"+testData.toString());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        Log.e("in error",e.getMessage());
                    }
                }else {
                    Toast.makeText(this,"连接服务失败！",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.send_out:
                //发送测试数据
                if (iTestInterface!=null){

                    try {
                        iTestInterface.sendTest2(testData,0);
                        Log.e("out","testData:"+testData.toString());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        Log.e("out error",e.getMessage());
                    }
                }else {
                    Toast.makeText(this,"连接服务失败！",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.send_inout:
                //发送测试数据
                if (iTestInterface!=null){
                    try {
                        iTestInterface.sendTest3(testData,"inout");
                        Log.e("inout","testData:"+testData.toString());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        Log.e("inout error",e.getMessage());
                    }
                }else {
                    Toast.makeText(this,"连接服务失败！",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
```

这样就完成了一个
