package com.king.kvcache.cache

import android.content.Context
import android.os.Parcelable
import com.tencent.mmkv.MMKV

/**
 * 基于 [MMKV] 实现的键值对缓存
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
internal class MMKVCache(context: Context) : Cache() {

    private val cache by lazy { MMKV.defaultMMKV() }

    init {
        MMKV.initialize(context.applicationContext)
    }

    override fun put(key: String, value: Float?) {
        value?.let {
            cache.encode(key, it)
        } ?: remove(key)
    }

    override fun put(key: String, value: Int?) {
        value?.let {
            cache.encode(key, it)
        } ?: remove(key)
    }

    override fun put(key: String, value: Double?) {
        value?.let {
            cache.encode(key, it)
        } ?: remove(key)
    }

    override fun put(key: String, value: Long?) {
        value?.let {
            cache.encode(key, it)
        } ?: remove(key)
    }

    override fun put(key: String, value: Boolean?) {
        value?.let {
            cache.encode(key, it)
        } ?: remove(key)
    }

    override fun put(key: String, value: String?) {
        value?.let {
            cache.encode(key, it)
        } ?: remove(key)
    }

    override fun put(key: String, value: Set<String>?) {
        value?.let {
            cache.encode(key, it)
        } ?: remove(key)
    }

    override fun put(key: String, value: ByteArray?) {
        value?.let {
            cache.encode(key, it)
        } ?: remove(key)
    }

    override fun put(key: String, value: Parcelable?) {
        value?.let {
            cache.encode(key, it)
        } ?: remove(key)
    }

    override fun getFloat(key: String, defValue: Float): Float {
        return cache.decodeFloat(key, defValue)
    }

    override fun getInt(key: String, defValue: Int): Int {
        return cache.decodeInt(key, defValue)
    }

    override fun getDouble(key: String, defValue: Double): Double {
        return cache.decodeDouble(key, defValue)
    }

    override fun getLong(key: String, defValue: Long): Long {
        return cache.decodeLong(key, defValue)
    }

    override fun getBoolean(key: String, defValue: Boolean): Boolean {
        return cache.decodeBool(key, defValue)
    }

    override fun getString(key: String, defValue: String?): String? {
        return cache.decodeString(key, defValue)
    }

    override fun getStringSet(key: String, defValue: Set<String>?): Set<String>? {
        return cache.decodeStringSet(key, defValue)
    }

    override fun getByteArray(key: String, defValue: ByteArray?): ByteArray? {
        return cache.decodeBytes(key, defValue)
    }

    override fun <T : Parcelable> getParcelable(key: String, tClass: Class<T>): T? {
        return cache.decodeParcelable(key, tClass)
    }

    override fun <T : Parcelable> getParcelable(key: String, tClass: Class<T>, defValue: T?): T? {
        return cache.decodeParcelable(key, tClass, defValue)
    }


    override fun remove(key: String) {
        cache.remove(key)
    }
}

