package com.example.aggregationserver.dao

import com.example.aggregationserver.entity.ChatHistorical
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ChatHistoricalDao : JpaRepository<ChatHistorical, Any> {  // 聊天历史数据类
    // 用户ID查询聊天历史内容
    @Query(value = "select * from chat_historical where user_id = ?1 ORDER BY time DESC ", nativeQuery = true)
    fun findByUserIdData(id: Int): List<Map<Any, Any>>
}