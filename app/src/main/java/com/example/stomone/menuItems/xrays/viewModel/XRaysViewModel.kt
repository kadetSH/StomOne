package com.example.stomone.menuItems.xrays.viewModel

import android.annotation.SuppressLint
import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.stomone.R
import com.example.stomone.SingleLiveEvent
import com.example.stomone.dagger.retrofit.repository.RetrofitServiceInterfaceXRays
import com.example.stomone.jsonMy.NumberXRaysJS
import com.example.stomone.jsonMy.PatientUIjs
import com.example.stomone.menuItems.xrays.recyclerXRays.XRaysItem
import com.example.stomone.room.xrays.RXRays
import com.example.stomone.room.xrays.XRaysDao
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import javax.inject.Inject

@Suppress("LocalVariableName")
class XRaysViewModel @Inject constructor(application: Application, private val xRaysDao: XRaysDao) :
    AndroidViewModel(application) {

    @Inject
    lateinit var mServise: RetrofitServiceInterfaceXRays

    private var _toastMessage = SingleLiveEvent<String>()
    val toastMessage: LiveData<String> get() = _toastMessage

    private var _viewPhoto = SingleLiveEvent<Bitmap>()
    val viewPhoto: LiveData<Bitmap> get() = _viewPhoto

    private var _booleanAnimation = SingleLiveEvent<Boolean>()
    val booleanAnimation: LiveData<Boolean> get() = _booleanAnimation

    val readAllXRaysLiveData: LiveData<List<RXRays>> = xRaysDao.readAllRXRaysLiveData()

    fun isAnimation(bool: Boolean) {
        _booleanAnimation.postValue(bool)
    }


    @SuppressLint("CheckResult")
    fun getXRaysList(patientUI: String, requireContext: android.content.Context) {
        try {
            Observable.just(xRaysDao.readAllXRays())
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
        mServise.patientXRaysRequest(JSON)
            .subscribeOn(Schedulers.io())
            .doOnError {
                _booleanAnimation.postValue(false)
            }
            .subscribe(
                { result ->
                    result.forEach { items ->
                        xRaysDao.addRXrays(
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
        mServise.patientLoaderPhotoXRays(JSON)
            .subscribeOn(Schedulers.io())
            .doOnError {
                _booleanAnimation.postValue(false)
                _toastMessage.postValue(it.toString())
            }
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