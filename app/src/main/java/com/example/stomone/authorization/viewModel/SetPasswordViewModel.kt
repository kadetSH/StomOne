package com.example.stomone.authorization.viewModel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.stomone.App
import com.example.stomone.R
import com.example.stomone.SingleLiveEvent
import com.example.stomone.jsonMy.PasswordRequest
import com.example.stomone.jsonMy.SearchKlient
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class SetPasswordViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    private var _toastMessage = SingleLiveEvent<String>()
    val toastMessage: LiveData<String> get() = _toastMessage

    private var _booleanAnimation = SingleLiveEvent<Boolean>()
    val booleanAnimation: LiveData<Boolean> get() = _booleanAnimation

    @SuppressLint("CheckResult")
    @Suppress("LocalVariableName")
    fun setPassword(
        surname: String,
        name: String,
        patronymic: String,
        telephone: String,
        birth: String,
        context: Context
    ) {
        if (surname == "") {
            _toastMessage.postValue(context.resources.getString(R.string.toast_password_surname_filds))
            return
        }
        if (name == "") {
            _toastMessage.postValue(context.resources.getString(R.string.toast_password_name_filds))
            return
        }
        if (patronymic == "") {
            _toastMessage.postValue(context.resources.getString(R.string.toast_password_patronymic_filds))
            return
        }
        if (telephone == "") {
            _toastMessage.postValue(context.resources.getString(R.string.toast_password_telephone_filds))
            return
        }
        if (birth == "") {
            _toastMessage.postValue(context.resources.getString(R.string.toast_password_birth_filds))
            return
        }

        _booleanAnimation.postValue(true)
        val JSON = SearchKlient(surname, name, patronymic, telephone,  birth)

        val interactor = App.instance.authorizationInteractor
        val result: io.reactivex.Observable<PasswordRequest> = interactor.getPassword(JSON)
        result
            .subscribeOn(Schedulers.io())
            .doOnError {
                _booleanAnimation.postValue(false)
                _toastMessage.postValue(context.resources.getString(R.string.toast_password_error_request))
            }
            .subscribe(
                { result ->
                    _booleanAnimation.postValue(false)
                    if (result.result == context.resources.getString(R.string.toast_password_client_found)) {
                        _toastMessage.postValue(context.resources.getString(R.string.toast_password_sent))
                    } else {
                        _toastMessage.postValue(result.result)
                    }
                },
                { error ->
                    _booleanAnimation.postValue(false)
                    _toastMessage.postValue(error.message)
                }
            )
    }

}