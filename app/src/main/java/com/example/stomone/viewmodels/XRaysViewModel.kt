package com.example.stomone.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.stomone.App
import com.example.stomone.R
import com.example.stomone.SingleLiveEvent
import com.example.stomone.jsonMy.NumberXRaysJS
import com.example.stomone.jsonMy.PatientUIjs
import com.example.stomone.recyclerXRays.XRaysItem
import com.example.stomone.room.LoginDatabase
import com.example.stomone.room.LoginRepository
import com.example.stomone.room.RXRays
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import javax.inject.Inject


class XRaysViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    private var _toastMessage = SingleLiveEvent<String>()
    val toastMessage: LiveData<String> get() = _toastMessage

    private var _viewPhoto = SingleLiveEvent<Bitmap>()
    val viewPhoto: LiveData<Bitmap> get() = _viewPhoto

    private var _booleanAnimation = SingleLiveEvent<Boolean>()
    val booleanAnimation: LiveData<Boolean> get() = _booleanAnimation

    val readAllXRaysLiveData: LiveData<List<RXRays>>
    private val repository: LoginRepository

    init {
        val loginDao = LoginDatabase.getLoginDatabase(application).filmDao()
        repository = LoginRepository(loginDao)
        readAllXRaysLiveData = repository.readAllXRaysLiveData
    }

    fun isAnimation(bool: Boolean) {
        _booleanAnimation.postValue(bool)
    }


    @SuppressLint("CheckResult")
    fun loadXRaysList(patientUI: String, requireContext: android.content.Context) {

        try {
            Observable.just(repository.readAllXRays())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe({ result ->
                    if (result.isEmpty()) {
                        loadInformationIsServer(patientUI, requireContext)
                    } else {
                        _booleanAnimation.postValue(false)
                    }
                }, { error ->
                    _booleanAnimation.postValue(false)
                })
        } catch (e: IOException) {
            e.printStackTrace()
            _toastMessage.postValue(e.toString())
        }

    }

    @SuppressLint("CheckResult")
    private fun loadInformationIsServer(
        patientUI: String,
        requireContext: android.content.Context
    ) {
        val JSON = PatientUIjs(patientUI)
        App.instance.api.patientXRaysRequest(JSON)
            .subscribeOn(Schedulers.io())
            .doOnError {
                _booleanAnimation.postValue(false)
            }
            .subscribeOn(Schedulers.newThread())
            .subscribe(
                { result ->
                    result.forEach { items ->
                        repository.addRXrays(
                            RXRays(
                                0,
                                items.datePhoto,
                                items.numberDirection,
                                items.typeOfResearch,
                                items.financing,
                                items.teeth,
                                items.doctor
                            )
                        )
                    }
                    _booleanAnimation.postValue(false)
                },
                { error ->
                    _booleanAnimation.postValue(false)
                    _toastMessage.postValue(requireContext.resources?.getString(R.string.template_x_rays_no_pictures))
                }
            )
    }

    @SuppressLint("CheckResult")
    fun loadImage(xRaysItem: XRaysItem, position: Int) {
        val JSON = NumberXRaysJS(xRaysItem.numberDirection)
        _booleanAnimation.postValue(true)
        App.instance.api.patientLoaderPhotoXRays(JSON)
            .subscribeOn(Schedulers.io())
            .doOnError {
                _booleanAnimation.postValue(false)
                _toastMessage.postValue(it.toString())
            }
            .subscribeOn(Schedulers.newThread())
            .subscribe(
                { result ->
                    _booleanAnimation.postValue(false)
                    var bmp1: Bitmap? =
                        BitmapFactory.decodeStream(result.byteStream())
                    bmp1?.let {
                        _viewPhoto.postValue(it)
                    }
                },
                { error ->
                    _booleanAnimation.postValue(false)
                    _toastMessage.postValue(error.toString())
                }
            )
    }

}