package com.example.stomone.menuItems.schedule.departmentDoctors.viewModel

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.stomone.SingleLiveEvent
import com.example.stomone.dagger.retrofit.repository.RetrofitServiceInterfaceSchedule
import com.example.stomone.jsonMy.DepartmentJS
import com.example.stomone.jsonMy.OfficeHoursJS
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@Suppress("LocalVariableName")
class OfficeHoursViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    @Inject
    lateinit var mServise: RetrofitServiceInterfaceSchedule

    private var _toastMessage = SingleLiveEvent<String>()
    val toastMessage: LiveData<String> get() = _toastMessage

    private var _booleanAnimation = SingleLiveEvent<Boolean>()
    val booleanAnimation: LiveData<Boolean> get() = _booleanAnimation

    private var _listOfficeHours = SingleLiveEvent<ArrayList<OfficeHoursJS>>()
    val listOfficeHours: LiveData<ArrayList<OfficeHoursJS>> get() = _listOfficeHours

    fun isAnimation(bool: Boolean) {
        _booleanAnimation.postValue(bool)
    }

    @SuppressLint("CheckResult")
    fun getOfficeHours(department: String) {
        val JSON = DepartmentJS(department)
        mServise.patientOfficeHoursRequest(JSON)
            .subscribeOn(Schedulers.io())
            .doOnError {
                _toastMessage.postValue(it.toString())
            }
            .subscribe(
                { result ->
                    _listOfficeHours.postValue(result)
                },
                { error ->
                    _toastMessage.postValue(error.toString())
                }
            )
    }
}