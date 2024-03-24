package com.example.aggregationserver.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody


interface AiController { // AI控制器接口类
    /** AI回答
     * @param itemId 集合ID
     * @param aiId AI ID
     * @param problem 问题
     * @return json对象 **/
    @RequestMapping("/getResponse")
    @ResponseBody
    fun getResponse(@RequestParam itemId: Int, aiId: Int, problem: String): Map<String, Any?>


}
