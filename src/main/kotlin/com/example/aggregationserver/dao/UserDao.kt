package com.example.aggregationserver.dao

import com.example.aggregationserver.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserDao : JpaRepository<User, Long> {  // 聊天历史数据类
    // 根据用户名查询用户数据
    fun findByUserName(userName: String): List<User>
}