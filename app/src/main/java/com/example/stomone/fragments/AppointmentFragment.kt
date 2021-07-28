package com.example.stomone.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stomone.R
import com.example.stomone.jsonMy.ListOfApplicationsJS
import com.example.stomone.recyclerAppointment.AppointmentAdapter
import com.example.stomone.recyclerAppointment.AppointmentItem
import com.example.stomone.viewmodels.AppointmentViewModel
import dagger.android.support.DaggerFragment
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_appointment.*
import javax.inject.Inject

class AppointmentFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: AppointmentViewModel by viewModels {
        viewModelFactory
    }

    companion object {
        fun newInstance(patientUI: String?): AppointmentFragment {
            val args = Bundle()
            args.putString("patientUI", patientUI)
            val fragment = AppointmentFragment()
            fragment.arguments = args
            return fragment
        }
    }

    var patientUI: String? = null
    private var recyclerView: RecyclerView? = null
    private var adapter: AppointmentAdapter? = null
    private var list = ArrayList<AppointmentItem>()
    lateinit var anima: View
    private lateinit var starAnim: Animation

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_appointment, container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let { arg ->
            patientUI = arg.getString(requireContext().resources.getString(R.string.key_patient_ui))
        }
        (activity as? AppointmentFragment.SetTitleIsFragment)?.setTitleIsAppointment(
            requireContext().resources.getString(R.string.drawer_menu_appointment)
        )
        initAnimationView()
        viewModel.isAnimation(true)
        initRecycler()
        observeViewModel()
        requestAppointmentList()
    }

    private fun initAnimationView() {
        starAnim = android.view.animation.AnimationUtils.loadAnimation(this.context, R.anim.turn)
        anima = requireActivity().findViewById(R.id.id_appointment_anim)
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

    private fun requestAppointmentList() {
        patientUI?.let { viewModel.requestAppointmentList(it) }
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

        viewModel.listAppointment.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer<ArrayList<ListOfApplicationsJS>> { result ->
                list.clear()
                result.forEach { item ->
                    list.add(
                        AppointmentItem(
                            item.requestNumber,
                            item.doctorFIO,
                            item.doctorProfession,
                            item.dateRequest,
                            item.timeStart,
                            item.timeEnd
                        )
                    )
                }
                recyclerView?.adapter?.notifyDataSetChanged()
            })
    }

    private fun initRecycler() {
        val layoutManager = LinearLayoutManager(context)
        adapter = AppointmentAdapter(
            LayoutInflater.from(requireContext()),
            list
        )
        recyclerView = id_recyclerView_appointment
        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = adapter
    }

    private fun selectToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    interface SetTitleIsFragment {
        fun setTitleIsAppointment(titleIsFragment: String)
    }

}