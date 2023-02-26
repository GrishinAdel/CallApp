package com.adelvanchik.callapp.entity

import android.graphics.Bitmap

data class Contact(var icon: Bitmap? = null, val name: String, val number: String)
