package com.example.githab_user.data

data class itemDetail(
    val username:String,
    val id: Int,
    val avatar_url:String,
    val followers_url:String,
    val following_url:String,
    val name:String,
    val followers:Int,
    val following:Int,
    val public_repos:Int,
    val bio:String
        )