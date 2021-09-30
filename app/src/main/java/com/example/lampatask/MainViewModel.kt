package com.example.lampatask

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val contentRepository = ContentRepository()

    private val fullLiveData: MutableLiveData<List<Content>> = MutableLiveData(ArrayList())

    init {

    }

    fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val contents = contentRepository.loadData()
                fullLiveData.postValue(contents)
            } catch (t: Throwable) {
                t.printStackTrace()
            }
        }
    }


    fun getContent(): LiveData<List<Content>> {
//        val liveData = MutableLiveData<List<Content>>(contentRepository.getData())

        return fullLiveData
    }

}