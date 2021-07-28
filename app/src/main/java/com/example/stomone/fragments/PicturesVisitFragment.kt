package com.example.stomone.fragments

import android.graphics.Bitmap
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
import com.example.stomone.recyclerPicturesVisit.PicturesVisitAdapter
import com.example.stomone.recyclerPicturesVisit.PicturesVisitItem
import com.example.stomone.room.RPicturesVisit
import com.example.stomone.viewmodels.PicturesVisitViewModel
import dagger.android.support.DaggerFragment
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_pictures_visit.*
import javax.inject.Inject

class PicturesVisitFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: PicturesVisitViewModel by viewModels {
        viewModelFactory
    }

    companion object {
        fun newInstance(patientUI: String?): PicturesVisitFragment {
            val args = Bundle()
            args.putString("patientUI", patientUI)
            val fragment = PicturesVisitFragment()
            fragment.arguments = args
            return fragment
        }
    }

    var patientUI: String? = null
    private var recyclerView: RecyclerView? = null
    private var adapter: PicturesVisitAdapter? = null
    private var list = ArrayList<PicturesVisitItem>()
    var btm: Bitmap? = null
    lateinit var anima: View
    private lateinit var starAnim: Animation

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_pictures_visit, container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let { arg ->
            patientUI = arg.getString(requireContext().resources.getString(R.string.key_patient_ui))
        }
        (activity as? PicturesVisitFragment.SetTitleIsFragment)?.setTitleIsPicturesVisit(
            requireContext().resources.getString(R.string.drawer_menu_pictures_visit)
        )
        initAnimationView()
        viewModel.isAnimation(true)
        initRecycler()
        observeViewModel()
        requestPicturesVisitList()
    }

    private fun initAnimationView() {
        starAnim = android.view.animation.AnimationUtils.loadAnimation(this.context, R.anim.turn)
        anima = requireActivity().findViewById(R.id.id_pictures_visit_anim)
    }

    private fun requestPicturesVisitList() {
        patientUI?.let { viewModel.loadPicturesVisitList(it) }
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

        viewModel.readAllPicturesVisitLiveData.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer<List<RPicturesVisit>> { result ->
                list.clear()
                result.forEach { item ->
                    list.add(
                        PicturesVisitItem(
                            item.dateOfReceipt,
                            item.numberPicture,
                            item.doctor
                        )
                    )
                }
                recyclerView?.adapter?.notifyDataSetChanged()
            })

        viewModel.viewPhoto.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer<Bitmap> { result ->
                btm = result
                btm?.let {
                    (activity as? PicturesVisitFragment.OnClickViewPicturesVisit)?.onViewPicturesVisit(
                        it
                    )
                }

            })
    }

    private fun initRecycler() {
        val layoutManager = LinearLayoutManager(context)
        adapter = PicturesVisitAdapter(
            LayoutInflater.from(requireContext()),
            list
        ) { itemPictures: PicturesVisitItem ->
            viewModel.loadPicturesVisit(itemPictures)
        }
        recyclerView = id_recyclerView_pictures_visit
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

    private fun selectToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    interface OnClickViewPicturesVisit {
        fun onViewPicturesVisit(btm: Bitmap)
    }

    interface SetTitleIsFragment {
        fun setTitleIsPicturesVisit(titleIsFragment: String)
    }

}