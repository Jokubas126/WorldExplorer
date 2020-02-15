package com.example.worldexplorer.model.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class CountryParcel() : Parcelable {

    @SerializedName("name")
    lateinit var countryName: String

    @SerializedName("flag")
    lateinit var countryFlagPath: String

    constructor(name: String, flagImagePath: String) : this() {
        countryName = name
        countryFlagPath = flagImagePath
    }

    //--------------- parcel related -----------------//

    override fun writeToParcel(parcel: Parcel, flags: Int) {}

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CountryParcel> {
        override fun createFromParcel(parcel: Parcel): CountryParcel {
            return CountryParcel()
        }

        override fun newArray(size: Int): Array<CountryParcel?> {
            return arrayOfNulls(size)
        }
    }
}