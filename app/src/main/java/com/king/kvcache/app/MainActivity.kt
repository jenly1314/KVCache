package com.king.kvcache.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.king.kvcache.KVCache
import com.king.kvcache.app.bean.ParcelableBean
import com.king.kvcache.app.databinding.ActivityMainBinding
import com.king.kvcache.kvCache
import com.king.kvcache.kvCacheBoolean
import com.king.kvcache.kvCacheInt

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "KVCache"
    }

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    // 属性委托：相当于 KVCache.getInt("arg1"); 类型为：Int（key的默认值如果忽略或为空时，则默认值为变量的名称）
    var arg1 by kvCacheInt()
    // 属性委托：相当于 KVCache.getFloat("param1"); 类型为：Float（默认值为：0F）
    var arg2 : Float by kvCache("argFloat", 0F)
    // 属性委托：相当于 KVCache.getDouble("arg3"); 类型为：Double（key的默认值如果忽略或为空时，则默认值为变量的名称）
    var arg3 by kvCache(defaultValue =  0.0)
    // 函数 kvCache 与 kvCacheXXX （XXX: 表示类型，如：kvCacheInt）用法基本一致，只是表现形式不同，由于 kvCache 有多个函数名称相同，所以需要传默认值，根据默认值的类型来推断使用的是哪个函数
    var arg4 by kvCache("argBool", false)
    // 这里让 arg4 和 arg5 指向相同的key
    var arg5 by kvCacheBoolean("argBool")

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

        Log.d(TAG, "// -------- kvCache")
        builder.append("// -------- kvCache").append("\n")

        // 属性委托：arg1 = 5 相当于：KVCache.put("arg1", 5)，再打印查看 KVCache.getInt("arg1") 的值
        arg1 = 5
        Log.d(TAG, "$cacheProvider: kvCache -> arg1 = ${KVCache.getInt("arg1")}")
        builder.append("$cacheProvider: kvCache -> arg1 = ${KVCache.getInt("arg1")}").append("\n")

        // 属性委托：arg2 = 6F 相当于：KVCache.put("argFloat", 6F)，再打印查看 KVCache.getFloat("argFloat") 的值
        arg2 = 6F
        Log.d(TAG, "$cacheProvider: kvCache -> arg2 = ${KVCache.getFloat("argFloat")}")
        builder.append("$cacheProvider: kvCache -> arg2 = ${KVCache.getFloat("argFloat")}").append("\n")

        // 属性委托：通过 KVCache 缓存值后，再打印查看 arg3 的值; 相当于：属性的 getValue 取 KVCache.getDouble("arg3") 的值
        KVCache.put("arg3", 7.0)
        Log.d(TAG, "$cacheProvider: kvCache -> arg3 = $arg3")
        builder.append("$cacheProvider: kvCache -> arg3 = $arg3").append("\n")

        // 属性委托：arg4 = true 相当于：KVCache.put("argBool", true)，再打印查看 KVCache.getBoolean("argBool") 的值
        arg4 = true
        Log.d(TAG, "$cacheProvider: kvCache -> arg4 = ${KVCache.getBoolean("argBool")}")
        builder.append("$cacheProvider: kvCache -> arg4 = ${KVCache.getBoolean("argBool")}").append("\n")

        // 因为 arg4 和 arg5 使用相同的 key，所以改变 arg4 = true 后，arg5 的值也将改变；即都是取的 KVCache.getBoolean("argBool") 的值
        Log.d(TAG, "$cacheProvider: kvCache -> arg5 = $arg5")
        builder.append("$cacheProvider: kvCache -> arg5 = $arg5").append("\n")


        Log.d(TAG, "// ------------------------------------------------ //")
        builder.append("// ------------------------------------------------ //").append("\n\n")

        binding.tv.append(builder)
    }

}