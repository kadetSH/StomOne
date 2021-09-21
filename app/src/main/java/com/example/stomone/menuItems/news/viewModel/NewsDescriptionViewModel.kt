package com.example.stomone.menuItems.news.viewModel

import android.annotation.SuppressLint
import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.stomone.App
import com.example.stomone.SingleLiveEvent
import com.example.stomone.room.news.NewsDao
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewsDescriptionViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    private var _viewPhoto = SingleLiveEvent<Bitmap>()
    val viewPhoto: LiveData<Bitmap> get() = _viewPhoto

    private val interactor = App.instance.newsDescriptionInteractor

    @SuppressLint("CheckResult")
    fun loadImage(imagePath: String){
        interactor.loadImage(imagePath)
            .subscribeOn(Schedulers.io())
            .doOnError {
//                _booleanAnimation.postValue(false)
//                _toastMessage.postValue(it.toString())
                println("")
            }
            .subscribe(
                { result ->
//                    _booleanAnimation.postValue(false)
                    val bmp1: Bitmap? =
                        BitmapFactory.decodeStream(result.byteStream())
                    bmp1?.let {
                        _viewPhoto.postValue(it)
                    }
                },
                { error ->
//                    _booleanAnimation.postValue(false)
//                    _toastMessage.postValue(error.toString())
                    println("")
                }
            )
    }



}