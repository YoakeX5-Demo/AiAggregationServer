package com.example.aggregationserver.entity

import jakarta.persistence.*
import java.sql.Timestamp

@Entity // orm 规则，即 class 名即数据库表中的表名，字段名即表中的字段名
@Table(name = "chat_historical")
class ChatHistorical {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    var chatId: Int? = null

    @Column(name = "item_id")
    var itemId: Int? = null

    @Column(name = "ai_id")
    var aiId: Int? = null

    @Column(name = "user_id")
    var userId: Int? = null
    
    @Column(name = "problem")
    var problem: String? = null

    @Column(name = "response")
    var response: String? = null

    @Column(name = "time")
    var time: Timestamp? = null
}
