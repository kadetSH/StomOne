package com.example.stomone.menuItems.radiationDose.fragment

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
import com.example.stomone.databinding.FragmentRadiationDoseBinding
import com.example.stomone.menuItems.radiationDose.recyclerRadiationDose.RadiationDoseAdapter
import com.example.stomone.menuItems.radiationDose.recyclerRadiationDose.RadiationDoseItem
import com.example.stomone.room.radiationDose.RRadiationDose
import com.example.stomone.menuItems.radiationDose.viewModel.RadiationDoseViewModel
import dagger.android.support.DaggerFragment
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
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
            args.putString(Constants.PATIENT_UI, patientUI)
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
    private lateinit var binding: FragmentRadiationDoseBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRadiationDoseBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let { arg ->
            patientUI = arg.getString(Constants.PATIENT_UI)
        }
        (activity as? TitleController)?.setTitle(
            requireContext().resources.getString(R.string.drawer_menu_irradiation)
        )
        initAnimationView()
        viewModel.isAnimation(true)
        initRecycler()
        observeViewModel()
        patientUI?.let { viewModel.getRadiationDoseList(it) }
    }

    private fun initAnimationView() {
        starAnim = android.view.animation.AnimationUtils.loadAnimation(this.context, R.anim.turn)
        anima = binding.idRadiationDoseAnim
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
        recyclerView = binding.idRecyclerViewRadiationDose
        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = adapter
    }

    private fun selectToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

}