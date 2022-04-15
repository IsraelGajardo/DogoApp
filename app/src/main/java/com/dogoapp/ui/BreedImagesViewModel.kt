package com.dogoapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dogoapp.api.ApiCall
import com.dogoapp.api.ApiCallState
import com.dogoapp.ui.api.BreedImagesCall
import com.dogoapp.ui.api.BreedsCall
import com.dogoapp.ui.model.BreedImages
import com.dogoapp.ui.model.Breeds

class BreedImagesViewModel(private val dataCall: BreedImagesCall) : ViewModel() {
    private val apiCall: ApiCall<BreedImages> = ApiCall()

    fun getDataFormState(): LiveData<ApiCallState<BreedImages>> {
        return apiCall.getDataFormState()
    }

    fun getImages(breed: String) {
        apiCall.apiCall(dataCall.getUrls(breed))
    }
}
