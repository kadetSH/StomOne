package com.example.stomone.menuItems.contactInformation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import android.view.animation.Animation
import androidx.lifecycle.ViewModelProvider
import com.example.stomone.Constants
import com.example.stomone.R
import com.example.stomone.TitleController
import com.example.stomone.databinding.FragmentContactInformationBinding
import com.example.stomone.room.contactInformation.RContactInformation
import com.example.stomone.menuItems.contactInformation.viewModel.ContactInformationViewModel
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
            args.putString(Constants.PATIENT_UI, patientUI)
            val fragment = ContactInformationFragment()
            fragment.arguments = args
            return fragment
        }
    }
    private lateinit var binding: FragmentContactInformationBinding
    private lateinit var editSurname : TextView
    private lateinit var editName : TextView
    private lateinit var editPatronymic : TextView
    private lateinit var editBirth : TextView
    private lateinit var editGender : TextView
    private lateinit var editTelephone : TextView
    private lateinit var editAddress : TextView
    var patientUI: String? = null
    lateinit var anima: View
    private lateinit var starAnim: Animation

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactInformationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let { arg ->
            patientUI = arg.getString(Constants.PATIENT_UI)
        }
        (activity as? TitleController)?.setTitle(
            requireContext().resources.getString(R.string.drawer_menu_contact_information)
        )
        initView()
        initAnimationView()
        viewModel.isAnimation(true)
        observeViewModel()
        patientUI?.let { viewModel.getPatientInfo(it) }
    }

    private fun initView(){
        editSurname =  binding.contactEditSurname
        editName = binding.contactEditName
        editPatronymic = binding.contactEditPatronymic
        editBirth  = binding.contactEditBirth
        editGender = binding.contactEditGender
        editTelephone = binding.contactEditTelephone
        editAddress = binding.contactEditAddress
    }

    private fun initAnimationView() {
        starAnim = android.view.animation.AnimationUtils.loadAnimation(this.context, R.anim.turn)
        anima = binding.idContactInfoAnim
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

    private fun selectToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

}