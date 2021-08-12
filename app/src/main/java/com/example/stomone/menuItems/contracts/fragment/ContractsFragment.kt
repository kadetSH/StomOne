package com.example.stomone.menuItems.contracts.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stomone.Constants
import com.example.stomone.R
import com.example.stomone.TitleController
import com.example.stomone.databinding.FragmentContractsBinding
import com.example.stomone.menuItems.contracts.recyclerContracts.ContractItem
import com.example.stomone.menuItems.contracts.recyclerContracts.ContractsAdapter
import com.example.stomone.room.contracts.RContracts
import com.example.stomone.menuItems.contracts.viewModel.ContractsViewModel
import dagger.android.support.DaggerFragment
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ContractsFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: ContractsViewModel by viewModels {
        viewModelFactory
    }

    companion object {
        fun newInstance(patientUI: String?): ContractsFragment {
            val args = Bundle()
            args.putString(Constants.PATIENT_UI, patientUI)
            val fragment = ContractsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    lateinit var anima: View
    private lateinit var starAnim: Animation
    private var recyclerView: RecyclerView? = null
    private var adapter: ContractsAdapter? = null
    private var list = ArrayList<ContractItem>()
    var patientUI: String? = null
    private lateinit var binding: FragmentContractsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContractsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let { arg ->
            patientUI = arg.getString(Constants.PATIENT_UI)
        }
        (activity as? TitleController)?.setTitle(
            requireContext().resources.getString(R.string.drawer_menu_contracts)
        )
        initAnimationView()
        viewModel.isAnimation(true)
        initRecycler()
        observeViewModel()
        patientUI?.let { viewModel.getContracts(it) }
    }

    private fun initAnimationView() {
        starAnim = android.view.animation.AnimationUtils.loadAnimation(this.context, R.anim.turn)
        anima = binding.idContractsAnim
    }

    private fun initRecycler() {
        val layoutManager = LinearLayoutManager(context)
        adapter = ContractsAdapter(
            LayoutInflater.from(requireContext()),
            list
        )
        recyclerView = binding.idRecyclerViewContracts
        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.readAllContractsLiveData.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer<List<RContracts>> { result ->
                list.clear()
                result.forEach { item ->
                    val checkBoxBoolean: Boolean = item.finishedCheckBox == 1
                    list.add(
                        ContractItem(
                            item.contractNumber,
                            item.dateOfCreation,
                            checkBoxBoolean,
                            item.doctor
                        )
                    )
                }
                if (list.size > 0) {
                    viewModel.isAnimation(false)
                }
                recyclerView?.adapter?.notifyDataSetChanged()

            })

        viewModel.booleanAnimation.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer<Boolean> { bool ->
                Observable.just(animation(bool))
                    .observeOn(Schedulers.newThread())
                    .subscribe()
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

}