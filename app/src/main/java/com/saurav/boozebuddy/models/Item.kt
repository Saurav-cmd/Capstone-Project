package com.saurav.boozebuddy.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(val id: Int, val text: String, val imageRes: Int) : Parcelable

