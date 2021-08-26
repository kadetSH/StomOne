package com.example.stomone.menuItems.picturesVisit.viewModel

import android.annotation.SuppressLint
import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.stomone.App
import com.example.stomone.SingleLiveEvent
import com.example.stomone.dagger.retrofit.repository.RetrofitServiceInterfacePicturesVisit
import com.example.stomone.jsonMy.NumberPicturesVisitJS
import com.example.stomone.jsonMy.PatientUIjs
import com.example.stomone.menuItems.picturesVisit.recyclerPicturesVisit.PicturesVisitItem
import com.example.stomone.room.picturesVisit.PicturesVisitDao
import com.example.stomone.room.picturesVisit.RPicturesVisit
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import javax.inject.Inject

@Suppress("LocalVariableName")
class PicturesVisitViewModel @Inject constructor(application: Application, private val picturesVisitDao: PicturesVisitDao) :
    AndroidViewModel(application) {

    private var _toastMessage = SingleLiveEvent<String>()
    val toastMessage: LiveData<String> get() = _toastMessage

    private var _viewPhoto = SingleLiveEvent<Bitmap>()
    val viewPhoto: LiveData<Bitmap> get() = _viewPhoto

    private var _booleanAnimation = SingleLiveEvent<Boolean>()
    val booleanAnimation: LiveData<Boolean> get() = _booleanAnimation

    val readAllPicturesVisitLiveData: LiveData<List<RPicturesVisit>> = picturesVisitDao.readAllPicturesVisitLiveData()
    private val interactor = App.instance.picturesVisitInteractor
    fun isAnimation(bool: Boolean) {
        _booleanAnimation.postValue(bool)
    }

    @SuppressLint("CheckResult")
    fun getPicturesVisitList(patientUI: String){
        val listPicturesVisit = interactor.getPicturesVisitList(patientUI)
        listPicturesVisit
            ?.subscribeOn(Schedulers.io())
            ?.doOnError {
                _booleanAnimation.postValue(false)
                _toastMessage.postValue(it.toString())
            }
            ?.subscribe(
                { result ->
                    result.forEach { items ->
                        picturesVisitDao.addPicturesVisit(RPicturesVisit(0, items.dateOfReceipt, items.numberPicture, items.doctor))
                    }
                    _booleanAnimation.postValue(false)
                },
                { error ->
                    _booleanAnimation.postValue(false)
                    _toastMessage.postValue(error.toString())
                }
            )
        if (listPicturesVisit == null){
            _booleanAnimation.postValue(false)
        }
    }

    @SuppressLint("CheckResult")
    fun loadPicturesVisit(itemPictures: PicturesVisitItem){
        _booleanAnimation.postValue(true)
        interactor.loadPicturesVisit(itemPictures.numberPicture)
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