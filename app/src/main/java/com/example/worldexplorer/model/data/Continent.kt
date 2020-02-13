package com.example.worldexplorer.model.data

import android.graphics.Bitmap
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup

class Continent(title: String?,
                val titleColor: Int,
                val bitmap: Bitmap?,
                items: MutableList<Country>?) :
    ExpandableGroup<Country>(title, items) {

    val continentTitle = title
}