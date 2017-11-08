package cn.enjoytoday.pluginsapp.beans

/**
 * @date 17-11-8.
 * @className TabType
 * @serial 1.0.0
 * 对于内容进行类别区分,均通过正规开发渠道获取内容
 */
enum class TabType(value:String) {
    NEWS("news"),//新闻类型,如知乎等推送文章
    BLOG("blog"),//博文类型,如CSDN等
    QUESTION("question"),//问答类型,如StackOverFlow等
    SHARE("share");//开源类型,如github等
}