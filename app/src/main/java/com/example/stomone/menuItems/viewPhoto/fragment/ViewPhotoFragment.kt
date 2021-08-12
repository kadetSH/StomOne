package com.example.stomone.menuItems.viewPhoto.fragment

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.stomone.Constants
import com.example.stomone.R
import com.example.stomone.TitleController
import com.example.stomone.databinding.FragmentPhotoBinding
import dagger.android.support.DaggerFragment

class ViewPhotoFragment : DaggerFragment() {

    private lateinit var image : ImageView
    private lateinit var binding: FragmentPhotoBinding
    var btm: Bitmap? = null
    var window: String? = null

    companion object {
        fun newInstance(btm: Bitmap?, window: String?): ViewPhotoFragment {
            val args = Bundle()
            args.putParcelable(Constants.PHOTO, btm)
            args.putString(Constants.WINDOW, window)
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
        binding = FragmentPhotoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        image = binding.idViewPhoto
        arguments?.let { arg ->
            btm = arg.getParcelable(Constants.PHOTO)
            window = arg.getString(Constants.WINDOW)
        }
        if (window == context?.resources?.getString(R.string.tag_window_x_rays)) {
            context?.getString(R.string.tag_view_photo_title_x_rays)?.let {
                (activity as? TitleController)?.setTitle(
                    it
                )
            }
            image.scaleType = ImageView.ScaleType.CENTER_CROP
        }
        if (window == context?.resources?.getString(R.string.tag_window_pictures_visit)) {
            context?.getString(R.string.tag_view_photo_title_pictures)?.let {
                (activity as? TitleController)?.setTitle(
                    it
                )
            }
            image.scaleType = ImageView.ScaleType.CENTER_INSIDE
        }
        image.setImageBitmap(btm)
    }

}