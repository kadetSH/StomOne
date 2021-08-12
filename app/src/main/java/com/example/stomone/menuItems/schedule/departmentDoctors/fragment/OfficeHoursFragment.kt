package com.example.stomone.menuItems.schedule.departmentDoctors.fragment

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
import com.example.stomone.Constants
import com.example.stomone.R
import com.example.stomone.TitleController
import com.example.stomone.databinding.FragmentOfficeHoursBinding
import com.example.stomone.jsonMy.OfficeHoursJS
import com.example.stomone.menuItems.schedule.departmentDoctors.recyclerOfficeHours.OfficeHoursAdapter
import com.example.stomone.menuItems.schedule.departmentDoctors.recyclerOfficeHours.OfficeHoursItem
import com.example.stomone.menuItems.schedule.departmentDoctors.viewModel.OfficeHoursViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class OfficeHoursFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: OfficeHoursViewModel by viewModels {
        viewModelFactory
    }

    companion object {
        fun newInstance(patientUI: String?): OfficeHoursFragment {
            val args = Bundle()
            args.putString(Constants.DEPARTMENT, patientUI)
            val fragment = OfficeHoursFragment()
            fragment.arguments = args
            return fragment
        }
    }

    var department: String? = null
    private var recyclerView: RecyclerView? = null
    private var adapter: OfficeHoursAdapter? = null
    private var list = ArrayList<OfficeHoursItem>()
    lateinit var anima: View
    private lateinit var starAnim: Animation
    private lateinit var binding: FragmentOfficeHoursBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOfficeHoursBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let { arg ->
            department = arg.getString(Constants.DEPARTMENT)
        }
        department?.let { depart ->
            (activity as? TitleController)?.setTitle(
                depart
            )
        }

        initAnimationView()
        viewModel.isAnimation(true)
        initRecycler()
        observeViewModel()
        department?.let { viewModel.getOfficeHours(it) }
    }

    private fun initAnimationView() {
        starAnim = android.view.animation.AnimationUtils.loadAnimation(this.context, R.anim.turn)
        anima = binding.idOfficeHoursAnim
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
                if (bool == true) {
                    anima.visibility = View.VISIBLE
                    anima.startAnimation(starAnim)
                } else {
                    anima.visibility = View.INVISIBLE
                    starAnim.cancel()
                    anima.clearAnimation()
                }
            })

        viewModel.listOfficeHours.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer<ArrayList<OfficeHoursJS>> { itemOfficeHoursJS ->
                list.clear()
                var check: Boolean = false
                itemOfficeHoursJS.forEach { item ->
                    check = true
                    val doctorFIO: String = item.doctor
                    val doctorProfession: String = item.profession
                    val day1: String = item.schedule[0].dayOfTheWeek
                    val date1: String = item.schedule[0].date
                    val reception1: String = item.schedule[0].reception
                    val day2: String = item.schedule[1].dayOfTheWeek
                    val date2: String = item.schedule[1].date
                    val reception2: String = item.schedule[1].reception
                    val day3: String = item.schedule[2].dayOfTheWeek
                    val date3: String = item.schedule[2].date
                    val reception3: String = item.schedule[2].reception
                    val day4: String = item.schedule[3].dayOfTheWeek
                    val date4: String = item.schedule[3].date
                    val reception4: String = item.schedule[3].reception
                    val day5: String = item.schedule[4].dayOfTheWeek
                    val date5: String = item.schedule[4].date
                    val reception5: String = item.schedule[4].reception
                    val day6: String = item.schedule[5].dayOfTheWeek
                    val date6: String = item.schedule[5].date
                    val reception6: String = item.schedule[5].reception

                    list.add(
                        OfficeHoursItem(
                            doctorFIO,
                            doctorProfession,
                            day1,
                            date1,
                            reception1,
                            day2,
                            date2,
                            reception2,
                            day3,
                            date3,
                            reception3,
                            day4,
                            date4,
                            reception4,
                            day5,
                            date5,
                            reception5,
                            day6,
                            date6,
                            reception6
                        )
                    )

                }
                recyclerView?.adapter?.notifyDataSetChanged()
                if (check) {
                    viewModel.isAnimation(false)
                }

            })
    }

    private fun initRecycler() {
        val layoutManager = LinearLayoutManager(context)
        adapter = OfficeHoursAdapter(
            LayoutInflater.from(requireContext()),
            list
        ) { officeHoursItem: OfficeHoursItem, position: Int, date: String, reception: String ->
            (activity as? OnOfficeHoursClickListener)?.onOfficeClick(
                officeHoursItem,
                position,
                date,
                reception
            )
        }
        recyclerView = binding.idRecyclerViewOfficeHours
        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = adapter
    }

    private fun selectToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    interface OnOfficeHoursClickListener {
        fun onOfficeClick(
            officeHoursItem: OfficeHoursItem,
            position: Int,
            date: String,
            reception: String
        )
    }


}