package com.mico.obic.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class historyModel (
    var namaJadwal: String? = null,
    var waktuJawal: String? = null,
    var isGetar: Boolean? = false,
    var status: String? = null,
    var createdAt: String? = null
) : Parcelable