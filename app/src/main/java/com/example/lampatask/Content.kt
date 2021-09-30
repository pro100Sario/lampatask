package com.example.lampatask

import com.google.gson.annotations.SerializedName

data class Content(
    @SerializedName("title") val title: String = "",
    @SerializedName("type") val type: String = "",
    @SerializedName("img") val img: String = "",
    @SerializedName("click_url") val clickUrl: String = "",
    @SerializedName("time") val time: String = "",
    @SerializedName("top") val top: String = ""
) {



}