package com.hfcai.fileprovider;



import org.junit.Test;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {


    @Test
    public void addition_isCorrect() throws Exception {
        ClassLoader loader=ClassLoader.class.getClassLoader();
        System.out.println("addition_isCorrect");

        while (loader!=null){

            System.out.println(loader.toString());
            loader=loader.getParent();
        }






//        assertEquals(4, 2 + 2);
    }
}