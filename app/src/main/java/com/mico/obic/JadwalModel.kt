package com.mico.obic

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class JadwalModel (
    var jdlJadwal: String? = null,
    var waktuJdwl: String? = null,
    var statusLogo: Int = 0,
    var status: String? = null
    ) : Parcelable