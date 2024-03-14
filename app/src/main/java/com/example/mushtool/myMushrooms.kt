package com.example.mushtool

import com.google.firebase.Timestamp


data class MyMushroom(
    var mId: Long? = null,
    var mKind: String? = null,
    var mComment: String? = null,
    var mDate: Timestamp? = null,
    var mLatitude: Double? = null,
    var mLongitude: Double? = null,
    var mPhotoId: String? = null,
    var mPhotoUri: String? = null,
    val userId: String? = null,
    )

enum class menuMymushrooms {
    SAVEIMAGE,
    MAPA
}