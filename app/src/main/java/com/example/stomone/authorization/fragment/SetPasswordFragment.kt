package com.example.stomone.authorization.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.stomone.authorization.item.LoginItem
import com.example.stomone.R
import com.example.stomone.authorization.viewModel.SetPasswordViewModel
import com.example.stomone.databinding.FragmentSetPasswordBinding
import dagger.android.support.DaggerFragment
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class SetPasswordFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: SetPasswordViewModel by viewModels {
        viewModelFactory
    }

    private var loginItem: LoginItem? = null
    private lateinit var surnameFilds : TextView
    private lateinit var nameFilds : TextView
    private lateinit var patronymicFilds : TextView
    private lateinit var telephoneFilds : TextView
    private lateinit var birthFilds : TextView
    private lateinit var btnSetPassword : Button
    private lateinit var btnSetData : Button
    lateinit var anima: View
    private lateinit var starAnim: Animation
    private lateinit var binding: FragmentSetPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSetPasswordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        btnSetPassword.setOnClickListener { checkFilds() }
        btnSetData.setOnClickListener { showDatePickerDialog(view) }
        initAnimationView()
        observeViewModel()
    }

    private fun initView(){
        surnameFilds = binding.idPassEditSurname
        nameFilds = binding.idPassEditName
        patronymicFilds = binding.idPassEditPatronymic
        telephoneFilds = binding.idPassEditTelephone
        birthFilds = binding.idBirthEdit
        btnSetPassword = binding.idBtnSetPassword
        btnSetData = binding.idBtnSetData
    }

    private fun initAnimationView() {
        starAnim = android.view.animation.AnimationUtils.loadAnimation(this.context, R.anim.turn)
        anima = binding.idSetPasswordAnim
    }

    private fun animation(bool: Boolean) {
        if (bool) {
            anima.visibility = View.VISIBLE
            anima.startAnimation(starAnim)
        } else {
            anima.visibility = View.INVISIBLE
            starAnim.cancel()
            anima.clearAnimation()
        }
    }

    private fun observeViewModel() {
        viewModel.toastMessage.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer<String> { message ->
                selectToast(message)
                if (message == resources.getString(R.string.toast_password_sent)) {
                    loginItem?.let { login ->
                        (activity as? OnBackPressedTrue)?.onBackPressedIsSetPassword(login)
                    }

                }
            })

        viewModel.booleanAnimation.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer<Boolean> { bool ->
                Observable.just(animation(bool))
                    .observeOn(Schedulers.newThread())
                    .subscribe()
            })
    }

    private fun showDatePickerDialog(view: View) {
        val listenerDate =
            DatePickerDialog.OnDateSetListener { datePicker: DatePicker, year: Int, mont: Int, day: Int ->
                var dayStr = day.toString()
                var montStr = (mont + 1).toString()
                val yearStr = year.toString()

                if (dayStr.length == 1) dayStr = "0$dayStr"
                if (montStr.length == 1) montStr = "0$montStr"
                birthFilds.text = "$dayStr.$montStr.$yearStr"
            }
        val newCalendar = Calendar.getInstance()
        val dialog = DatePickerDialog(
            requireContext(),
            listenerDate,
            newCalendar[Calendar.YEAR],
            newCalendar[Calendar.MONTH],
            newCalendar[Calendar.DAY_OF_MONTH]
        )
        dialog.show()
    }

    private fun checkFilds() {
        val surname: String = surnameFilds.text.toString()
        val name: String = nameFilds.text.toString()
        val patronymic: String = patronymicFilds.text.toString()
        val telephone: String = telephoneFilds.text.toString()
        val birth: String = birthFilds.text.toString()
        loginItem = LoginItem(surname, name, patronymic, "")
        viewModel.setPassword(surname, name, patronymic, telephone, birth, requireContext())
    }

    private fun selectToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    interface OnBackPressedTrue {
        fun onBackPressedIsSetPassword(loginItem: LoginItem)
    }


}