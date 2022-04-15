package com.dogoapp.ui.model

import com.google.gson.annotations.SerializedName

class BreedImages {
    @SerializedName("message")
    var urls: List<String>? = null

    @SerializedName("status")
    var status: String? = null
}
