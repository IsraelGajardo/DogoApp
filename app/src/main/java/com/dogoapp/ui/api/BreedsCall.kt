package com.dogoapp.ui.api

import com.dogoapp.ui.model.Breeds
import retrofit2.Call
import retrofit2.http.GET

interface BreedsCall {
    @GET("breeds/list")
    fun getBreeds() : Call<Breeds>
}
