package com.example.aggregationserver.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import java.util.*

interface UserController { // 用户控制器接口类
    /** 用户注册
     * @param userName 用户名
     * @param userPwd 用户密码
     * @return json对象 **/
    fun register(@RequestParam userName: String, userPwd: String): LinkedHashMap<Any, Any>

    /** 用户登录
     * @param userName 用户名
     * @param userPwd 用户密码
     * @return json对象 **/
    fun login(@RequestParam userName: String, userPwd: String): LinkedHashMap<Any, Any>

    /** 用户登录状态
     * @return json对象 **/
    @RequestMapping("/isLogin")
    fun isLogin(): LinkedHashMap<Any, Any>

    /** 用户注销
     * @return json对象 **/
    @RequestMapping("/logout")
    fun logout(): LinkedHashMap<Any, Any>

    /** 用户Token
     * @return json对象 **/
    @RequestMapping("/token")
    fun token(): LinkedHashMap<Any, Any>

    /** 用户历史消息
     * @return json对象 **/
    @RequestMapping("/chatHistorical")
    @ResponseBody
    fun chatHistorical(): LinkedHashMap<Any, Any>

}
