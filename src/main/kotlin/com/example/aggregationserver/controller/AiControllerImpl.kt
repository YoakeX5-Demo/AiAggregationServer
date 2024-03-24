package com.example.aggregationserver.controller

import com.example.aggregationserver.service.AiServer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/ai")
class AiControllerImpl : AiController {  // 用户控制器实现类
    // 注入AI服务
    @Autowired
    @Qualifier("aiServerImpl")
    lateinit var aiServer: AiServer

    // 获取回答
    @RequestMapping("/getResponse")
    @ResponseBody
    override fun getResponse(@RequestParam itemId: Int, aiId: Int, problem: String): Map<String, Any?> {
        val result: Map<String, Any?>
        when (aiId) {
            0 -> { // 选择ChatGLM
                // 调用ChatGLM获取回答
                result = aiServer.chatGLM(problem)
            }

            1 -> { // 选择ChatGPT
                // 调用ChatGPT获取回答
                result = aiServer.chatGPT(problem)
            }

            3 -> { // 选择SD
                // 调用SD获取回答
                result = aiServer.sD(problem)
            }

            else -> {
                return mapOf("code" to 400, "msg" to "选择错误")
            }
        }
        // 记录回答
        aiServer.saveChatHistorical(itemId, aiId, problem, result["data"].toString())
        return result
    }
}