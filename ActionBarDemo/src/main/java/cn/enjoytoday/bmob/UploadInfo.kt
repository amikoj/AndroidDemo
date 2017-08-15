package cn.enjoytoday.bmob

import cn.bmob.v3.BmobObject

/**
 *上传数据类.
 */
class UploadInfo:BmobObject() {
    var userId:String?=null
    var recordNumber:Int?=null
    var databaseUrl:String?=null
    var fileName:String?=null

}