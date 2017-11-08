package cn.enjoytoday.dynamiclib

/**
 * @date 17-11-6.
 * @className IProxy
 * @serial 1.0.0
 * 代理处理
 */
class IProxy :ICallback {

    override fun callback(): String {
        return "IProxy callback."
    }
}