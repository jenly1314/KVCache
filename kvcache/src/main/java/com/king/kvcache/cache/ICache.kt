package com.king.kvcache.cache

import android.os.Parcelable

/**
 * 缓存接口
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
interface ICache {

    fun put(key: String, value: Float?)
    fun put(key: String, value: Int?)
    fun put(key: String, value: Double?)
    fun put(key: String, value: Long?)
    fun put(key: String, value: Boolean?)
    fun put(key: String, value: String?)
    fun put(key: String, value: Set<String>?)
    fun put(key: String, value: ByteArray?)
    fun put(key: String, value: Parcelable?)

    fun getFloat(key: String, defValue: Float) : Float
    fun getInt(key: String, defValue: Int) : Int
    fun getDouble(key: String, defValue: Double) : Double
    fun getLong(key: String, defValue: Long) : Long
    fun getBoolean(key: String, defValue: Boolean) : Boolean
    fun getString(key: String, defValue: String?) : String?
    fun getStringSet(key: String, defValue: Set<String>?) : Set<String>?
    fun getByteArray(key: String, defValue: ByteArray?) : ByteArray?

    fun <T: Parcelable> getParcelable(key: String, tClass: Class<T>) : T?
    fun <T: Parcelable> getParcelable(key: String, tClass: Class<T>, defValue: T?) : T?

    fun remove(key: String)
}