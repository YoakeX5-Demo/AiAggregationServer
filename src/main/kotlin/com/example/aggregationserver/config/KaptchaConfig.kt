package com.example.aggregationserver.config

import com.google.code.kaptcha.impl.DefaultKaptcha
import com.google.code.kaptcha.util.Config
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*


@Configuration
class KaptchaConfig {
    @Bean
    fun producer(): DefaultKaptcha {
        val properties = Properties()
        properties["kaptcha.border"] = "no"
        properties["kaptcha.textproducer.font.color"] = "black"
        properties["kaptcha.textproducer.char.space"] = "4"
        properties["kaptcha.image.height"] = "40"
        properties["kaptcha.image.width"] = "120"
        properties["kaptcha.textproducer.font.size"] = "30"
        val config = Config(properties)
        val defaultKaptcha = DefaultKaptcha()
        defaultKaptcha.config = config
        return defaultKaptcha
    }
}

