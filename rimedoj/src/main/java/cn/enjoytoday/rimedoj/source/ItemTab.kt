package cn.enjoytoday.rimedoj.source

/**
 * @date 17-11-10.
 * @className ItemTab
 * @serial 1.0.0
 *
 *
 * tab中子目录下的数据集合,需要动态可扩展,故此需要存在异同
 */
class ItemTab {

    /**
     * 单个子目录访问采用html加载,不再对其进行本地定制化格式
     */
    var infoUrl:String?=null

    /**
     * 位置
     */
    var position:Int=0

    /**
     * 所属父Tab
     */
    var parent:DataTabSource?=null


    /**
     * 参数对照表,将viewtype对应控件和获取数据集合数据对应采用ids对应数据key格式对应,如下:
     *
     * datasources:{"name":"hfcai"}
     * viewtype
     *  <p>
     *    <?xml version="1.0" encoding="utf-8"?>
     *    <FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
     *          android:layout_width="match_parent"
     *          android:layout_height="wrap_content">
     *
     *         <TextView
     *              android:id="@+id/text_view"
     *              android:layout_width="match_parent"
     *              android:layout_height="wrap_content"/>
     *
     *     </FrameLayout>
     * </p>
     *
     * 对应格式如下:
     *        {"R.id.text_view":"name"}
     *
     *
     * 若格式为list类型,对应数据源位置:
     *       datasources:{"name","hfcai"}
     * 格式如下:
     *       {"R.id.text_view":"0"}
     *
     *注意:需要与数据源类型对应
     *
     */
    var paramsMap : MutableMap<Int,String> = mutableMapOf()


    /**
     * 无参构造函数防止访问
     */
    private constructor(){
        TODO("must not create according to empty params,all description  see  constructor(position:Int,infoUrl:String,parent:DataTabSource)")
    }


    /**
     * 基本共有构造函数
     */
    constructor(position:Int,infoUrl:String?,parent:DataTabSource){
        this.position=position
        this.infoUrl=infoUrl
        this.parent=parent
    }


}