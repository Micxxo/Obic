package com.mico.obic.models

data class updateUser(    var email: String? = null,
                     var password: String? = null,
                     var uuID: String? = null,
                     var level: Int? = null,
                     var point: Int? = null,
                     var jdwlDone: Int? = null,
)