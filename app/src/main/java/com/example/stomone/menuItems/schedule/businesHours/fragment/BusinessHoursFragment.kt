package com.example.stomone.menuItems.schedule.businesHours.fragment

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stomone.Constants
import com.example.stomone.R
import com.example.stomone.TitleController
import com.example.stomone.databinding.FragmentBusinessHoursBinding
import com.example.stomone.jsonMy.BusinessHoursResultJS
import com.example.stomone.jsonMy.CreateAnAppointmentJS
import com.example.stomone.menuItems.schedule.businesHours.recyclerBusinessHours.BusinessHoursAdapter
import com.example.stomone.menuItems.schedule.businesHours.recyclerBusinessHours.BusinessHoursItem
import com.example.stomone.menuItems.schedule.businesHours.viewModel.BusinessHoursViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class BusinessHoursFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: BusinessHoursViewModel by viewModels {
        viewModelFactory
    }

    companion object {
        fun newInstance(
            doctor: String?,
            date: String?,
            periodOfTime: String?,
            patientUI: String?
        ): BusinessHoursFragment {
            val args = Bundle()
            args.putString(Constants.DOCTOR_REQUEST, doctor)
            args.putString(Constants.DATE_REQUEST, date)
            args.putString(Constants.PERIOD_OF_TIME_REQUEST, periodOfTime)
            args.putString(Constants.PATIENT_UI, patientUI)
            val fragment = BusinessHoursFragment()
            fragment.arguments = args
            return fragment
        }
    }

    var doctorRequest: String? = null
    var dateRequest: String? = null
    var periodOfTimeRequest: String? = null
    var patientUI: String? = null
    private var recyclerView: RecyclerView? = null
    private var adapter: BusinessHoursAdapter? = null
    private var list = ArrayList<BusinessHoursItem>()
    private lateinit var binding: FragmentBusinessHoursBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBusinessHoursBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let { arg ->
            doctorRequest =
                arg.getString(Constants.DOCTOR_REQUEST)
            dateRequest =
                arg.getString(Constants.DATE_REQUEST)
            periodOfTimeRequest =
                arg.getString(Constants.PERIOD_OF_TIME_REQUEST)
            patientUI = arg.getString(Constants.PATIENT_UI)
        }
        doctorRequest?.let {
            (activity as? TitleController)?.setTitle(
                it
            )
        }
        initRecycler()
        observeViewModel()
        getBusinessHours()
    }

    private fun getBusinessHours() {
        doctorRequest?.let { doctorString ->
            dateRequest?.let { dateString ->
                periodOfTimeRequest?.let { periodString ->
                    viewModel.getBusinessHours(
                        doctorString,
                        dateString, periodString
                    )
                }
            }
        }
    }

    private fun observeViewModel() {
        viewModel.listBusinessHours.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer<ArrayList<BusinessHoursResultJS>> { itemOfficeHoursJS ->
                list.clear()
                itemOfficeHoursJS.forEach { item ->
                    list.add(BusinessHoursItem(item.time, item.access))
                }
                recyclerView?.adapter?.notifyDataSetChanged()
            })

        viewModel.toastMessage.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer<String> { message ->
                selectToast(message)
            })

        viewModel.checkPeriodOfTime.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer<CreateAnAppointmentJS> { request ->
                writeRequestAlertDialog(requireContext(), request)
            })
    }

    private fun initRecycler() {
        val layoutManager = LinearLayoutManager(context)
        adapter = BusinessHoursAdapter(
            LayoutInflater.from(requireContext()),
            list
        )
        { businessHoursItem: BusinessHoursItem, position: Int ->
            patientUI?.let { patientUIString ->
                doctorRequest?.let { doctorRequestString ->
                    dateRequest?.let { dateRequestString ->
                        (activity as? OnBusinessHoursClickListener)?.onBusinessClick(
                            businessHoursItem,
                            position,
                            patientUIString,
                            doctorRequestString,
                            dateRequestString
                        )
                        viewModel.clickTimeItem(
                            businessHoursItem,
                            requireContext(),
                            CreateAnAppointmentJS(
                                patientUIString,
                                doctorRequestString,
                                dateRequestString,
                                businessHoursItem.time
                            )
                        )
                    }
                }
            }
        }
        recyclerView = binding.idRecyclerViewBusinessHours
        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = adapter
    }

    private fun selectToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    interface OnBusinessHoursClickListener {
        fun onBusinessClick(
            businessHoursItem: BusinessHoursItem,
            position: Int,
            PatientUiString: String,
            DoctorFIO: String,
            DateOfReceipt: String
        )
    }

    private fun writeRequestAlertDialog(
        context: Context,
        anAppointmentJS: CreateAnAppointmentJS
    ) {
        val builderAlertDialog: AlertDialog.Builder = AlertDialog.Builder(context)
        val clickCancel = DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
        }
        val clickRecording = DialogInterface.OnClickListener { dialog, which ->
            viewModel.makeToAppointment(anAppointmentJS)
        }
        builderAlertDialog.setMessage(resources.getString(R.string.toast_make_an_appointment))
        builderAlertDialog.setNegativeButton(resources.getString(R.string.labelNo), clickCancel)
        builderAlertDialog.setPositiveButton(resources.getString(R.string.labelYes), clickRecording)
        val dialog: AlertDialog = builderAlertDialog.create()
        dialog.show()
    }

}