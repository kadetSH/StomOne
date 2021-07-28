package com.example.stomone.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.stomone.R
import com.example.stomone.viewmodels.ViewPhotoViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ViewPhotoFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: ViewPhotoViewModel by viewModels {
        viewModelFactory
    }

    private val image by lazy {
        requireActivity().findViewById(R.id.id_view_photo) as ImageView
    }

    var btm: Bitmap? = null
    var window: String? = null

    companion object {
        fun newInstance(btm: Bitmap?, window: String?): ViewPhotoFragment {
            val args = Bundle()
            args.putParcelable("photo", btm)
            args.putString("window", window)
            val fragment = ViewPhotoFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_photo, container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let { arg ->
            btm = arg.getParcelable(requireContext().resources.getString(R.string.key_photo))
            window = arg.getString(requireContext().resources.getString(R.string.key_window))
        }
        if (window == context?.resources?.getString(R.string.tag_window_x_rays)) {
            context?.getString(R.string.tag_view_photo_title_x_rays)?.let {
                (activity as? ViewPhotoFragment.OnBackPressedFromViewPhoto)?.onBackPressedFromViewPhoto(
                    it
                )
            }
            image.scaleType = ImageView.ScaleType.CENTER_CROP
        }
        if (window == context?.resources?.getString(R.string.tag_window_pictures_visit)) {
            context?.getString(R.string.tag_view_photo_title_pictures)?.let {
                (activity as? ViewPhotoFragment.OnBackPressedFromViewPhoto)?.onBackPressedFromViewPhoto(
                    it
                )
            }
            image.scaleType = ImageView.ScaleType.CENTER_INSIDE
        }
        image.setImageBitmap(btm)
    }

    interface OnBackPressedFromViewPhoto {
        fun onBackPressedFromViewPhoto(titleIsFragment: String)
    }

}