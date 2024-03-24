package com.example.aggregationserver.exception

import cn.dev33.satoken.exception.NotLoginException
import cn.dev33.satoken.util.SaResult
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class NotLoginException {
    // 全局异常拦截（拦截项目中的NotLoginException异常）
    @ExceptionHandler(NotLoginException::class)
    @Throws(Exception::class)
    fun handlerNotLoginException(nle: NotLoginException): SaResult? {

        // 打印堆栈，以供调试
        nle.printStackTrace()
        // 判断场景值，定制化异常信息
        val message: String = when (nle.type) {
            NotLoginException.NOT_TOKEN -> {
                "未能读取到有效 token，请在首页登录"
            }

            NotLoginException.INVALID_TOKEN -> {
                "token 无效，请重新登录"
            }

            NotLoginException.TOKEN_TIMEOUT -> {
                "token 已过期，请重新登录"
            }

            NotLoginException.BE_REPLACED -> {
                "token 已被顶下线，请重新登录"
            }

            NotLoginException.KICK_OUT -> {
                "token 已被踢下线，请重新登录"
            }

            NotLoginException.TOKEN_FREEZE -> {
                "token 已被冻结"
            }

            NotLoginException.NO_PREFIX -> {
                "未按照指定前缀提交 token"
            }

            else -> {
                "当前会话未登录"
            }
        }

        // 返回给前端
        return SaResult.error(message)
    }
}