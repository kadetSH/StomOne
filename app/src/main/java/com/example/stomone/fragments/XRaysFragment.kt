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
import com.example.stomone.recyclerXRays.XRaysAdapter
import com.example.stomone.recyclerXRays.XRaysItem
import com.example.stomone.room.RXRays
import com.example.stomone.viewmodels.XRaysViewModel
import dagger.android.support.DaggerFragment
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_x_rays.*
import javax.inject.Inject

class XRaysFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: XRaysViewModel by viewModels {
        viewModelFactory
    }

    companion object {
        fun newInstance(patientUI: String?): XRaysFragment {
            val args = Bundle()
            args.putString("patientUI", patientUI)
            val fragment = XRaysFragment()
            fragment.arguments = args
            return fragment
        }
    }

    var patientUI: String? = null
    private var recyclerView: RecyclerView? = null
    private var adapter: XRaysAdapter? = null
    private var list = ArrayList<XRaysItem>()
    var btm: Bitmap? = null
    lateinit var anima: View
    private lateinit var starAnim: Animation

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_x_rays, container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let { arg ->
            patientUI = arg.getString(requireContext().resources.getString(R.string.key_patient_ui))
        }
        (activity as? XRaysFragment.SetTitleIsFragment)?.setTitleIsXRays(
            requireContext().resources.getString(R.string.drawer_menu_x_rays)
        )
        initAnimationView()
        viewModel.isAnimation(true)
        initRecycler()
        observeViewModel()
        requestXRaysList()
    }

    private fun initAnimationView() {
        starAnim = android.view.animation.AnimationUtils.loadAnimation(this.context, R.anim.turn)
        anima = requireActivity().findViewById(R.id.id_x_rays_anim)
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

        viewModel.readAllXRaysLiveData.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer<List<RXRays>> { result ->
                list.clear()
                result.forEach { item ->
                    list.add(
                        XRaysItem(
                            item.datePhoto,
                            item.numberDirection,
                            item.typeOfResearch,
                            item.financing,
                            item.teeth,
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
                btm?.let { (activity as? OnClickViewPhoto)?.onViewPhoto(it) }

            })
    }

    private fun initRecycler() {
        val layoutManager = LinearLayoutManager(context)
        adapter = XRaysAdapter(
            LayoutInflater.from(requireContext()),
            list
        ) { xRaysItem: XRaysItem, position: Int ->
            (activity as? OnXRaysClickListener)?.onXRaysClick(xRaysItem, position)
            viewModel.loadImage(xRaysItem, position)
        }
        recyclerView = id_recyclerView_x_rays
        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = adapter
    }

    private fun requestXRaysList() {
        patientUI?.let { viewModel.loadXRaysList(it, requireContext()) }
    }

    private fun selectToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    interface OnXRaysClickListener {
        fun onXRaysClick(xRaysItem: XRaysItem, position: Int)
    }

    interface OnClickViewPhoto {
        fun onViewPhoto(btm: Bitmap)
    }

    interface SetTitleIsFragment {
        fun setTitleIsXRays(titleIsFragment: String)
    }

}