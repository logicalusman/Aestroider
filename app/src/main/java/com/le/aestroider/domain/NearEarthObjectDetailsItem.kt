package com.le.aestroider.domain

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class NearEarthObjectDetailsItem(
    @DrawableRes val icon: Int,
    val headerText: String,
    @StringRes val subText: Int,
    val isHazardousItem: Boolean
) {
    // only applies to hazardous item
    @StringRes var hazardousItemHeaderText: Int? = null
}
