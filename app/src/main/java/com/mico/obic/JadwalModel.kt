package com.mico.obic

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class JadwalModel (
    var namaJadwal: String? = null,
    var waktuJawal: String? = null,
    var status: String? = null,
    var createdAt: String? = null
    ) : Parcelable