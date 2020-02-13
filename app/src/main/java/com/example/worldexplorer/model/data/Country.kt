package com.example.worldexplorer.model.data

import android.os.Parcel
import android.os.Parcelable

class Country: Parcelable{

    var countryName: String
    var countryFlagPath: String

    constructor(name: String, flagImagePath: String){
        countryName = name
        countryFlagPath = flagImagePath
    }

    //--------------- parcel related -----------------//

    @Suppress("UNREACHABLE_CODE")
    constructor() : this(
        TODO("name"),
        TODO("flagImagePath")
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {}

    override fun describeContents(): Int { return 0 }

    companion object CREATOR : Parcelable.Creator<Country> {
        override fun createFromParcel(parcel: Parcel): Country {
            return Country()
        }

        override fun newArray(size: Int): Array<Country?> {
            return arrayOfNulls(size)
        }
    }
}