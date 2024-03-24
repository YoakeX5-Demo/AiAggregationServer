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
        val userID: Int = StpUtil.getLoginId().toString().toInt()
        println(">> 收到用户ID：$userID 的请求")
        println(">> 获取本地接口池：http://127.0.0.1:22022")
        println(">> 请求本地接口: http://127.0.0.1:22022")
        println(">> 请求AI: ChatGLM（模型版本ChatGLM3-6B）")
        println(">> 请求问题: $problem")
        // 请求体
        val body = """
                {
                    "prompt": "问题",
                    "history": [],
                    "max_length": 2048,
                    "top_p": 0.7,
                    "temperature": 0.95
                }
                """.trimIndent()
        // 构造并发送请求
        val result: String =
            HttpRequest.post("http://127.0.0.1:22022").body(body.replace("问题", problem))
                .timeout(100000) // 超时，毫秒
                .execute().body()
        // 解析回答内容
        val data = JSONUtil.parseObj(result).getStr("response")
        println(">> 回答结果: $data")
        return mapOf(
            "code" to 200, "data" to data
        )
    }

    override fun chatGPT(problem: String): Map<String, Any?> {
        val userID: Int = StpUtil.getLoginId().toString().toInt()
        println(">> 收到用户ID：$userID 的请求")
        println(">> 请求在线接口: https://oa.api2d.net/v1/chat/completions")
        println(">> 请求AI: ChatGPT（模型版本GPT-4）")
        println(">> 请求问题: $problem")
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
                    "model": "gpt-4",
                    "temperature": 0.5,
                    "presence_penalty": 2
                }
                """.trimIndent()
        // 构造并发送请求
        val result: String = HttpRequest.post("https://oa.api2d.net/v1/chat/completions").header(
            "Authorization", "Bearer fk209687-pq6tcrxG926schMw8L7HvLBaH7xKuiS3"
        ).body(body.replace("问题", problem)).timeout(100000) // 超时，毫秒
            .execute().body()
        // 解析回答内容
        val data = JSONUtil.parseObj(result).getJSONArray("choices").getJSONObject(0).getJSONObject("message")
            .getStr("content")
        println(">> 回答结果: $data")
        return mapOf(
            "code" to 200,
            "data" to data,
        )
    }

    override fun eRNIE(problem: String): Map<String, Any?> {
        val userID: Int = StpUtil.getLoginId().toString().toInt()
        println(">> 收到用户ID：$userID 的请求")
        println(">> 请求在线接口: https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/completions_pro")
        println(">> 请求AI: 文心一言（模型版本ERNIE-4.0）")
        println(">> 请求问题: $problem")
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
                    "model": "gpt-4",
                    "temperature": 0.5,
                    "presence_penalty": 2
                }
                """.trimIndent()
        // 构造并发送请求
        val result: String = HttpRequest.post("https://oa.api2d.net/v1/chat/completions").header(
            "Authorization", "Bearer XXXXXXXXXXX"
        ).body(body.replace("问题", problem)).timeout(100000) // 超时，毫秒
            .execute().body()
        // 解析回答内容
        var data = JSONUtil.parseObj(result).getJSONArray("choices").getJSONObject(0).getJSONObject("message")
            .getStr("content")
        data = data.replace("```", "")
        data = data.replace("markdown", "")
        println(">> 回答结果: $data")
        return mapOf(
            "code" to 200,
            "data" to data,
        )
    }

    override fun sD(problem: String): Map<String, Any?> {
        println(">> 获取本地接口池：http://127.0.0.1:22023")
        println(">> 请求本地接口: http://127.0.0.1:22023")
        println(">> 请求AI: Stable Diffusion（模型版本AstraMix2）")
        println(">> 请求问题: $problem")

        // 请求体
        val body = """
            {
                "prompt": "问题",
                "negative_prompt": "paintings, sketches, (worst quality:2), (low quality:2), (normal quality:2), lowres, normal quality, ((monochrome)), ((grayscale)), skin spots, acnes, skin blemishes, age spot, (outdoor:1.6), manboobs, backlight,(ugly:1.331), (duplicate:1.331), (morbid:1.21), (mutilated:1.21), (tranny:1.331), mutated hands, (poorly drawn hands:1.331), blurry, (bad anatomy:1.21), (bad proportions:1.331), extra limbs, (disfigured:1.331), (more than 2 nipples:1.331), (missing arms:1.331), (extra legs:1.331), (fused fingers:1.61051), (too many fingers:1.61051), (unclear eyes:1.331), bad hands, missing fingers, extra digit, (futa:1.1), bad body, NG_DeepNegative_V1_75T,pubic hair, glans(bad-artist:0.7)",
                "sampler_index": "DPM++ SDE",
                "seed": -1,
                "steps": 30,
                "width": 512,
                "height": 512,
                "cfg_scale": 7,
                "override_settings": {"sd_model_checkpoint": "AstraMix2.safetensors.ckpt [04f7a3de24]"}
            }
        """.trimIndent()
        val result: String =
            HttpRequest.post("http://127.0.0.1:22022/sd").body(body.replace("问题", problem))
                .timeout(100000) // 超时，毫秒
                .execute().body()
        // 解析回答内容
        var data = JSONUtil.parseObj(result)["images"]
        data = data.toString().substring(2, data.toString().length - 2)
        println(">> 回答结果: Base64编码")
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
