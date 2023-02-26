package com.king.kvcache.cache

import android.content.Context
import android.content.SharedPreferences
import android.os.Parcelable
import android.util.Base64
import com.king.kvcache.util.ParcelableUtil

/**
 * 基于 [SharedPreferences] 实现的键值对缓存
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
internal class SharedPreferencesCache(context: Context) : Cache() {

    private val applicationContext = context.applicationContext

    private val cache by lazy {
        applicationContext.getSharedPreferences(getProvider(), Context.MODE_PRIVATE)
    }

    override fun put(key: String, value: Float?) {
        value?.let {
            cache.edit().putFloat(key, it).apply()
        } ?: remove(key, false)
    }

    override fun put(key: String, value: Int?) {
        value?.let {
            cache.edit().putInt(key, it).apply()
        } ?: remove(key, false)
    }

    override fun put(key: String, value: Double?) {
        value?.let {
            cache.edit().putFloat(key, it.toFloat()).apply()
        } ?: remove(key, false)
    }

    override fun put(key: String, value: Long?) {
        value?.let {
            cache.edit().putLong(key, it).apply()
        } ?: remove(key, false)
    }

    override fun put(key: String, value: Boolean?) {
        value?.let {
            cache.edit().putBoolean(key, it).apply()
        } ?: remove(key, false)
    }

    override fun put(key: String, value: String?) {
        value?.let {
            cache.edit().putString(key, it).apply()
        } ?: remove(key, false)
    }

    override fun put(key: String, value: Set<String>?) {
        value?.let {
            cache.edit().putStringSet(key, it).apply()
        } ?: remove(key, false)
    }

    override fun put(key: String, value: ByteArray?) {
        value?.let {
            runCatching {
                // 由于SharedPreferences不支持 ByteArray类型，这里特殊处理
                cache.edit()
                    .putString(byteArrayKey(key), Base64.encodeToString(it, Base64.DEFAULT))
                    .apply()
            }
        } ?: remove(byteArrayKey(key), false)
    }

    override fun put(key: String, value: Parcelable?) {
        value?.let {
            runCatching {
                val bytes = ParcelableUtil.parcelableToByteArray(value)
                // 由于DataStore不支持 Parcelable类型，这里特殊处理
                cache.edit()
                    .putString(parcelableKey(key), Base64.encodeToString(bytes, Base64.DEFAULT))
                    .apply()
            }
        } ?: remove(parcelableKey(key), false)
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
        return cache.getString(byteArrayKey(key), null)?.let {
            runCatching {
                Base64.decode(it, Base64.DEFAULT)
            }.getOrDefault(defValue)
        } ?: defValue
    }

    override fun <T : Parcelable> getParcelable(key: String, tClass: Class<T>): T? {
        return getParcelable(key, tClass, null)
    }

    override fun <T : Parcelable> getParcelable(key: String, tClass: Class<T>, defValue: T?): T? {
        return cache.getString(parcelableKey(key), null)?.let {
            try {
                val bytes = Base64.decode(it, Base64.DEFAULT)
                ParcelableUtil.byteArrayToParcelable(applicationContext, bytes)
            } catch (e: Exception) {
                defValue
            }
        } ?: defValue
    }

    override fun remove(key: String) {
        remove(key, true)
    }

    private fun remove(key: String, flag: Boolean) {
        cache.edit().also {
            it.remove(key)
            if (flag) {
                it.remove(byteArrayKey(key))
                it.remove(parcelableKey(key))
            }
        }.apply()
    }

    override fun clear() {
        cache.edit().clear().apply()
    }

    /**
     * ByteArray Key
     */
    private fun byteArrayKey(key: String) = "${BYTE_ARRAY_FLAG}_$key"

    /**
     * Parcelable Key
     */
    private fun parcelableKey(key: String) = "${PARCELABLE_FLAG}_$key"

    companion object {
        private const val BYTE_ARRAY_FLAG = "BYTE_ARRAY"

        private const val PARCELABLE_FLAG = "PARCELABLE_FLAG"
    }
}

