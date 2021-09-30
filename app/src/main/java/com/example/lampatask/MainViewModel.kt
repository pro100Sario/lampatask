package com.example.lampatask

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    private val mockList = ArrayList<Content>().apply {
        for (i in 0 until 50) {
            add(Content())
        }
    }

    init {

    }

    fun loadData() {

    }


    fun getContent(): MutableLiveData<List<Content>> {
        val liveData = MutableLiveData<List<Content>>(mockList)

        return liveData
    }

}