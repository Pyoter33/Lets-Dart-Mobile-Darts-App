package com.example.letsdart.models.general

import android.os.Parcel
import android.os.Parcelable
import kotlinx.serialization.Serializable

@Serializable
data class Rules(
    val startScore: Int,
    val numberOfLegs: Int,
    val numberOfSets: Int,
    val checkout: Int,
    val winCondition: Int,
    val deciderType: Int,
    val rematch: Boolean
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(startScore)
        parcel.writeInt(numberOfLegs)
        parcel.writeInt(numberOfSets)
        parcel.writeInt(checkout)
        parcel.writeInt(winCondition)
        parcel.writeInt(deciderType)
        parcel.writeByte(if (rematch) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Rules> {
        override fun createFromParcel(parcel: Parcel): Rules {
            return Rules(parcel)
        }

        override fun newArray(size: Int): Array<Rules?> {
            return arrayOfNulls(size)
        }
    }
}
