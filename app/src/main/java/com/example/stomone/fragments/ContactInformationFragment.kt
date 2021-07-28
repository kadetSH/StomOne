package com.example.stomone.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import android.view.animation.Animation
import androidx.lifecycle.ViewModelProvider
import com.example.stomone.R
import com.example.stomone.room.RContactInformation
import com.example.stomone.viewmodels.ContactInformationViewModel
import dagger.android.support.DaggerFragment
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@Suppress("UNREACHABLE_CODE")
class ContactInformationFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: ContactInformationViewModel by viewModels {
        viewModelFactory
    }

    companion object {
        fun newInstance(patientUI: String?): ContactInformationFragment {
            val args = Bundle()
            args.putString("patientUI", patientUI)
            val fragment = ContactInformationFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val editSurname by lazy {
        requireActivity().findViewById(R.id.contact_editSurname) as TextView
    }
    private val editName by lazy {
        requireActivity().findViewById(R.id.contact_editName) as TextView
    }
    private val editPatronymic by lazy {
        requireActivity().findViewById(R.id.contact_editPatronymic) as TextView
    }
    private val editBirth by lazy {
        requireActivity().findViewById(R.id.contact_editBirth) as TextView
    }
    private val editGender by lazy {
        requireActivity().findViewById(R.id.contact_editGender) as TextView
    }
    private val editTelephone by lazy {
        requireActivity().findViewById(R.id.contact_editTelephone) as TextView
    }
    private val editAddress by lazy {
        requireActivity().findViewById(R.id.contact_editAddress) as TextView
    }
    var patientUI: String? = null
    lateinit var anima: View
    private lateinit var starAnim: Animation

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_contact_information, container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let { arg ->
            patientUI = arg.getString(requireContext().resources.getString(R.string.key_patient_ui))
        }
        (activity as? ContactInformationFragment.SetTitleIsFragment)?.setTitleIsContactInformation(
            requireContext().resources.getString(R.string.drawer_menu_contact_information)
        )
        initAnimationView()
        viewModel.isAnimation(true)
        observeViewModel()
        loadPatientInfo()
    }

    private fun initAnimationView() {
        starAnim = android.view.animation.AnimationUtils.loadAnimation(this.context, R.anim.turn)
        anima = requireActivity().findViewById(R.id.id_contact_info_anim)
    }

    private fun observeViewModel() {

        viewModel.toastMessage.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer<String> { message ->
                selectToast(message)
            })

        viewModel.booleanAnimation.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer<Boolean> { bool ->
                Observable.just(animation(bool))
                    .observeOn(Schedulers.newThread())
                    .subscribe()
            })

        viewModel.readAllContractInfoLiveData.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer<List<RContactInformation>> { result ->
                result.forEach { patientInfo ->
                    editSurname?.text = patientInfo.surname
                    editName.text = patientInfo.name
                    editPatronymic.text = patientInfo.patronymic
                    editBirth.text = patientInfo.birth
                    editGender.text = patientInfo.gender
                    editTelephone.text = patientInfo.telephone
                    editAddress.text = patientInfo.address
                    viewModel.isAnimation(false)
                }
            })
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

    private fun loadPatientInfo() {
        patientUI?.let { viewModel.loadPatientInfo(it) }
    }

    private fun selectToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    interface SetTitleIsFragment {
        fun setTitleIsContactInformation(title: String)
    }

}