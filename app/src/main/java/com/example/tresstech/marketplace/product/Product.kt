package com.example.tresstech.marketplace.product

import android.os.Parcel
import android.os.Parcelable

data class Product(
    val productPic : Int,
    val productName : String,
    val productSize : String,
    val productPrice : String,
    val productDesc : String
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(productPic)
        parcel.writeString(productName)
        parcel.writeString(productSize)
        parcel.writeString(productPrice)
        parcel.writeString(productDesc)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}
