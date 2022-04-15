package com.dogoapp.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.dogoapp.R
import com.dogoapp.api.ApiClient
import com.dogoapp.databinding.FragmentBreedImagesBinding
import com.dogoapp.ui.api.BreedImagesCall
import com.dogoapp.ui.list.BreedImagesAdapter

class BreedImagesFragment : Fragment(R.layout.fragment_breed_images) {
    companion object {
        const val ARG_BREED = "breed"
    }

    private var argBreed: String? = null
    lateinit var binding: FragmentBreedImagesBinding
    private var viewModel: BreedImagesViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            argBreed = it.getString(ARG_BREED)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBreedImagesBinding.bind(view)
        binding.breedName.text = argBreed
        viewModel = BreedImagesViewModel(ApiClient.getClient(BreedImagesCall::class.java))

        val listListener: BreedImagesAdapter.OnClickListener = object : BreedImagesAdapter.OnClickListener {
            override fun onClick(url: String) {
                val bundle = Bundle()
                bundle.putString(ImageExpandFragment.ARG_URL, url)
                Navigation.findNavController(requireView()).navigate(
                    R.id.action_nav_images_to_nav_expand,
                    bundle
                )
            }
        }

        viewModel!!.getDataFormState().observe(this) { state ->
            binding.loading.visibility = if (state.isLoading()) View.VISIBLE else View.GONE
            binding.noConn.visibility = if (state.hasConn()) View.GONE else View.VISIBLE

            if (state.hasConn() && !state.isLoading()) {
                if (state.getData() == null || state.getData()!!.urls.isNullOrEmpty()) {
                    binding.msjNoImages.visibility = View.VISIBLE
                    binding.imagesList.visibility = View.GONE
                } else {
                    binding.msjNoImages.visibility = View.GONE
                    binding.imagesList.visibility = View.VISIBLE
                    binding.imagesList.adapter = BreedImagesAdapter(context!!, state.getData()!!.urls!!, listListener)
                }
            } else {
                binding.msjNoImages.visibility = View.GONE
                binding.imagesList.visibility = View.GONE
            }
        }

        binding.noConn.setOnClickListener {
            viewModel!!.getImages(argBreed!!)
        }

        viewModel!!.getImages(argBreed!!)
    }
}