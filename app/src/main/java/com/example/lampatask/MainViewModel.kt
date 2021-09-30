package com.example.lampatask

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val contentRepository = ContentRepository()


    private val storiesData: MutableLiveData<List<Content>> = MutableLiveData(ArrayList())
    private val videoData: MutableLiveData<List<Content>> = MutableLiveData(ArrayList())
    private val favouritesData: MutableLiveData<List<Content>> = MutableLiveData(ArrayList())

    init {

    }

    fun loadData(): LiveData<Outcome<Boolean>> {
        val liveData = MutableLiveData<Outcome<Boolean>>(Outcome.loading(true))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val contents = contentRepository.loadData()
                Log.d("DEV_TAG", "size: ${contents.size}")
                storiesData.postValue(contents.filter { it.type == ContentType.STRORIES.getServerName() })
                videoData.postValue(contents.filter { it.type == ContentType.VIDEO.getServerName() })
                favouritesData.postValue(contents.filter { it.type == ContentType.FAVOURITES.getServerName() })
                liveData.postValue(Outcome.success(true))
            } catch (t: Throwable) {
                t.printStackTrace()
                liveData.postValue(Outcome.failure(t))
            }
        }

        return liveData
    }


    fun getContent(contentType: ContentType): LiveData<List<Content>> {
        return when (contentType) {
            ContentType.STRORIES -> storiesData
            ContentType.VIDEO -> videoData
            ContentType.FAVOURITES -> favouritesData
        }
    }

}