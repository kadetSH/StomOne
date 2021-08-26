package com.example.stomone.menuItems.xrays.viewModel

import android.annotation.SuppressLint
import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.stomone.App
import com.example.stomone.R
import com.example.stomone.SingleLiveEvent
import com.example.stomone.dagger.retrofit.repository.RetrofitServiceInterfaceXRays
import com.example.stomone.menuItems.xrays.recyclerXRays.XRaysItem
import com.example.stomone.room.xrays.RXRays
import com.example.stomone.room.xrays.XRaysDao
import io.reactivex.schedulers.Schedulers
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
    private val interactor = App.instance.xRaysInteractor

    fun isAnimation(bool: Boolean) {
        _booleanAnimation.postValue(bool)
    }

    @SuppressLint("CheckResult")
    fun getXRaysList(patientUI: String, requireContext: android.content.Context) {
        val listXRays = interactor.getXRaysList(patientUI)
        listXRays
            ?.subscribeOn(Schedulers.io())
            ?.doOnError {
                _booleanAnimation.postValue(false)
            }
            ?.subscribe(
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
        if (listXRays == null) {
            _booleanAnimation.postValue(false)
        }
    }

    @SuppressLint("CheckResult")
    fun loadImage(xRaysItem: XRaysItem) {
        _booleanAnimation.postValue(true)

        interactor.loadImage(xRaysItem.numberDirection)
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