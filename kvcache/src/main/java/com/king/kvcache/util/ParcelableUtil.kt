package com.king.kvcache.util

import android.content.Context
import android.os.Parcel
import android.os.Parcelable

/**
 * Parcelable Util
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
internal object ParcelableUtil {

    /**
     * Parcelable to ByteArray
     */
    fun parcelableToByteArray(parcelable: Parcelable): ByteArray {
        val parcel = Parcel.obtain()
        val bytes = parcel.let {
            it.setDataPosition(0)
            it.writeValue(parcelable)
            it.marshall()
        }
        parcel.recycle()
        return bytes
    }

    /**
     * ByteArray to Parcelable
     */
    fun <T : Parcelable> byteArrayToParcelable(context: Context, bytes: ByteArray): T? {
        val parcel = Parcel.obtain()
        parcel.unmarshall(bytes, 0, bytes.size)
        parcel.setDataPosition(0)
        val parcelable = parcel.readValue(context.classLoader) as? T
        parcel.recycle()
        return parcelable
    }

}