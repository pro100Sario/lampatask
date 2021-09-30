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

    private val topStoriesData: MutableLiveData<List<Content>> = MutableLiveData(ArrayList())
    private val topVideoData: MutableLiveData<List<Content>> = MutableLiveData(ArrayList())
    private val topFavouritesData: MutableLiveData<List<Content>> = MutableLiveData(ArrayList())

    init {

    }

    fun loadData(): LiveData<Outcome<Boolean>> {
        val liveData = MutableLiveData<Outcome<Boolean>>(Outcome.loading(true))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val contents = contentRepository.loadData()
                Log.d("DEV_TAG", "size: ${contents.size}")

                val stories = contents.filter { it.type == ContentType.STRORIES.getServerName() }
                storiesData.postValue(stories)
                val videos = contents.filter { it.type == ContentType.VIDEO.getServerName() }
                videoData.postValue(videos)
                val favourites = contents.filter { it.type == ContentType.FAVOURITES.getServerName() }
                favouritesData.postValue(favourites)

                topStoriesData.postValue(stories.filter { it.isTopNews() })
                topVideoData.postValue(videos.filter { it.isTopNews() })
                topFavouritesData.postValue(favourites.filter { it.isTopNews() })

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

    fun getTopNews(contentType: ContentType): LiveData<List<Content>> {
        return when (contentType) {
            ContentType.STRORIES -> topStoriesData
            ContentType.VIDEO -> topVideoData
            ContentType.FAVOURITES -> topFavouritesData
        }
    }

}