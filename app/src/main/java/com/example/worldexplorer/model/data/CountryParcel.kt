package com.example.worldexplorer.model.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class CountryParcel() : Parcelable {

    lateinit var name: String

    @SerializedName("flag")
    lateinit var flagPath: String

    @SerializedName("alpha3Code")
    lateinit var code: String

    lateinit var numericCode: String

    constructor(countryName: String, flag: String, countryCode: String) : this() {
        name = countryName
        flagPath = flag
        code = countryCode
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