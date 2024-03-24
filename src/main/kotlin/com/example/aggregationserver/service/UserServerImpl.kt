package com.example.aggregationserver.service

import cn.dev33.satoken.stp.StpUtil
import cn.hutool.crypto.digest.DigestAlgorithm
import cn.hutool.crypto.digest.Digester
import com.example.aggregationserver.dao.ChatHistoricalDao
import com.example.aggregationserver.dao.UserDao
import com.example.aggregationserver.entity.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestParam
import java.util.*


@Service("userServerImpl")
class UserServerImpl : UserServer { // 用户服务实现类
    // 注入用户数据对象
    @Autowired
    lateinit var userDao: UserDao

    // 注入聊天历史数据对象
    @Autowired
    lateinit var chatHistoricalDao: ChatHistoricalDao

    // SHA-256加密工具类
    val sha256 = Digester(DigestAlgorithm.SHA256)

    override fun register(@RequestParam userName: String, userPwd: String): LinkedHashMap<Any, Any> {
        val map = LinkedHashMap<Any, Any>()
        // 判断输入非空
        if (userName == "" || userPwd == "") {
            map["code"] = 400
            map["msg"] = "用户名或密码不能为空"
        }
        // 判断用户名是否存在
        else if (userDao.findByUserName(userName).isNotEmpty()) {
            map["code"] = 400
            map["msg"] = "用户名已存在"
        }
        // 进行注册
        else {
            val user = User()
            user.userName = userName
            user.userPassword = sha256.digestHex(userPwd + "666") // 密码加盐，然后生成SHA-256哈希值存储
            if (userDao.save(user).userId == null) {
                map["code"] = 400
                map["msg"] = "注册失败"
                return map
            } else {
                map["code"] = 200
                map["msg"] = "注册成功"
            }
        }
        return map
    }

    override fun login(@RequestParam userName: String, userPwd: String): LinkedHashMap<Any, Any> {
        val map = LinkedHashMap<Any, Any>()
        // 判断输入非空
        if (userName == "" || userPwd == "") {
            map["code"] = 400
            map["msg"] = "用户名或密码不能为空"
            return map
        }
        // 判断用户名是否存在
        else if (userDao.findByUserName(userName).isEmpty()) {
            map["code"] = 400
            map["msg"] = "用户名不存在"
        }
        // 判断密码对错
        else if (userDao.findByUserName(userName)[0].userPassword != sha256.digestHex(userPwd + "666")) {
            map["code"] = 400
            map["msg"] = "密码错误"
        } else if (userDao.findByUserName(userName)[0].userPassword == sha256.digestHex(userPwd + "666")) {
            map["code"] = 200
            map["msg"] = "登录成功"
            StpUtil.login(userDao.findByUserName(userName)[0].userId.toString())
        }
        return map
    }


    override fun isLogin(): LinkedHashMap<Any, Any> {
        val s = if (StpUtil.isLogin()) "用户已登录" else "用户未登录"
        return linkedMapOf("code" to 200, "msg" to s)
    }

    override fun logout(): LinkedHashMap<Any, Any> {
        StpUtil.logout()
        return linkedMapOf("code" to 200, "msg" to "注销成功")
    }

    override fun token(): LinkedHashMap<Any, Any> {
        val map = LinkedHashMap<Any, Any>()
        val token = if (StpUtil.isLogin()) StpUtil.getTokenInfo().tokenValue else "null"
        map["code"] = 200
        map["msg"] = token
        return map
    }

    override fun chatHistorical(): LinkedHashMap<Any, Any> {
        val list = chatHistoricalDao.findByUserIdData(StpUtil.getLoginIdAsLong().toInt())
        return linkedMapOf("code" to 200, "msg" to "查询成功", "data" to list)
    }

}
