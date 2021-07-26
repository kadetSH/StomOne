package com.example.stomone.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.stomone.LoginItem
import com.example.stomone.R
import com.example.stomone.viewmodels.SetPasswordViewModel
import dagger.android.support.DaggerFragment
import java.util.*
import javax.inject.Inject

class SetPasswordFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: SetPasswordViewModel by viewModels {
        viewModelFactory
    }

    private var loginItem: LoginItem? = null

    private val surnameFilds by lazy {
        requireActivity().findViewById(R.id.idPassEditSurname) as TextView
    }

    private val nameFilds by lazy {
        requireActivity().findViewById(R.id.idPassEditName) as TextView
    }

    private val patronymicFilds by lazy {
        requireActivity().findViewById(R.id.idPassEditPatronymic) as TextView
    }

    private val telephoneFilds by lazy {
        requireActivity().findViewById(R.id.idPassEditTelephone) as TextView
    }

    private val birthFilds by lazy {
        requireActivity().findViewById(R.id.idBirthEdit) as TextView
    }

    private val btnSetPassword by lazy {
        requireActivity().findViewById(R.id.idBtnSetPassword) as Button
    }

    private val btnSetData by lazy {
        requireActivity().findViewById(R.id.idBtnSetData) as Button
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_set_password, container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btnSetPassword.setOnClickListener { checkFilds() }
        btnSetData.setOnClickListener { showDatePickerDialog(view) }
        observeViewModel()
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