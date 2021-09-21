package com.example.stomone.menuItems.news.viewModel

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.stomone.App
import com.example.stomone.SingleLiveEvent
import com.example.stomone.room.news.NewsDao
import com.example.stomone.room.news.RNews
import com.example.stomone.room.picturesVisit.PicturesVisitDao
import com.example.stomone.room.picturesVisit.RPicturesVisit
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsViewModel @Inject constructor(application: Application, private val newsDao: NewsDao) :
    AndroidViewModel(application) {

    private var _toastMessage = SingleLiveEvent<String>()
    val toastMessage: LiveData<String> get() = _toastMessage

    private var _booleanAnimation = SingleLiveEvent<Boolean>()
    val booleanAnimation: LiveData<Boolean> get() = _booleanAnimation

    val readAllNewsLiveData: LiveData<List<RNews>> = newsDao.readAllNewsLiveData()
    private val interactor = App.instance.newsInteractor

    fun isAnimation(bool: Boolean) {
        _booleanAnimation.postValue(bool)
    }

    @SuppressLint("CheckResult")
    fun getNews() {

        val listNews = interactor.getNews()
        listNews
            ?.subscribeOn(Schedulers.io())
            ?.doOnError {
                _booleanAnimation.postValue(false)
                _toastMessage.postValue(it.toString())
            }
            ?.subscribe(
                { result ->
                    result.forEach { item ->
                        interactor.addNewsItem(RNews(0, item.title, item.content, item.imagePath))
                    }
                    _booleanAnimation.postValue(false)
                },
                { error ->
                    _booleanAnimation.postValue(false)
                    _toastMessage.postValue(error.toString())
                }
            )
        if (listNews == null) _booleanAnimation.postValue(false)
    }

}