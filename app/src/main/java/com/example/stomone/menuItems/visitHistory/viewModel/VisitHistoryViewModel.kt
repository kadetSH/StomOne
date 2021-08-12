package com.example.stomone.menuItems.visitHistory.viewModel

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.stomone.SingleLiveEvent
import com.example.stomone.dagger.retrofit.repository.RetrofitServiceInterfaceVisitHistory
import com.example.stomone.jsonMy.PatientUIjs
import com.example.stomone.room.visitHistory.RVisitHistory
import com.example.stomone.room.visitHistory.VisitHistoryDao
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import javax.inject.Inject

@Suppress("LocalVariableName")
class VisitHistoryViewModel @Inject constructor(application: Application, private val visitHistoryDao: VisitHistoryDao) :
    AndroidViewModel(application) {

    @Inject
    lateinit var mServise: RetrofitServiceInterfaceVisitHistory

    private var _toastMessage = SingleLiveEvent<String>()
    val toastMessage: LiveData<String> get() = _toastMessage

    private var _booleanAnimation = SingleLiveEvent<Boolean>()
    val booleanAnimation: LiveData<Boolean> get() = _booleanAnimation

    val readAllVisitHistoryLiveData: LiveData<List<RVisitHistory>> = visitHistoryDao.readAllVisitHistoryLiveData()

    fun isAnimation(bool: Boolean) {
        _booleanAnimation.postValue(bool)
    }

    @SuppressLint("CheckResult")
    fun getVisitHistory(patientUI: String) {

        try {
            Observable.just(visitHistoryDao.readAllVisitHistory())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe({ result ->
                    if (result.isEmpty()) {
                        loadInformationIsServer(patientUI)
                    } else {
                        _booleanAnimation.postValue(false)
                    }
                }, { error ->
                    println("")
                    _toastMessage.postValue(error.toString())
                })
        } catch (e: IOException) {
            e.printStackTrace()
            _toastMessage.postValue(e.toString())
        }

    }

    @SuppressLint("CheckResult")
    private fun loadInformationIsServer(patientUI: String) {
        val JSON = PatientUIjs(patientUI)
        mServise.patientVisitHistoryRequest(JSON)
            .subscribeOn(Schedulers.io())
            .doOnError {
                _toastMessage.postValue(it.toString())
                _booleanAnimation.postValue(false)
            }
            .subscribe(
                { result ->
                    result.forEach { items ->
                        visitHistoryDao.addVisitHistory(
                            RVisitHistory(
                                0,
                                items.service,
                                items.dateOfService,
                                items.type,
                                items.count,
                                items.sum,
                                items.doctor
                            )
                        )
                    }
                },
                { error ->
                    _booleanAnimation.postValue(false)
                    _toastMessage.postValue(error.toString())
                }
            )
    }

}