package com.example.aggregationserver.service

import cn.dev33.satoken.stp.StpUtil
import cn.hutool.http.HttpRequest
import cn.hutool.json.JSONUtil
import com.example.aggregationserver.dao.ChatHistoricalDao
import com.example.aggregationserver.entity.ChatHistorical
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.util.*


@Service("aiServerImpl")
class AiServerImpl : AiServer { // AI服务实现类

    // 注入用户数据对象
    @Autowired
    lateinit var chatHistoricalDao: ChatHistoricalDao


    override fun chatGLM(problem: String): Map<String, Any?> {
        // 请求体
        val body = """
                {
                    "prompt": "请用markdown格式回复。问题",
                    "history": [],
                    "max_length": 2048,
                    "top_p": 0.7,
                    "temperature": 0.95
                }
                """.trimIndent()
        // 构造并发送请求
        val result: String =
            HttpRequest.post("http://s0.i-mc.cn:22022").body(body.replace("问题", problem)).timeout(20000) // 超时，毫秒
                .execute().body()
        // 解析回答内容
        val data = JSONUtil.parseObj(result).getStr("response")
        return mapOf(
            "code" to 200, "data" to data
        )
    }

    override fun chatGPT(problem: String): Map<String, Any?> {
        // 选择ChatGPT
        // 请求体
        val body = """
                {
                    "messages": [
                        {
                            "role": "user",
                            "content": "请用markdown格式回复。问题"
                        }
                    ],
                    "stream": false,
                    "model": "gpt-3.5-turbo",
                    "temperature": 0.5,
                    "presence_penalty": 2
                }
                """.trimIndent()
        // 构造并发送请求
        val result: String = HttpRequest.post("https://oa.api2d.net/v1/chat/completions").header(
            "Authorization", "Bearer fk209687-pq6tcrxG926schMw8L7HvLBaH7xKuiS3"
        ).body(body.replace("问题", problem)).timeout(20000) // 超时，毫秒
            .execute().body()
        // 解析回答内容
        val data = JSONUtil.parseObj(result).getJSONArray("choices").getJSONObject(0).getJSONObject("message")
            .getStr("content")

        return mapOf(
            "code" to 200,
            "data" to data,
        )
    }

    override fun sD(problem: String): Map<String, Any?> {
        // 请求体
        val body = """
                    {
                    "prompt": "问题",
                    "negative_prompt": "",
                    "sampler_index": "DPM++ SDE",
                    "seed": 1234,
                    "steps": 20,
                    "width": 512,
                    "height": 512,
                    "cfg_scale": 8
                    }
                """.trimIndent()
        val result: String =
            HttpRequest.post("http://s0.i-mc.cn:22023/sdapi/v1/txt2img").body(body.replace("问题", problem))
                .timeout(20000) // 超时，毫秒
                .execute().body()
        // 解析回答内容
        val data = JSONUtil.parseObj(result)["images"]
        return mapOf(
            "code" to 200, "data" to data
        )
    }

    override fun saveChatHistorical(itemId: Int, aiId: Int, problem: String, response: String) {
        val chatHistorical = ChatHistorical()
        chatHistorical.itemId = itemId
        chatHistorical.userId = StpUtil.getLoginId().toString().toInt()
        chatHistorical.aiId = aiId
        chatHistorical.problem = problem
        chatHistorical.response = response
        chatHistorical.time = Timestamp(System.currentTimeMillis())
        chatHistoricalDao.save(chatHistorical)
    }
}
