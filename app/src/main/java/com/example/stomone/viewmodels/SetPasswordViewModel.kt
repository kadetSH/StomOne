package com.example.stomone.viewmodels


import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.stomone.App
import com.example.stomone.R
import com.example.stomone.SingleLiveEvent
import com.example.stomone.jsonMy.SearchKlient
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class SetPasswordViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    private var _toastMessage = SingleLiveEvent<String>()
    val toastMessage: LiveData<String> get() = _toastMessage


    @SuppressLint("CheckResult")
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

//        val JSON = SearchKlient(surname, name, patronymic, telephone,  birth)
        val JSON = SearchKlient("Гончаров", "Евгений", "Николаевич", "9803869901", "12.12.1986")

        App.instance.api.passwordRequest(JSON)
            .subscribeOn(Schedulers.io())
            .doOnError {
                _toastMessage.postValue(context.resources.getString(R.string.toast_password_error_request))
            }
            .subscribeOn(Schedulers.newThread())
            .subscribe(
                { result ->
                    if (result.result == context.resources.getString(R.string.toast_password_client_found)) {
                        _toastMessage.postValue(context.resources.getString(R.string.toast_password_sent))
                    } else {
                        _toastMessage.postValue(result.result)
                    }
                },
                { error ->
                    _toastMessage.postValue(error.message)
                }
            )

    }


}