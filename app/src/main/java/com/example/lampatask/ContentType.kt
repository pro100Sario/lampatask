package com.example.lampatask

enum class ContentType(private val serverName: String) {
    STRORIES(serverName = "strories"),
    VIDEO(serverName = "favourites"),
    FAVOURITES(serverName = "video");


    fun getServerName(): String = serverName

}