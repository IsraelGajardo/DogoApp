package com.dogoapp.ui.model

import com.google.gson.annotations.SerializedName

class Breeds {
    @SerializedName("message")
    var breeds: List<String>? = null

    @SerializedName("status")
    var status: String? = null
}
