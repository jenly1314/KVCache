package com.king.kvcache.cache

import android.content.Context
import android.os.Parcelable
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

/**
 * 基于 [DataStore] 实现的键值对缓存
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
internal class DataStoreCache(context: Context) : Cache() {

    private val cache by lazy { context.applicationContext.dataStoreCache }

    init {
        context.applicationContext.dataStoreCache
    }


    override fun put(key: String, value: Float?) {
        value?.let {
            runBlocking {
                cache.edit { it[floatPreferencesKey(key)] = value }
            }
        } ?: remove(key)
    }

    override fun put(key: String, value: Int?) {
        value?.let {
            runBlocking {
                cache.edit { it[intPreferencesKey(key)] = value }
            }
        } ?: remove(key)
    }

    override fun put(key: String, value: Double?) {
        value?.let {
            runBlocking {
                cache.edit { it[doublePreferencesKey(key)] = value }
            }
        } ?: remove(key)
    }

    override fun put(key: String, value: Long?) {
        value?.let {
            runBlocking {
                cache.edit { it[longPreferencesKey(key)] = value }
            }
        } ?: remove(key)
    }

    override fun put(key: String, value: Boolean?) {
        value?.let {
            runBlocking {
                cache.edit { it[booleanPreferencesKey(key)] = value }
            }
        } ?: remove(key)
    }

    override fun put(key: String, value: String?) {
        value?.let {
            runBlocking {
                cache.edit { it[stringPreferencesKey(key)] = value }
            }
        } ?: remove(key)
    }

    override fun put(key: String, value: Set<String>?) {
        value?.let {
            runBlocking {
                cache.edit { it[stringSetPreferencesKey(key)] = value }
            }
        } ?: remove(key)
    }

    override fun put(key: String, value: ByteArray?) {
        throw IllegalArgumentException("Illegal value type ByteArray for key \"$key\"")
    }

    override fun put(key: String, value: Parcelable?) {
        throw IllegalArgumentException("Illegal value type Parcelable for key \"$key\"")
    }

    override fun getFloat(key: String, defValue: Float): Float {
        var value = defValue
        runBlocking {
            cache.data.first {
                try{
                    it[floatPreferencesKey(key)]?.run {
                        value = this
                    }
                }catch (e: Exception){

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
                try{
                    it[intPreferencesKey(key)]?.run {
                        value = this
                    }
                }catch (e: Exception){

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
                try{
                    it[doublePreferencesKey(key)]?.run {
                        value = this
                    }
                }catch (e: Exception){

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
                try{
                    it[longPreferencesKey(key)]?.run {
                        value = this
                    }
                }catch (e: Exception){

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
                try{
                    it[booleanPreferencesKey(key)]?.run {
                        value = this
                    }
                }catch (e: Exception){

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
                try{
                    it[stringPreferencesKey(key)]?.run {
                        value = this
                    }
                }catch (e: Exception){

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
                try{
                    it[stringSetPreferencesKey(key)]?.run {
                        value = this
                    }
                }catch (e: Exception){

                }
                true
            }
        }
        return value
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
        runBlocking {
            // 由于 DataStore 不支持 remove，所以这里都还原成默认值
            cache.edit {
                it[intPreferencesKey(key)] = 0
                it[floatPreferencesKey(key)] = 0F
                it[doublePreferencesKey(key)] = 0.0
                it[longPreferencesKey(key)] = 0L
                it[booleanPreferencesKey(key)] = false
                // （String 的默认值本应是 null，由于不能为 null，这里还原成 ""）
                it[stringPreferencesKey(key)] = ""
            }
        }
    }


}

