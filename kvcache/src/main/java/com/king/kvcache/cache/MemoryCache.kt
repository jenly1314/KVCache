package com.king.kvcache.cache

import android.os.Parcelable
import java.util.concurrent.ConcurrentHashMap

/**
 * 基于 [Map] 实现的键值对缓存；主要应用场景：只需使用内存进行缓存即可满足需求；如：单元测试
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
internal class MemoryCache : Cache() {

    private val cache by lazy { ConcurrentHashMap<String, Any>() }

    override fun put(key: String, value: Float?) {
        value?.let {
            cache[key] = it
        } ?: remove(key)
    }

    override fun put(key: String, value: Int?) {
        value?.let {
            cache[key] = it
        } ?: remove(key)
    }

    override fun put(key: String, value: Double?) {
        value?.let {
            cache[key] = it
        } ?: remove(key)
    }

    override fun put(key: String, value: Long?) {
        value?.let {
            cache[key] = it
        } ?: remove(key)
    }

    override fun put(key: String, value: Boolean?) {
        value?.let {
            cache[key] = it
        } ?: remove(key)
    }

    override fun put(key: String, value: String?) {
        value?.let {
            cache[key] = it
        } ?: remove(key)
    }

    override fun put(key: String, value: Set<String>?) {
        value?.let {
            cache[key] = it
        } ?: remove(key)
    }

    override fun put(key: String, value: ByteArray?) {
        value?.let {
            cache[key] = it
        } ?: remove(key)
    }

    override fun put(key: String, value: Parcelable?) {
        value?.let {
            cache[key] = it
        } ?: remove(key)
    }

    override fun getFloat(key: String, defValue: Float): Float {
        return cache[key] as? Float ?: defValue
    }

    override fun getInt(key: String, defValue: Int): Int {
        return cache[key] as? Int ?: defValue
    }

    override fun getDouble(key: String, defValue: Double): Double {
        return cache[key] as? Double ?: defValue
    }

    override fun getLong(key: String, defValue: Long): Long {
        return cache[key] as? Long ?: defValue
    }

    override fun getBoolean(key: String, defValue: Boolean): Boolean {
        return cache[key] as? Boolean ?: defValue
    }

    override fun getString(key: String, defValue: String?): String? {
        return cache[key] as? String ?: defValue
    }

    override fun getStringSet(key: String, defValue: Set<String>?): Set<String>? {
        return cache[key] as? Set<String> ?: defValue
    }

    override fun getByteArray(key: String, defValue: ByteArray?): ByteArray? {
        return cache[key] as? ByteArray ?: defValue
    }

    override fun <T : Parcelable> getParcelable(key: String, tClass: Class<T>): T? {
        return cache[key] as? T
    }

    override fun <T : Parcelable> getParcelable(key: String, tClass: Class<T>, defValue: T?): T? {
        return cache[key] as? T ?: defValue
    }

    override fun remove(key: String) {
        cache.remove(key)
    }

    override fun clear() {
        cache.clear()
    }
}

