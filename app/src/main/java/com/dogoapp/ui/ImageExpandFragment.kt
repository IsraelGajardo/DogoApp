package com.dogoapp.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.dogoapp.R
import com.dogoapp.databinding.FragmentImageExpandBinding

class ImageExpandFragment : Fragment(R.layout.fragment_image_expand) {
    companion object {
        const val ARG_URL = "url"
    }
    lateinit var binding: FragmentImageExpandBinding
    private var argUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            argUrl = it.getString(ARG_URL)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentImageExpandBinding.bind(view)
        Glide.with(context).load(argUrl).into(binding.image)
    }

}