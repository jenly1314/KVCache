package com.king.kvcache

import android.content.Context
import android.os.Parcelable
import android.util.Log
import androidx.annotation.StringDef
import com.king.kvcache.KVCache.Provider
import com.king.kvcache.cache.*

/**
 * KVCache：统一管理缓存键值；支持无缝切换。
 *
 * 主要有如下特点：
 *
 * 你可以无需关心 API 之间的差异，无缝切缓存的内部实现；缓存提供者可参见 [Provider]
 *
 * 利用kotlin的委托属性特性，使用更简洁。
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@Suppress("unused")
class KVCache private constructor() {
    init {
        throw AssertionError()
    }

    /**
     * 缓存提供者
     */
    @StringDef(
        Provider.MMKV_CACHE,
        Provider.DATA_STORE_CACHE,
        Provider.SHARED_PREFERENCES_CACHE,
        Provider.MEMORY_CACHE,
    )
    @Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FIELD, AnnotationTarget.FUNCTION)
    @Retention(AnnotationRetention.SOURCE)
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

    companion object : ICache, CacheProvider {

        const val TAG = "KVCache"

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
         *
         * @param context 上下文
         * @param provider 缓存提供者，参见 [Provider]；当 provider 为空或未知时，会自动决定缓存实现：优先级
         * 从高到低依次为： MMKV -> DataStore -> SharedPreferences -> Memory
         */
        @JvmStatic
        @JvmOverloads
        fun initialize(context: Context, @Provider provider: String? = null) {
            initialize(obtainCache(context, provider))
        }

        /**
         * 初始化；建议在 Application 中进行初始化
         *
         * @param cache 缓存实现；如果 initialize(context, cacheProvider) 无法满足你的需求，你可以通过此函数来初始化缓存
         */
        @JvmStatic
        fun initialize(cache: Cache) {
            this.cache = cache
            this.isInitializedCache = true
        }

        /**
         * 根据 [provider] 获取缓存对象
         *
         * 当 [provider] 为空或未找到对应的缓存实现时，会返回一个内置的缓存实现：优先级
         * 从高到低依次为： MMKV -> DataStore -> SharedPreferences -> Memory
         *
         * @param context [Context]
         * @param provider [Provider]
         */
        @JvmStatic
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

        /**
         * 获取缓存提供者
         */
        @JvmStatic
        override fun getProvider(): String {
            return if (isInitializedCache) cache.getProvider() else ""
        }

        /**
         * 根据 [key] 将 [value] 存放到缓存
         * @param key 键
         * @param value 值
         */
        @JvmStatic
        override fun put(key: String, value: Float?) {
            cache.put(key, value)
        }

        /**
         * 根据 [key] 将 [value] 存放到缓存
         * @param key 键
         * @param value 值
         */
        @JvmStatic
        override fun put(key: String, value: Int?) {
            cache.put(key, value)
        }

        /**
         * 根据 [key] 将 [value] 存放到缓存
         * @param key 键
         * @param value 值
         */
        @JvmStatic
        override fun put(key: String, value: Double?) {
            cache.put(key, value)
        }

        /**
         * 根据 [key] 将 [value] 存放到缓存
         * @param key 键
         * @param value 值
         */
        @JvmStatic
        override fun put(key: String, value: Long?) {
            cache.put(key, value)
        }

        /**
         * 根据 [key] 将 [value] 存放到缓存
         * @param key 键
         * @param value 值
         */
        @JvmStatic
        override fun put(key: String, value: Boolean?) {
            cache.put(key, value)
        }

        /**
         * 根据 [key] 将 [value] 存放到缓存
         * @param key 键
         * @param value 值
         */
        @JvmStatic
        override fun put(key: String, value: String?) {
            cache.put(key, value)
        }

        /**
         * 根据 [key] 将 [value] 存放到缓存
         * @param key 键
         * @param value 值
         */
        @JvmStatic
        override fun put(key: String, value: Set<String>?) {
            cache.put(key, value)
        }

        /**
         * 根据 [key] 将 [value] 存放到缓存
         * @param key 键
         * @param value 值
         */
        @JvmStatic
        override fun put(key: String, value: ByteArray?) {
            cache.put(key, value)
        }

        /**
         * 根据 [key] 将 [value] 存放到缓存
         * @param key 键
         * @param value 值
         */
        @JvmStatic
        override fun put(key: String, value: Parcelable?) {
            cache.put(key, value)
        }

        /**
         * 根据 [key] 获取缓存的值，如果未获取到，则返回默认值：0F
         * @param key 键
         */
        @JvmStatic
        fun getFloat(key: String) = getFloat(key, 0F)

        /**
         * 根据 [key] 获取缓存的值，如果未获取到，则返回默认值 [defValue]
         * @param key 键
         * @param defValue 默认值
         */
        @JvmStatic
        override fun getFloat(key: String, defValue: Float): Float {
            return cache.getFloat(key, defValue)
        }

        /**
         * 根据 [key] 获取缓存的值，如果未获取到，则返回默认值：0
         * @param key 键
         */
        @JvmStatic
        fun getInt(key: String) = getInt(key, 0)

        /**
         * 根据 [key] 获取缓存的值，如果未获取到，则返回默认值 [defValue]
         * @param key 键
         * @param defValue 默认值
         */
        @JvmStatic
        override fun getInt(key: String, defValue: Int): Int {
            return cache.getInt(key, defValue)
        }

        /**
         * 根据 [key] 获取缓存的值，如果未获取到，则返回默认值：0.0
         * @param key 键
         */
        @JvmStatic
        fun getDouble(key: String) = getDouble(key, 0.0)

        /**
         * 根据 [key] 获取缓存的值，如果未获取到，则返回默认值 [defValue]
         * @param key 键
         * @param defValue 默认值
         */
        @JvmStatic
        override fun getDouble(key: String, defValue: Double): Double {
            return cache.getDouble(key, defValue)
        }

        /**
         * 根据 [key] 获取缓存的值，如果未获取到，则返回默认值：0
         * @param key 键
         */
        @JvmStatic
        fun getLong(key: String) = getLong(key, 0)

        /**
         * 根据 [key] 获取缓存的值，如果未获取到，则返回默认值 [defValue]
         * @param key 键
         * @param defValue 默认值
         */
        @JvmStatic
        override fun getLong(key: String, defValue: Long): Long {
            return cache.getLong(key, defValue)
        }

        /**
         * 根据 [key] 获取缓存的值，如果未获取到，则返回默认值：false
         * @param key 键
         */
        @JvmStatic
        fun getBoolean(key: String) = getBoolean(key, false)

        /**
         * 根据 [key] 获取缓存的值，如果未获取到，则返回默认值 [defValue]
         * @param key 键
         * @param defValue 默认值
         */
        @JvmStatic
        override fun getBoolean(key: String, defValue: Boolean): Boolean {
            return cache.getBoolean(key, defValue)
        }

        /**
         * 根据 [key] 获取缓存的值，如果未获取到，则返回空
         * @param key 键
         */
        @JvmStatic
        fun getString(key: String) = getString(key, null)

        /**
         * 根据 [key] 获取缓存的值，如果未获取到，则返回默认值 [defValue]
         * @param key 键
         * @param defValue 默认值
         */
        @JvmStatic
        override fun getString(key: String, defValue: String?): String? {
            return cache.getString(key, defValue)
        }

        /**
         * 根据 [key] 获取缓存的值，如果未获取到，则返回空
         * @param key 键
         */
        @JvmStatic
        fun getStringSet(key: String) = getStringSet(key, null)

        /**
         * 根据 [key] 获取缓存的值，如果未获取到，则返回默认值 [defValue]
         * @param key 键
         * @param defValue 默认值
         */
        @JvmStatic
        override fun getStringSet(key: String, defValue: Set<String>?): Set<String>? {
            return cache.getStringSet(key, defValue)
        }

        /**
         * 根据 [key] 获取缓存的值，如果未获取到，则返回空
         * @param key 键
         */
        @JvmStatic
        fun getByteArray(key: String) = getByteArray(key, null)

        /**
         * 根据 [key] 获取缓存的值，如果未获取到，则返回默认值 [defValue]
         * @param key 键
         * @param defValue 默认值
         */
        @JvmStatic
        override fun getByteArray(key: String, defValue: ByteArray?): ByteArray? {
            return cache.getByteArray(key, defValue)
        }

        /**
         * 根据 [key] 获取缓存的值，如果未获取到，则返回默认值 [defValue]
         * @param key 键
         * @param defValue 默认值
         */
        inline fun <reified T : Parcelable> getParcelable(key: String, defValue: T? = null): T? {
            return getParcelable(key, T::class.java, defValue)
        }

        /**
         * 根据 [key] 获取缓存的值，如果未获取到，则返回空
         * @param key 键
         * @param tClass 值对应的 [Class]
         */
        @JvmStatic
        override fun <T : Parcelable> getParcelable(key: String, tClass: Class<T>): T? {
            return cache.getParcelable(key, tClass)
        }

        /**
         * 根据 [key] 获取缓存的值，如果未获取到，则返回默认值 [defValue]
         * @param key 键
         * @param tClass 值对应的 [Class]
         * @param defValue 默认值
         */
        @JvmStatic
        override fun <T : Parcelable> getParcelable(
            key: String,
            tClass: Class<T>,
            defValue: T?
        ): T? {
            return cache.getParcelable(key, tClass, defValue)
        }

        /**
         * 根据 [key] 获取缓存的值，如果未获取到，则返回默认值 [defValue]
         * @param key 键
         * @param defValue 默认值
         */
        inline fun <reified T : Parcelable> getOrDefault(key: String, defValue: T?): T? {
            return getParcelable(key, defValue)
        }

        /**
         * 根据 [key] 获取缓存的值，如果未获取到，则返回默认值 [defValue]
         * @param key 键
         * @param defValue 默认值
         */
        inline fun <reified T> getOrDefault(key: String, defValue: T): T {
            return if (defValue != null) {
                // 如果默认值不为空，则优先通过默认值类型来推断获取的数据类型
                when (defValue) {
                    is Boolean -> getBoolean(key, defValue) as T
                    is Double -> getDouble(key, defValue) as T
                    is Float -> getFloat(key, defValue) as T
                    is Int -> getInt(key, defValue) as T
                    is Long -> getLong(key, defValue) as T
                    is String -> getString(key, defValue) as T
                    is Parcelable -> getParcelable(key, defValue) as T
                    is ByteArray -> getByteArray(key, defValue) as T
                    is Set<*> -> getStringSet(key) as? T ?: defValue
                    else -> {
                        val valueType = defValue.javaClass.canonicalName
                        Log.w(TAG, "Illegal value type $valueType for key \"$key\"")
                        defValue
                    }
                }
            } else {
                val cls = T::class.java
                if (java.lang.Boolean::class.java.isAssignableFrom(cls)) {
                    getBoolean(key) as T
                } else if (java.lang.Double::class.java.isAssignableFrom(cls)) {
                    getDouble(key) as T
                } else if (java.lang.Float::class.java.isAssignableFrom(cls)) {
                    getFloat(key) as T
                } else if (java.lang.Integer::class.java.isAssignableFrom(cls)) {
                    getInt(key) as T
                } else if (java.lang.Long::class.java.isAssignableFrom(cls)) {
                    getLong(key) as T
                } else if (java.lang.String::class.java.isAssignableFrom(cls)) {
                    getString(key) as T
                } else if (ByteArray::class.java.isAssignableFrom(cls)) {
                    getByteArray(key) as T
                } else if (java.util.Set::class.java.isAssignableFrom(cls)) {
                    getStringSet(key) as T
                } else {
                    val valueType = cls.canonicalName
                    Log.w(TAG, "Illegal value type $valueType for key \"$key\"")
                    null as T
                }
            }
        }

        /**
         * 根据 [key] 删除缓存的键值对
         * @param key 键
         */
        @JvmStatic
        override fun remove(key: String) {
            cache.remove(key)
        }

        /**
         * 清空所有缓存的键值对
         */
        @JvmStatic
        override fun clear() {
            cache.clear()
        }
    }
}