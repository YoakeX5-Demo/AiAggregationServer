package com.example.aggregationserver.entity

import jakarta.persistence.*

@Entity // orm 规则，即 class 名即数据库表中的表名，字段名即表中的字段名
@Table(name = "user")
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    val userId: Int? = null

    @Column(name = "user_name")
    var userName: String? = null

    @Column(name = "user_password")
    var userPassword: String? = null

}
