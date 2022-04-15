package com.dogoapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dogoapp.api.ApiCall
import com.dogoapp.api.ApiCallState
import com.dogoapp.ui.api.BreedsCall
import com.dogoapp.ui.model.Breeds

class BreedsViewModel(private val dataCall: BreedsCall) : ViewModel() {
    private val apiCall: ApiCall<Breeds> = ApiCall()

    fun getDataFormState(): LiveData<ApiCallState<Breeds>> {
        return apiCall.getDataFormState()
    }

    fun getBreeds() {
        apiCall.apiCall(dataCall.getBreeds())
    }
}
