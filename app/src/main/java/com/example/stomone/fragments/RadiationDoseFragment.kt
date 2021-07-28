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
import com.example.stomone.recyclerRadiationDose.RadiationDoseAdapter
import com.example.stomone.recyclerRadiationDose.RadiationDoseItem
import com.example.stomone.room.RRadiationDose
import com.example.stomone.viewmodels.RadiationDoseViewModel
import dagger.android.support.DaggerFragment
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_radiation_dose.*
import javax.inject.Inject

class RadiationDoseFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: RadiationDoseViewModel by viewModels {
        viewModelFactory
    }

    companion object {
        fun newInstance(patientUI: String?): RadiationDoseFragment {
            val args = Bundle()
            args.putString("patientUI", patientUI)
            val fragment = RadiationDoseFragment()
            fragment.arguments = args
            return fragment
        }
    }

    var patientUI: String? = null
    private var recyclerView: RecyclerView? = null
    private var adapter: RadiationDoseAdapter? = null
    private var list = ArrayList<RadiationDoseItem>()
    lateinit var anima: View
    private lateinit var starAnim: Animation

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_radiation_dose, container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let { arg ->
            patientUI = arg.getString(requireContext().resources.getString(R.string.key_patient_ui))
        }
        (activity as? RadiationDoseFragment.SetTitleIsFragment)?.setTitleRadiationDose(
            requireContext().resources.getString(R.string.drawer_menu_irradiation)
        )
        initAnimationView()
        viewModel.isAnimation(true)
        initRecycler()
        observeViewModel()
        requestRadiationDoseList()
    }

    private fun initAnimationView() {
        starAnim = android.view.animation.AnimationUtils.loadAnimation(this.context, R.anim.turn)
        anima = requireActivity().findViewById(R.id.id_radiation_dose_anim)
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

    private fun requestRadiationDoseList() {
        patientUI?.let { viewModel.loadRadiationDoseList(it) }
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

        viewModel.readAllRadiationDoseLiveData.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer<List<RRadiationDose>> { result ->
                list.clear()
                result.forEach { item ->
                    list.add(
                        RadiationDoseItem(
                            item.date,
                            item.teeth,
                            item.typeOfResearch,
                            item.radiationDose,
                            item.doctor
                        )
                    )
                }
                recyclerView?.adapter?.notifyDataSetChanged()
            })
    }

    private fun initRecycler() {
        val layoutManager = LinearLayoutManager(context)
        adapter = RadiationDoseAdapter(
            LayoutInflater.from(requireContext()),
            list
        )
        recyclerView = id_recyclerView_radiation_dose
        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = adapter
    }

    private fun selectToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    interface SetTitleIsFragment {
        fun setTitleRadiationDose(titleIsFragment: String)
    }
}