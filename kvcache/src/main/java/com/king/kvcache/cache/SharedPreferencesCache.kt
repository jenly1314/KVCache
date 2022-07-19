package com.king.kvcache.cache

import android.content.Context
import android.os.Parcelable
import android.content.SharedPreferences
import java.lang.NullPointerException

/**
 * 基于 [SharedPreferences] 实现的键值对缓存
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
internal class SharedPreferencesCache(context: Context) : Cache() {

    private val map by lazy { context.applicationContext.getSharedPreferences("sp_cache", 0) }

    init {
        if (map == null) {
            throw NullPointerException()
        }
    }

    override fun put(key: String, value: Float?) {
        value?.let {
            map.edit().putFloat(key, it).apply()
        } ?: remove(key)
    }

    override fun put(key: String, value: Int?) {
        value?.let {
            map.edit().putInt(key, it).apply()
        } ?: remove(key)
    }

    override fun put(key: String, value: Double?) {
        value?.let {
            map.edit().putFloat(key, it.toFloat()).apply()
        } ?: remove(key)
    }

    override fun put(key: String, value: Long?) {
        value?.let {
            map.edit().putLong(key, it).apply()
        } ?: remove(key)
    }

    override fun put(key: String, value: Boolean?) {
        value?.let {
            map.edit().putBoolean(key, it).apply()
        } ?: remove(key)
    }

    override fun put(key: String, value: String?) {
        value?.let {
            map.edit().putString(key, it).apply()
        } ?: remove(key)
    }

    override fun put(key: String, value: Set<String>?) {
        value?.let {
            map.edit().putStringSet(key, it).apply()
        } ?: remove(key)
    }

    override fun put(key: String, value: ByteArray?) {
        throw IllegalArgumentException("Illegal value type ByteArray for key \"$key\"")
    }

    override fun put(key: String, value: Parcelable?) {
        throw IllegalArgumentException("Illegal value type Parcelable for key \"$key\"")
    }

    override fun getFloat(key: String, defValue: Float): Float {
        return map.getFloat(key, defValue)
    }

    override fun getInt(key: String, defValue: Int): Int {
        return map.getInt(key, defValue)
    }

    override fun getDouble(key: String, defValue: Double): Double {
        return map.getFloat(key, defValue.toFloat()).toDouble()
    }

    override fun getLong(key: String, defValue: Long): Long {
        return map.getLong(key, defValue)
    }

    override fun getBoolean(key: String, defValue: Boolean): Boolean {
        return map.getBoolean(key, defValue)
    }

    override fun getString(key: String, defValue: String?): String? {
        return map.getString(key, defValue)
    }

    override fun getStringSet(key: String, defValue: Set<String>?): Set<String>? {
        return map.getStringSet(key, defValue)
    }

    override fun getByteArray(key: String, defValue: ByteArray?): ByteArray? {
        return defValue
    }

    override fun <T : Parcelable> getParcelable(key: String, tClass: Class<T>): T? {
        return getParcelable(key, tClass, null)
    }

    override fun <T : Parcelable> getParcelable(key: String, tClass: Class<T>, defValue: T?): T? {
        return defValue
    }

    override fun remove(key: String) {
        map.edit().remove(key).apply()
    }
}

