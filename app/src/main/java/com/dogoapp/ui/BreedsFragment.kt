package com.dogoapp.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import com.dogoapp.R
import com.dogoapp.api.ApiClient
import com.dogoapp.databinding.FragmentBreedsBinding
import com.dogoapp.ui.api.BreedsCall
import com.dogoapp.ui.list.BreedsAdapter
import androidx.recyclerview.widget.DividerItemDecoration

class BreedsFragment : Fragment(R.layout.fragment_breeds) {

    lateinit var binding: FragmentBreedsBinding
    private var viewModel: BreedsViewModel? = null
    private var adapter: BreedsAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBreedsBinding.bind(view)
        viewModel = BreedsViewModel(ApiClient.getClient(BreedsCall::class.java))

        val listListener: BreedsAdapter.OnClickListener = object : BreedsAdapter.OnClickListener {
            override fun onClick(breed: String) {
                binding.search.text.clear()
                val bundle = Bundle()
                bundle.putString(BreedImagesFragment.ARG_BREED, breed)
                findNavController(requireView()).navigate(
                    R.id.action_nav_breeds_to_nav_images,
                    bundle
                )
            }
        }

        binding.search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p: Editable?) {
                if (adapter != null) {
                    adapter!!.filter.filter(p.toString())
                }
            }
        })

        viewModel!!.getDataFormState().observe(this) { state ->
            binding.loading.visibility = if (state.isLoading()) View.VISIBLE else View.GONE
            binding.noConn.visibility = if (state.hasConn()) View.GONE else View.VISIBLE

            if (state.hasConn() && !state.isLoading()) {
                if (state.getData() == null || state.getData()!!.breeds.isNullOrEmpty()) {
                    binding.msjNoDogs.visibility = View.VISIBLE
                    binding.breedsList.visibility = View.GONE
                } else {
                    binding.msjNoDogs.visibility = View.GONE
                    binding.breedsList.visibility = View.VISIBLE
                    binding.breedsList.addItemDecoration(
                        DividerItemDecoration(
                            binding.breedsList.context,
                            DividerItemDecoration.VERTICAL
                        )
                    )
                    adapter = BreedsAdapter(ArrayList(state.getData()!!.breeds!!), listListener)
                    binding.breedsList.adapter = adapter
                    binding.search.isEnabled = true
                }
            } else {
                binding.msjNoDogs.visibility = View.GONE
                binding.breedsList.visibility = View.GONE
            }
        }

        binding.noConn.setOnClickListener {
            viewModel!!.getBreeds()
        }

        viewModel!!.getBreeds()
    }
}