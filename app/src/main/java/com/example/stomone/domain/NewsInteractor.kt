package com.example.stomone.domain

import com.example.stomone.dagger.retrofit.repository.RetrofitServiceInterfaceNews
import com.example.stomone.jsonMy.JSNews
import com.example.stomone.jsonMy.JSPicturesVisit
import com.example.stomone.room.news.NewsDao
import com.example.stomone.room.news.RNews
import io.reactivex.Observable

class NewsInteractor(
    private val mServiseNewsInteractor: RetrofitServiceInterfaceNews,
    private val newsDao: NewsDao
) {

    fun getNews(): Observable<ArrayList<JSNews>>? {
        val listNews = newsDao.readAllNews()
        if (listNews.isEmpty()) {
            return mServiseNewsInteractor.loaderNewsRequest()
        }
        return null
    }

    fun addNewsItem(item: RNews) {
        newsDao.addNews(item)
    }

}