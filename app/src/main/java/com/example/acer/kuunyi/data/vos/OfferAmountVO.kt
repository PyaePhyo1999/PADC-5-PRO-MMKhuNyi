package com.example.acer.kuunyi.data.vos

import com.google.gson.annotations.SerializedName

/**
 * Created by Acer on 8/2/2018.
 */
class OfferAmountVO {
    @SerializedName("amount")
    var amount: Int? = null

    @SerializedName("duration")
    var duration: String? = null
}