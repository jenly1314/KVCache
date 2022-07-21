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

    private val cache by lazy { context.applicationContext.getSharedPreferences("sp_cache", 0) }

    init {
        if (cache == null) {
            throw NullPointerException()
        }
    }

    override fun put(key: String, value: Float?) {
        value?.let {
            cache.edit().putFloat(key, it).apply()
        } ?: remove(key)
    }

    override fun put(key: String, value: Int?) {
        value?.let {
            cache.edit().putInt(key, it).apply()
        } ?: remove(key)
    }

    override fun put(key: String, value: Double?) {
        value?.let {
            cache.edit().putFloat(key, it.toFloat()).apply()
        } ?: remove(key)
    }

    override fun put(key: String, value: Long?) {
        value?.let {
            cache.edit().putLong(key, it).apply()
        } ?: remove(key)
    }

    override fun put(key: String, value: Boolean?) {
        value?.let {
            cache.edit().putBoolean(key, it).apply()
        } ?: remove(key)
    }

    override fun put(key: String, value: String?) {
        value?.let {
            cache.edit().putString(key, it).apply()
        } ?: remove(key)
    }

    override fun put(key: String, value: Set<String>?) {
        value?.let {
            cache.edit().putStringSet(key, it).apply()
        } ?: remove(key)
    }

    override fun put(key: String, value: ByteArray?) {
        throw IllegalArgumentException("Illegal value type ByteArray for key \"$key\"")
    }

    override fun put(key: String, value: Parcelable?) {
        throw IllegalArgumentException("Illegal value type Parcelable for key \"$key\"")
    }

    override fun getFloat(key: String, defValue: Float): Float {
        return cache.getFloat(key, defValue)
    }

    override fun getInt(key: String, defValue: Int): Int {
        return cache.getInt(key, defValue)
    }

    override fun getDouble(key: String, defValue: Double): Double {
        return cache.getFloat(key, defValue.toFloat()).toDouble()
    }

    override fun getLong(key: String, defValue: Long): Long {
        return cache.getLong(key, defValue)
    }

    override fun getBoolean(key: String, defValue: Boolean): Boolean {
        return cache.getBoolean(key, defValue)
    }

    override fun getString(key: String, defValue: String?): String? {
        return cache.getString(key, defValue)
    }

    override fun getStringSet(key: String, defValue: Set<String>?): Set<String>? {
        return cache.getStringSet(key, defValue)
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
        cache.edit().remove(key).apply()
    }
}

