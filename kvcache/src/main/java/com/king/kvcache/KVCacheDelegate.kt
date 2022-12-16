package com.king.kvcache

import android.os.Parcelable
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * 属性委托：将属性的 get 和 set 托付给 [KVCache] 来代理
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */

//----------------------------------------------
/**
 * @param key 当 key不为空时，根据 key 来缓存对应的值，当 key 为空时，则取属性名称作为 key 来缓存对应的值
 * @param defaultValue 默认值
 */
fun kvCacheFloat(key: String? = null, defaultValue: Float = 0F) = kvCache(key, defaultValue)
/**
 * @param key 当 key不为空时，根据 key 来缓存对应的值，当 key 为空时，则取属性名称作为 key 来缓存对应的值
 * @param defaultValue 默认值
 */
fun kvCacheInt(key: String? = null, defaultValue: Int = 0) = kvCache(key, defaultValue)
/**
 * @param key 当 key不为空时，根据 key 来缓存对应的值，当 key 为空时，则取属性名称作为 key 来缓存对应的值
 * @param defaultValue 默认值
 */
fun kvCacheDouble(key: String? = null, defaultValue: Double = 0.0) = kvCache(key, defaultValue)
/**
 * @param key 当 key不为空时，根据 key 来缓存对应的值，当 key 为空时，则取属性名称作为 key 来缓存对应的值
 * @param defaultValue 默认值
 */
fun kvCacheLong(key: String? = null, defaultValue: Long = 0) = kvCache(key, defaultValue)
/**
 * @param key 当 key不为空时，根据 key 来缓存对应的值，当 key 为空时，则取属性名称作为 key 来缓存对应的值
 * @param defaultValue 默认值
 */
fun kvCacheBoolean(key: String? = null, defaultValue: Boolean = false) = kvCache(key, defaultValue)
/**
 * @param key 当 key不为空时，根据 key 来缓存对应的值，当 key 为空时，则取属性名称作为 key 来缓存对应的值
 * @param defaultValue 默认值
 */
fun kvCacheString(key: String? = null, defaultValue: String? = null) = kvCache(key, defaultValue)
/**
 * @param key 当 key不为空时，根据 key 来缓存对应的值，当 key 为空时，则取属性名称作为 key 来缓存对应的值
 * @param defaultValue 默认值
 */
fun kvCacheStringSet(key: String? = null, defaultValue: Set<String>? = null) = kvCache(key, defaultValue)
/**
 * @param key 当 key不为空时，根据 key 来缓存对应的值，当 key 为空时，则取属性名称作为 key 来缓存对应的值
 * @param defaultValue 默认值
 */
fun kvCacheByteArray(key: String? = null, defaultValue: ByteArray? = null) = kvCache(key, defaultValue)
/**
 * @param key 当 key不为空时，根据 key 来缓存对应的值，当 key 为空时，则取属性名称作为 key 来缓存对应的值
 * @param tClass 泛型 T 对应的类
 * @param defaultValue 默认值
 */
fun <T: Parcelable> kvCacheParcelable(key: String? = null, tClass: Class<T>, defaultValue: T? = null) = kvCache(key, tClass, defaultValue)
/**
 * @param key 当 key不为空时，根据 key 来缓存对应的值，当 key 为空时，则取属性名称作为 key 来缓存对应的值
 * @param defaultValue 默认值
 */
inline fun <reified T : Parcelable> kvCacheParcelable(key: String? = null, defaultValue: T? = null) = kvCache(key, defaultValue)

//----------------------------------------------

/**
 * @param key 当 key不为空时，根据 key 来缓存对应的值，当 key 为空时，则取属性名称作为 key 来缓存对应的值
 * @param defaultValue 默认值
 */
fun kvCache(key: String? = null, defaultValue: Float = 0F) = KVCachePropertyFloat(key, defaultValue)
/**
 * @param key 当 key不为空时，根据 key 来缓存对应的值，当 key 为空时，则取属性名称作为 key 来缓存对应的值
 * @param defaultValue 默认值
 */
fun kvCache(key: String? = null, defaultValue: Int = 0) = KVCachePropertyInt(key, defaultValue)
/**
 * @param key 当 key不为空时，根据 key 来缓存对应的值，当 key 为空时，则取属性名称作为 key 来缓存对应的值
 * @param defaultValue 默认值
 */
fun kvCache(key: String? = null, defaultValue: Double = 0.0) = KVCachePropertyDouble(key, defaultValue)
/**
 * @param key 当 key不为空时，根据 key 来缓存对应的值，当 key 为空时，则取属性名称作为 key 来缓存对应的值
 * @param defaultValue 默认值
 */
fun kvCache(key: String? = null, defaultValue: Long = 0) = KVCachePropertyLong(key, defaultValue)
/**
 * @param key 当 key不为空时，根据 key 来缓存对应的值，当 key 为空时，则取属性名称作为 key 来缓存对应的值
 * @param defaultValue 默认值
 */
fun kvCache(key: String? = null, defaultValue: Boolean = false) = KVCachePropertyBoolean(key, defaultValue)
/**
 * @param key 当 key不为空时，根据 key 来缓存对应的值，当 key 为空时，则取属性名称作为 key 来缓存对应的值
 * @param defaultValue 默认值
 */
fun kvCache(key: String? = null, defaultValue: String? = null) = KVCachePropertyString(key, defaultValue)
/**
 * @param key 当 key不为空时，根据 key 来缓存对应的值，当 key 为空时，则取属性名称作为 key 来缓存对应的值
 * @param defaultValue 默认值
 */
fun kvCache(key: String? = null, defaultValue: Set<String>? = null) = KVCachePropertyStringSet(key, defaultValue)
/**
 * @param key 当 key不为空时，根据 key 来缓存对应的值，当 key 为空时，则取属性名称作为 key 来缓存对应的值
 * @param defaultValue 默认值
 */
fun kvCache(key: String? = null, defaultValue: ByteArray? = null) = KVCachePropertyByteArray(key, defaultValue)
/**
 * @param key 当 key不为空时，根据 key 来缓存对应的值，当 key 为空时，则取属性名称作为 key 来缓存对应的值
 * @param tClass 泛型 T 对应的类
 * @param defaultValue 默认值
 */
fun <T: Parcelable> kvCache(key: String? = null, tClass: Class<T>, defaultValue: T? = null) = KVCachePropertyParcelable(key, tClass, defaultValue)
/**
 * @param key 当 key不为空时，根据 key 来缓存对应的值，当 key 为空时，则取属性名称作为 key 来缓存对应的值
 * @param defaultValue 默认值
 */
inline fun <reified T : Parcelable> kvCache(key: String? = null, defaultValue: T? = null) = KVCachePropertyParcelable(key, T::class.java, defaultValue)

//----------------------------------------------
/**
 * 属性委托 [Float]
 */
class KVCachePropertyFloat(
    private val key: String?,
    private val defaultValue: Float,
) : ReadWriteProperty<Any, Float> {
    override fun getValue(thisRef: Any, property: KProperty<*>): Float {
        return KVCache.getFloat(key ?: property.name, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Float) {
        KVCache.put(key ?: property.name, value)
    }
}

/**
 * 属性委托 [Int]
 */
class KVCachePropertyInt(
    private val key: String?,
    private val defaultValue: Int,
) : ReadWriteProperty<Any, Int> {
    override fun getValue(thisRef: Any, property: KProperty<*>): Int {
        return KVCache.getInt(key ?: property.name, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Int) {
        KVCache.put(key ?: property.name, value)
    }
}

/**
 * 属性委托 [Double]
 */
class KVCachePropertyDouble(
    private val key: String?,
    private val defaultValue: Double,
) : ReadWriteProperty<Any, Double> {
    override fun getValue(thisRef: Any, property: KProperty<*>): Double {
        return KVCache.getDouble(key ?: property.name, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Double) {
        KVCache.put(key ?: property.name, value)
    }
}

/**
 * 属性委托 [Long]
 */
class KVCachePropertyLong(
    private val key: String?,
    private val defaultValue: Long,
) : ReadWriteProperty<Any, Long> {
    override fun getValue(thisRef: Any, property: KProperty<*>): Long {
        return KVCache.getLong(key ?: property.name, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Long) {
        KVCache.put(key ?: property.name, value)
    }
}

/**
 * 属性委托 [Boolean]
 */
class KVCachePropertyBoolean(
    private val key: String?,
    private val defaultValue: Boolean,
) : ReadWriteProperty<Any, Boolean> {
    override fun getValue(thisRef: Any, property: KProperty<*>): Boolean {
        return KVCache.getBoolean(key ?: property.name, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) {
        KVCache.put(key ?: property.name, value)
    }
}

/**
 * 属性委托 [String]
 */
class KVCachePropertyString(
    private val key: String?,
    private val defaultValue: String?
) : ReadWriteProperty<Any, String?> {

    override fun getValue(thisRef: Any, property: KProperty<*>): String? {
        return KVCache.getString(key ?: property.name, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: String?) {
        KVCache.put(key ?: property.name, value)
    }
}

/**
 * 属性委托 [Set]
 */
class KVCachePropertyStringSet(
    private val key: String?,
    private val defaultValue: Set<String>?
) : ReadWriteProperty<Any, Set<String>?> {

    override fun getValue(thisRef: Any, property: KProperty<*>): Set<String>? {
        return KVCache.getStringSet(key ?: property.name, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Set<String>?) {
        KVCache.put(key ?: property.name, value)
    }
}

/**
 * 属性委托 [ByteArray]
 */
class KVCachePropertyByteArray(
    private val key: String?,
    private val defaultValue: ByteArray?
) : ReadWriteProperty<Any, ByteArray?> {

    override fun getValue(thisRef: Any, property: KProperty<*>): ByteArray? {
        return KVCache.getByteArray(key ?: property.name, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: ByteArray?) {
        KVCache.put(key ?: property.name, value)
    }
}

/**
 * 属性委托 [Parcelable]
 */
class KVCachePropertyParcelable<T: Parcelable>(
    private val key: String?,
    private val tClass: Class<T>,
    private val defaultValue: T?
) : ReadWriteProperty<Any, T?> {
    override fun getValue(thisRef: Any, property: KProperty<*>): T? {
        return KVCache.getParcelable(key ?: property.name, tClass, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T?) {
        KVCache.put(key ?: property.name, value)
    }

}