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
import com.example.stomone.recyclerContracts.ContractItem
import com.example.stomone.recyclerContracts.ContractsAdapter
import com.example.stomone.recyclerVisitHistory.VisitHistoryAdapter
import com.example.stomone.recyclerVisitHistory.VisitHistoryItem
import com.example.stomone.room.RContracts
import com.example.stomone.room.RVisitHistory
import com.example.stomone.viewmodels.VisitHistoryViewModel
import dagger.android.support.DaggerFragment
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_contracts.*
import kotlinx.android.synthetic.main.fragment_visit_history.*
import javax.inject.Inject

class VisitHistoryFragment: DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: VisitHistoryViewModel by viewModels {
        viewModelFactory
    }

    companion object {
        fun newInstance(patientUI: String?): VisitHistoryFragment {
            val args = Bundle()
            args.putString("patientUI", patientUI)
            val fragment = VisitHistoryFragment()
            fragment.arguments = args
            return fragment
        }
    }

    var patientUI: String? = null
    private var recyclerView : RecyclerView? = null
    private var adapter : VisitHistoryAdapter? = null
    private var list = ArrayList<VisitHistoryItem>()
    lateinit var anima: View
    private lateinit var starAnim: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_visit_history, container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let { arg ->
            patientUI = arg.getString("patientUI")
        }

        (activity as? VisitHistoryFragment.SetTitleIsFragment)?.setTitleIsVisitHistory(
            requireContext().resources.getString(R.string.drawer_menu_visit_history)
        )

        initAnimationView()
        viewModel.isAnimation(true)

        initRecycler()
        observeViewModel()
        requestVisitHistory()
    }

    private fun initAnimationView() {
        starAnim = android.view.animation.AnimationUtils.loadAnimation(this.context, R.anim.turn)
        anima = requireActivity().findViewById(R.id.id_visit_history_anim)
    }

    private fun initRecycler() {
        val layoutManager = LinearLayoutManager(context)
        adapter = VisitHistoryAdapter(
            LayoutInflater.from(requireContext()),
            list
        )
        recyclerView = id_recyclerView_visit_history
        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = adapter
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
        viewModel.booleanAnimation.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer<Boolean> { bool ->
                Observable.just(animation(bool))
                    .observeOn(Schedulers.newThread())
                    .subscribe()
            })

        viewModel.readAllVisitHistoryLiveData.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer<List<RVisitHistory>> { result ->
                list.clear()
                result.forEach { item ->
                    list.add(
                        VisitHistoryItem(
                            item.service,
                            item.dateOfService,
                            item.type,
                            item.count,
                            item.sum,
                            item.doctor
                        )
                    )
                }
                if (list.size > 0){
                    viewModel.isAnimation(false)
                }
                recyclerView?.adapter?.notifyDataSetChanged()
    })

        viewModel.toastMessage.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer<String> { message ->
                selectToast(message)
            })

    }

    private fun selectToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun requestVisitHistory() {
        patientUI?.let { viewModel.loadVisitHistory(it) }
    }

    interface SetTitleIsFragment {
        fun setTitleIsVisitHistory(titleIsFragment: String)
    }

}


