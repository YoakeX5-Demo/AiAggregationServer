package com.example.aggregationserver.controller

import com.example.aggregationserver.service.UserServer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/user")
class UserControllerImpl : UserController {  // 用户控制器实现类
    // 注入用户服务
    @Autowired
    @Qualifier("userServerImpl")
    lateinit var userServer: UserServer

    // 用户注册
    @RequestMapping("/register")
    override fun register(@RequestParam userName: String, userPwd: String): LinkedHashMap<Any, Any> {
        println("userName: $userName, userPwd: $userPwd")
        return userServer.register(userName, userPwd)
    }

    // 用户登录
    @RequestMapping("/login")
    override fun login(@RequestParam userName: String, userPwd: String): LinkedHashMap<Any, Any> {
        return userServer.login(userName, userPwd)
    }

    // 查询登录状态
    @RequestMapping("/isLogin")
    override fun isLogin(): LinkedHashMap<Any, Any> {
        return userServer.isLogin()
    }

    // 注销登录状态
    @RequestMapping("/logout")
    override fun logout(): LinkedHashMap<Any, Any> {
        return userServer.logout()
    }

    // 查询Token
    @RequestMapping("/token")
    override fun token(): LinkedHashMap<Any, Any> {
        return userServer.token()
    }

    // 查询历史消息
    @RequestMapping("/chatHistorical")
    override fun chatHistorical(): LinkedHashMap<Any, Any> {
        return userServer.chatHistorical()
    }
}