package com.dogoapp.ui.api

import com.dogoapp.ui.model.BreedImages
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface BreedImagesCall {
    @GET("breed/{name}/images")
    fun getUrls(@Path("name") breed: String) : Call<BreedImages>
}
