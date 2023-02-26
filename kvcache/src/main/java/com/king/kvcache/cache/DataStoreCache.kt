package com.king.kvcache.cache

import android.content.Context
import android.os.Parcelable
import android.util.Base64
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.king.kvcache.util.ParcelableUtil
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

/**
 * 基于 [DataStore] 实现的键值对缓存
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
internal class DataStoreCache(context: Context) : Cache() {

    private val applicationContext = context.applicationContext

    private val cache by lazy { applicationContext.dataStoreCache }

    override fun put(key: String, value: Float?) {
        value?.let {
            runBlocking {
                cache.edit { it[floatPreferencesKey(key)] = value }
            }
        } ?: remove(floatPreferencesKey(key))
    }

    override fun put(key: String, value: Int?) {
        value?.let {
            runBlocking {
                cache.edit { it[intPreferencesKey(key)] = value }
            }
        } ?: remove(intPreferencesKey(key))
    }

    override fun put(key: String, value: Double?) {
        value?.let {
            runBlocking {
                cache.edit { it[doublePreferencesKey(key)] = value }
            }
        } ?: remove(doublePreferencesKey(key))
    }

    override fun put(key: String, value: Long?) {
        value?.let {
            runBlocking {
                cache.edit { it[longPreferencesKey(key)] = value }
            }
        } ?: remove(longPreferencesKey(key))
    }

    override fun put(key: String, value: Boolean?) {
        value?.let {
            runBlocking {
                cache.edit { it[booleanPreferencesKey(key)] = value }
            }
        } ?: remove(booleanPreferencesKey(key))
    }

    override fun put(key: String, value: String?) {
        value?.let {
            runBlocking {
                cache.edit { it[stringPreferencesKey(key)] = value }
            }
        } ?: remove(stringPreferencesKey(key))
    }

    override fun put(key: String, value: Set<String>?) {
        value?.let {
            runBlocking {
                cache.edit { it[stringSetPreferencesKey(key)] = value }
            }
        } ?: remove(stringSetPreferencesKey(key))
    }

    override fun put(key: String, value: ByteArray?) {
        value?.let {
            runBlocking {
                cache.edit {
                    runCatching {
                        // 由于DataStore不支持 ByteArray类型，这里特殊处理
                        it[byteArrayPreferencesKey(key)] =
                            Base64.encodeToString(value, Base64.DEFAULT)
                    }
                }
            }
        } ?: remove(byteArrayPreferencesKey(key))
    }

    override fun put(key: String, value: Parcelable?) {
        value?.let {
            runBlocking {
                cache.edit {
                    runCatching {
                        // 由于DataStore不支持 Parcelable类型，这里特殊处理
                        val bytes = ParcelableUtil.parcelableToByteArray(value)
                        it[parcelablePreferencesKey(key)] =
                            Base64.encodeToString(bytes, Base64.DEFAULT)
                    }
                }
            }
        } ?: remove(parcelablePreferencesKey(key))
    }

    override fun getFloat(key: String, defValue: Float): Float {
        var value = defValue
        runBlocking {
            cache.data.first {
                runCatching {
                    it[floatPreferencesKey(key)]?.run {
                        value = this
                    }
                }
                true
            }
        }
        return value
    }

    override fun getInt(key: String, defValue: Int): Int {
        var value = defValue
        runBlocking {
            cache.data.first {
                runCatching {
                    it[intPreferencesKey(key)]?.run {
                        value = this
                    }
                }
                true
            }
        }
        return value
    }

    override fun getDouble(key: String, defValue: Double): Double {
        var value = defValue
        runBlocking {
            cache.data.first {
                runCatching {
                    it[doublePreferencesKey(key)]?.run {
                        value = this
                    }
                }
                true
            }
        }
        return value
    }

    override fun getLong(key: String, defValue: Long): Long {
        var value = defValue
        runBlocking {
            cache.data.first {
                runCatching {
                    it[longPreferencesKey(key)]?.run {
                        value = this
                    }
                }
                true
            }
        }
        return value
    }

    override fun getBoolean(key: String, defValue: Boolean): Boolean {
        var value = defValue
        runBlocking {
            cache.data.first {
                runCatching {
                    it[booleanPreferencesKey(key)]?.run {
                        value = this
                    }
                }
                true
            }
        }
        return value
    }

    override fun getString(key: String, defValue: String?): String? {
        var value = defValue
        runBlocking {
            cache.data.first {
                runCatching {
                    it[stringPreferencesKey(key)]?.run {
                        value = this
                    }
                }
                true
            }
        }
        return value
    }

    override fun getStringSet(key: String, defValue: Set<String>?): Set<String>? {
        var value = defValue
        runBlocking {
            cache.data.first {
                runCatching {
                    it[stringSetPreferencesKey(key)]?.run {
                        value = this
                    }
                }
                true
            }
        }
        return value
    }

    override fun getByteArray(key: String, defValue: ByteArray?): ByteArray? {
        var value = defValue
        runBlocking {
            cache.data.first {
                runCatching {
                    it[byteArrayPreferencesKey(key)]?.let {
                        value = Base64.decode(it, Base64.DEFAULT)
                    }
                }
                true
            }
        }
        return value
    }

    override fun <T : Parcelable> getParcelable(key: String, tClass: Class<T>): T? {
        return getParcelable(key, tClass, null)
    }

    override fun <T : Parcelable> getParcelable(key: String, tClass: Class<T>, defValue: T?): T? {
        var value = defValue
        runBlocking {
            cache.data.first {
                runCatching {
                    it[parcelablePreferencesKey(key)]?.let {
                        val bytes = Base64.decode(it, Base64.DEFAULT)
                        value = ParcelableUtil.byteArrayToParcelable(applicationContext, bytes)
                    }
                }
                true
            }
        }
        return value
    }

    override fun remove(key: String) {
        runBlocking {
            cache.edit {
                it.remove(intPreferencesKey(key))
                it.remove(floatPreferencesKey(key))
                it.remove(doublePreferencesKey(key))
                it.remove(longPreferencesKey(key))
                it.remove(booleanPreferencesKey(key))
                it.remove(stringPreferencesKey(key))
                it.remove(stringSetPreferencesKey(key))
                it.remove(byteArrayPreferencesKey(key))
                it.remove(parcelablePreferencesKey(key))
            }
        }
    }

    private fun <T> remove(key: Preferences.Key<T>) {
        runBlocking {
            cache.edit {
                it.remove(key)
            }
        }
    }

    override fun clear() {
        runBlocking {
            cache.edit {
                it.clear()
            }
        }
    }

    /**
     * ByteArray Key
     */
    private fun byteArrayPreferencesKey(key: String): Preferences.Key<String> {
        return stringPreferencesKey("${BYTE_ARRAY_FLAG}_$key")
    }

    /**
     * Parcelable Key
     */
    private fun parcelablePreferencesKey(key: String): Preferences.Key<String> {
        return stringPreferencesKey("${PARCELABLE_FLAG}_$key")
    }

    companion object {
        private const val BYTE_ARRAY_FLAG = "BYTE_ARRAY"

        private const val PARCELABLE_FLAG = "PARCELABLE_FLAG"
    }
}

