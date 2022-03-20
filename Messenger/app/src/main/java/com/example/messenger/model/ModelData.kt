package com.example.messenger.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelData(
    val id: Long,
    val name: String
): Parcelable