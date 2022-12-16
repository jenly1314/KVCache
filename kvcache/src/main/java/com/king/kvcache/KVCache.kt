package com.king.kvcache

import android.content.Context
import android.os.Parcelable
import androidx.annotation.StringDef
import com.king.kvcache.cache.*
import com.king.kvcache.cache.DataStoreCache
import com.king.kvcache.cache.MMKVCache
import com.king.kvcache.cache.SharedPreferencesCache

/**
 * KVCache：统一管理缓存键值
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
object KVCache : ICache, CacheProvider {

    /**
     * 缓存提供者
     */
    @StringDef(
        /**
         * 使用 MMKV 提供缓存实现
         */
        Provider.MMKV_CACHE,
        /**
         * 使用 DataStore 提供缓存实现
         */
        Provider.DATA_STORE_CACHE,
        /**
         * 使用 SharedPreferences 提供缓存实现
         */
        Provider.SHARED_PREFERENCES_CACHE,
        /**
         * 使用 Memory 提供缓存实现
         */
        Provider.MEMORY_CACHE
    )
    @Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FIELD, AnnotationTarget.FUNCTION)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    annotation class Provider {
        companion object {
            /**
             * 使用 MMKV 提供缓存实现
             */
            const val MMKV_CACHE = "MMKVCache"

            /**
             * 使用 DataStore 提供缓存实现
             */
            const val DATA_STORE_CACHE = "DataStoreCache"

            /**
             * 使用 SharedPreferences 提供缓存实现
             */
            const val SHARED_PREFERENCES_CACHE = "SharedPreferencesCache"

            /**
             * 使用 Memory 提供缓存实现
             */
            const val MEMORY_CACHE = "MemoryCache"

        }
    }

    /**
     * 是否初始化缓存
     */
    private var isInitializedCache = false

    /**
     * 缓存
     */
    private lateinit var cache: Cache

    /**
     * 初始化；建议在 Application 中进行初始化
     * @param context 上下文
     * @param provider 缓存提供者，参见 [Provider]；当 provider 为空或未知时，会自动决定缓存实现：优先级
     * 从高到低依次为： MMKV -> DataStore -> SharedPreferences -> Memory
     */
    @JvmOverloads
    fun initialize(context: Context, @Provider provider: String? = null) {
        initialize(obtainCache(context, provider))
    }


    /**
     * 初始化；建议在 Application 中进行初始化
     *
     * @param cache 缓存实现；如果 initialize(context, cacheProvider) 无法满足你的需求，你可以通过此函数来初始化缓存
     */
    @JvmOverloads
    fun initialize(cache: Cache) {
        this.cache = cache
        this.isInitializedCache = true
    }

    /**
     * 获取缓存对象
     */
    private fun obtainCache(context: Context, @Provider provider: String? = null): Cache {
        return when (provider) {
            Provider.MMKV_CACHE -> MMKVCache(context)
            Provider.DATA_STORE_CACHE -> DataStoreCache(context)
            Provider.SHARED_PREFERENCES_CACHE -> SharedPreferencesCache(context)
            Provider.MEMORY_CACHE -> MemoryCache()
            else -> try {
                MMKVCache(context)
            } catch (e: Throwable) {
                try {
                    DataStoreCache(context)
                } catch (e: Throwable) {
                    try {
                        SharedPreferencesCache(context)
                    } catch (e: Throwable) {
                        MemoryCache()
                    }
                }
            }
        }
    }

    override fun cacheProvider(): String {
        return if (isInitializedCache) cache.cacheProvider() else ""
    }

    override fun put(key: String, value: Float?) {
        cache.put(key, value)
    }

    override fun put(key: String, value: Int?) {
        cache.put(key, value)
    }

    override fun put(key: String, value: Double?) {
        cache.put(key, value)
    }

    override fun put(key: String, value: Long?) {
        cache.put(key, value)
    }

    override fun put(key: String, value: Boolean?) {
        cache.put(key, value)
    }

    override fun put(key: String, value: String?) {
        cache.put(key, value)
    }

    override fun put(key: String, value: Set<String>?) {
        cache.put(key, value)
    }

    override fun put(key: String, value: ByteArray?) {
        cache.put(key, value)
    }

    override fun put(key: String, value: Parcelable?) {
        cache.put(key, value)
    }

    fun getFloat(key: String) = getFloat(key, 0F)

    override fun getFloat(key: String, defValue: Float): Float {
        return cache.getFloat(key, defValue)
    }

    fun getInt(key: String) = getInt(key, 0)

    override fun getInt(key: String, defValue: Int): Int {
        return cache.getInt(key, defValue)
    }

    fun getDouble(key: String) = getDouble(key, 0.0)

    override fun getDouble(key: String, defValue: Double): Double {
        return cache.getDouble(key, defValue)
    }

    fun getLong(key: String) = getLong(key, 0)

    override fun getLong(key: String, defValue: Long): Long {
        return cache.getLong(key, defValue)
    }

    fun getBoolean(key: String) = getBoolean(key, false)

    override fun getBoolean(key: String, defValue: Boolean): Boolean {
        return cache.getBoolean(key, defValue)
    }

    fun getString(key: String) = getString(key, null)

    override fun getString(key: String, defValue: String?): String? {
        return cache.getString(key, defValue)
    }

    fun getStringSet(key: String) = getStringSet(key, null)

    override fun getStringSet(key: String, defValue: Set<String>?): Set<String>? {
        return cache.getStringSet(key, defValue)
    }

    fun getByteArray(key: String) = getByteArray(key, null)

    override fun getByteArray(key: String, defValue: ByteArray?): ByteArray? {
        return cache.getByteArray(key, defValue)
    }

    @JvmOverloads
    inline fun <reified T : Parcelable> getParcelable(key: String, defValue: T? = null): T? {
        return getParcelable(key, T::class.java, defValue)
    }

    override fun <T : Parcelable> getParcelable(key: String, tClass: Class<T>): T? {
        return cache.getParcelable(key, tClass)
    }

    override fun <T : Parcelable> getParcelable(key: String, tClass: Class<T>, defValue: T?): T? {
        return cache.getParcelable(key, tClass, defValue)
    }

    override fun remove(key: String) {
        cache.remove(key)
    }

}