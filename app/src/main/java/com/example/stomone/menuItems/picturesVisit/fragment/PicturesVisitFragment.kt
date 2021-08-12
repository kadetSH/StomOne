package com.example.stomone.menuItems.picturesVisit.fragment

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
import com.example.stomone.Constants
import com.example.stomone.R
import com.example.stomone.TitleController
import com.example.stomone.databinding.FragmentPicturesVisitBinding
import com.example.stomone.menuItems.picturesVisit.recyclerPicturesVisit.PicturesVisitAdapter
import com.example.stomone.menuItems.picturesVisit.recyclerPicturesVisit.PicturesVisitItem
import com.example.stomone.room.picturesVisit.RPicturesVisit
import com.example.stomone.menuItems.picturesVisit.viewModel.PicturesVisitViewModel
import dagger.android.support.DaggerFragment
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

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
            args.putString(Constants.PATIENT_UI, patientUI)
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
    private lateinit var binding: FragmentPicturesVisitBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPicturesVisitBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let { arg ->
            patientUI = arg.getString(Constants.PATIENT_UI)
        }
        (activity as? TitleController)?.setTitle(
            requireContext().resources.getString(R.string.drawer_menu_pictures_visit)
        )
        initAnimationView()
        viewModel.isAnimation(true)
        initRecycler()
        observeViewModel()
        patientUI?.let { viewModel.getPicturesVisitList(it) }
    }

    private fun initAnimationView() {
        starAnim = android.view.animation.AnimationUtils.loadAnimation(this.context, R.anim.turn)
        anima = binding.idPicturesVisitAnim
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
                    (activity as? OnClickViewPicturesVisit)?.onViewPicturesVisit(
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
        recyclerView = binding.idRecyclerViewPicturesVisit
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


}