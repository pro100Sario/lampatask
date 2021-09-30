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

    var stories: List<Content> = ArrayList()
    var videos: List<Content> = ArrayList()
    var favourites: List<Content> = ArrayList()


    init {

    }

    fun loadData(): LiveData<Outcome<Boolean>> {
        val liveData = MutableLiveData<Outcome<Boolean>>(Outcome.loading(true))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val contents = contentRepository.loadData()

                stories = contents.filter { it.type == ContentType.STRORIES.getServerName() }
                storiesData.postValue(stories)
                videos = contents.filter { it.type == ContentType.VIDEO.getServerName() }
                videoData.postValue(videos)
                favourites = contents.filter { it.type == ContentType.FAVOURITES.getServerName() }
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

    fun query(query: String) {
        viewModelScope.launch (Dispatchers.IO) {
            if (query.isEmpty()) {
                storiesData.postValue(stories)
                videoData.postValue(videos)
                favouritesData.postValue(favourites)
            } else {
                storiesData.postValue(stories.filter { it.title.contains(query) })
                videoData.postValue(videos.filter { it.title.contains(query) })
                favouritesData.postValue(favourites.filter { it.title.contains(query) })
            }
        }
    }

}