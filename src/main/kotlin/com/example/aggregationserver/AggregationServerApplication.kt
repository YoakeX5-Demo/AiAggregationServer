package com.example.aggregationserver

import cn.dev33.satoken.SaManager
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AggregationServerApplication

fun main(args: Array<String>) {
    runApplication<AggregationServerApplication>(*args)
    println("启动成功，Sa-Token 配置如下：" + SaManager.getConfig())
}
