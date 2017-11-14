package cn.enjoytoday.rimedoj.callbacks

/**
 * @date 17-11-14.
 * @className RequestCallback
 * @serial 1.0.0
 */
interface RequestCallback:Callback {

    /**
     * request success
     */
    fun onSuccess(response:String)


    /**
     * request failed
     */
    fun onFailed(code:Int,errmsg:String?)
}