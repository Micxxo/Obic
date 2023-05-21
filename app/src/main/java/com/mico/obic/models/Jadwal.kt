package com.mico.obic.models

data class Jadwal (
    var namaJadwal: String? = null,
    var waktuJawal: String? = null,
    var isGetar: Boolean? = false,
    var status: String? = null,
    var createdAt: String? = null
)