package com.example.aggregationserver.service


interface AiServer { // AI服务接口
    /** 调用ChatGLM
     * @param problem 问题
     * **/
    fun chatGLM(problem: String): Map<String, Any?>

    /** 调用ChatGPT
     * @param problem 问题
     * **/
    fun chatGPT(problem: String): Map<String, Any?>

    /** 调用ERNIE
     * @param problem 问题
     * **/
    fun eRNIE(problem: String): Map<String, Any?>

    /** 调用SD
     * @param problem 问题
     * **/
    fun sD(problem: String): Map<String, Any?>

    /** 保存聊天历史
     * @param itemId 集合ID
     * @param aiId AI ID
     * @param problem 问题
     * @param response 回答
     * **/
    fun saveChatHistorical(itemId: Int, aiId: Int, problem: String, response: String)
}
