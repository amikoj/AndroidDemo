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
