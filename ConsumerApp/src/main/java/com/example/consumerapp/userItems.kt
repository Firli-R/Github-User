package com.example.consumerapp


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class userItems (
    var username :String? = null,
    var id :Int = 0,
    var ava_url : String? = null
): Parcelable