package com.king.kvcache.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.king.kvcache.KVCache
import com.king.kvcache.app.bean.ParcelableBean
import com.king.kvcache.app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "KVCache"
    }

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.tv.text = ""
        // 默认：自动查找可用的缓存实现；优先级从高到低依次为： MMKV -> DataStore -> SharedPreferences
        testKVCache()
        // 使用 MMKV 提供缓存实现
        testKVCache(KVCache.Provider.MMKV_CACHE)
        // 使用 DataStore 提供缓存实现
        testKVCache(KVCache.Provider.DATA_STORE_CACHE)
        // 使用 SharedPreferences 提供缓存实现
        testKVCache(KVCache.Provider.SHARED_PREFERENCES_CACHE)
    }


    private fun testKVCache(@KVCache.Provider provider: String? = null) {

        val builder = StringBuilder()

        Log.d(TAG, "// ---------------- Provider: $provider")
        builder.append("// ---------------- Provider: $provider").append("\n")

        // 初始化 KVCache，建议在 Application 中进行初始化；这里为了演示几种不同的缓存实现，所以直接在这里初始化
        KVCache.initialize(this, provider)

        val cacheProvider = KVCache.cacheProvider()

        Log.d(TAG, "CacheProvider: $cacheProvider")
        builder.append("CacheProvider: $cacheProvider").append("\n")

        val f = 1.0F
        KVCache.put("float", f)
        Log.d(TAG, "$cacheProvider: float = ${KVCache.getFloat("float")}")
        builder.append("$cacheProvider: float = ${KVCache.getFloat("float")}").append("\n")

        KVCache.remove("float")

        Log.d(TAG, "$cacheProvider: remove.. float = ${KVCache.getFloat("float")}")
        builder.append("$cacheProvider: remove.. float = ${KVCache.getFloat("float")}").append("\n")

        val i = 2
        KVCache.put("int", i)
        Log.d(TAG, "$cacheProvider: int = ${KVCache.getInt("int")}")
        builder.append("$cacheProvider: int = ${KVCache.getInt("int")}").append("\n")

        val d = 3.0
        KVCache.put("double", d)
        Log.d(TAG, "$cacheProvider: double = ${KVCache.getDouble("double")}")
        builder.append("$cacheProvider: double = ${KVCache.getDouble("double")}").append("\n")

        val l = 4L
        KVCache.put("long", l)
        Log.d(TAG, "$cacheProvider: long = ${KVCache.getLong("long")}")
        builder.append("$cacheProvider: long = ${KVCache.getLong("long")}").append("\n")

        val b = true
        KVCache.put("boolean", b)
        Log.d(TAG, "$cacheProvider: boolean = ${KVCache.getBoolean("boolean")}")
        builder.append("$cacheProvider: boolean = ${KVCache.getBoolean("boolean")}").append("\n")

        val s = KVCache::class.java.simpleName
        KVCache.put("string", s)
        Log.d(TAG, "$cacheProvider: string = ${KVCache.getString("string")}")
        builder.append("$cacheProvider: string = ${KVCache.getString("string")}").append("\n")

        val stringSet = setOf("1", "2", "3")
        KVCache.put("stringSet", stringSet)
        Log.d(TAG, "$cacheProvider: stringSet = ${KVCache.getStringSet("stringSet")}")
        builder.append("$cacheProvider: stringSet = ${KVCache.getStringSet("stringSet")}").append("\n")

        // 如果使用的是 MMKV 缓存实现，则额外支持缓存 ByteArray 和 Parcelable
        if (KVCache.cacheProvider() == KVCache.Provider.MMKV_CACHE) {

            val byteArray: ByteArray = byteArrayOf(1, 2, 3)
            KVCache.put("byteArray", byteArray)
            Log.d(TAG, "$cacheProvider: byteArray = ${KVCache.getByteArray("byteArray")?.toList()}")
            builder.append("$cacheProvider: byteArray = ${KVCache.getByteArray("byteArray")?.toList()}").append("\n")

            val p = ParcelableBean("ParcelableBean", 10, true)
            KVCache.put("parcelable", p)
            Log.d(TAG, "$cacheProvider: parcelable = ${KVCache.getParcelable<ParcelableBean>("parcelable")}")
            builder.append("$cacheProvider: parcelable = ${KVCache.getParcelable<ParcelableBean>("parcelable")}").append("\n")

        }

        Log.d(TAG, "// ------------------------------------------------ //")
        builder.append("// ------------------------------------------------ //").append("\n\n")

        binding.tv.append(builder)
    }

}