package com.example.satan.splitmoney

import android.os.Parcel
import android.os.Parcelable
import android.widget.ArrayAdapter

/**
 * Some useful things
 */

data class ListItem(val name: String, val amount: Int) : Parcelable {

    private constructor(source: Parcel) : this(source.readString(), source.readInt())

    companion object {
        @JvmField final val CREATOR: Parcelable.Creator<ListItem> = object : Parcelable.Creator<ListItem> {
            override fun createFromParcel(source: Parcel?): ListItem {
                if (source == null) {
                    throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
                return ListItem(source)
            }

            override fun newArray(size: Int): Array<out ListItem> {
                return Array(size) { ListItem("", 0) }
            }
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(name)
        dest?.writeInt(amount)
    }

    override fun toString(): String = "$name: $amount"
}
