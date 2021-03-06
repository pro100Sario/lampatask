package com.example.lampatask

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Content(
    @SerializedName("title") val title: String = "",
    @SerializedName("type") val type: String = "",
    @SerializedName("img") val img: String = "",
    @SerializedName("click_url") val clickUrl: String = "",
    @SerializedName("url") val url: String = "",
    @SerializedName("time") val time: String = "",
    @SerializedName("top") val top: String = ""
) : Serializable {

    fun isTopNews(): Boolean {
        return top == "1"
    }


    fun getUrlForOpen(): String {
        return when {
            clickUrl.isNotBlank() -> {
                clickUrl
            }
            url.isNotBlank() -> {
                url
            }
            else -> ""
        }
    }

}