package com.king.kvcache.cache

import android.os.Parcelable

/**
 * 缓存接口定义
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
interface ICache {
    /**
     * 根据 [key] 将 [value] 存放到缓存
     * @param key 键
     * @param value 值
     */
    fun put(key: String, value: Float?)

    /**
     * 根据 [key] 将 [value] 存放到缓存
     * @param key 键
     * @param value 值
     */
    fun put(key: String, value: Int?)

    /**
     * 根据 [key] 将 [value] 存放到缓存
     * @param key 键
     * @param value 值
     */
    fun put(key: String, value: Double?)

    /**
     * 根据 [key] 将 [value] 存放到缓存
     * @param key 键
     * @param value 值
     */
    fun put(key: String, value: Long?)

    /**
     * 根据 [key] 将 [value] 存放到缓存
     * @param key 键
     * @param value 值
     */
    fun put(key: String, value: Boolean?)

    /**
     * 根据 [key] 将 [value] 存放到缓存
     * @param key 键
     * @param value 值
     */
    fun put(key: String, value: String?)

    /**
     * 根据 [key] 将 [value] 存放到缓存
     * @param key 键
     * @param value 值
     */
    fun put(key: String, value: Set<String>?)

    /**
     * 根据 [key] 将 [value] 存放到缓存
     * @param key 键
     * @param value 值
     */
    fun put(key: String, value: ByteArray?)

    /**
     * 根据 [key] 将 [value] 存放到缓存
     * @param key 键
     * @param value 值
     */
    fun put(key: String, value: Parcelable?)

    /**
     * 根据 [key] 获取缓存的值，如果未获取到，则返回默认值 [defValue]
     * @param key 键
     * @param defValue 默认值
     */
    fun getFloat(key: String, defValue: Float): Float

    /**
     * 根据 [key] 获取缓存的值，如果未获取到，则返回默认值 [defValue]
     * @param key 键
     * @param defValue 默认值
     */
    fun getInt(key: String, defValue: Int): Int

    /**
     * 根据 [key] 获取缓存的值，如果未获取到，则返回默认值 [defValue]
     * @param key 键
     * @param defValue 默认值
     */
    fun getDouble(key: String, defValue: Double): Double

    /**
     * 根据 [key] 获取缓存的值，如果未获取到，则返回默认值 [defValue]
     * @param key 键
     * @param defValue 默认值
     */
    fun getLong(key: String, defValue: Long): Long

    /**
     * 根据 [key] 获取缓存的值，如果未获取到，则返回默认值 [defValue]
     * @param key 键
     * @param defValue 默认值
     */
    fun getBoolean(key: String, defValue: Boolean): Boolean

    /**
     * 根据 [key] 获取缓存的值，如果未获取到，则返回默认值 [defValue]
     * @param key 键
     * @param defValue 默认值
     */
    fun getString(key: String, defValue: String?): String?

    /**
     * 根据 [key] 获取缓存的值，如果未获取到，则返回默认值 [defValue]
     * @param key 键
     * @param defValue 默认值
     */
    fun getStringSet(key: String, defValue: Set<String>?): Set<String>?

    /**
     * 根据 [key] 获取缓存的值，如果未获取到，则返回默认值 [defValue]
     * @param key 键
     * @param defValue 默认值
     */
    fun getByteArray(key: String, defValue: ByteArray?): ByteArray?

    /**
     * 根据 [key] 获取缓存的值，如果未获取到，则返回空
     * @param key 键
     * @param tClass 值对应的 [Class]
     */
    fun <T : Parcelable> getParcelable(key: String, tClass: Class<T>): T?

    /**
     * 根据 [key] 获取缓存的值，如果未获取到，则返回默认值 [defValue]
     * @param key 键
     * @param tClass 值对应的 [Class]
     * @param defValue 默认值
     */
    fun <T : Parcelable> getParcelable(key: String, tClass: Class<T>, defValue: T?): T?

    /**
     * 根据 [key] 删除缓存的键值对
     * @param key 键
     */
    fun remove(key: String)

    /**
     * 清空所有缓存的键值对
     */
    fun clear()
}