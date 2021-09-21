package com.example.stomone.domain

import com.example.stomone.dagger.retrofit.repository.RetrofitServiceInterfaceNewsDescription
import com.example.stomone.jsonMy.ImagePathJS
import io.reactivex.Observable
import okhttp3.ResponseBody

class NewsDescriptionInteractor(private val mServiseNewsDescriptionInteractor: RetrofitServiceInterfaceNewsDescription) {

    fun loadImage(imagePath: String): Observable<ResponseBody>{
        val json = getImagePathJS(imagePath)
        return mServiseNewsDescriptionInteractor.loadNewsImage(json)
    }

    private fun getImagePathJS(imagePath: String): ImagePathJS {
        return ImagePathJS(imagePath)
    }

}