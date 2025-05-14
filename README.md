# KVCache

[![MavenCentral](https://img.shields.io/maven-central/v/com.github.jenly1314/kvcache?logo=sonatype)](https://repo1.maven.org/maven2/com/github/jenly1314/KVCache)
[![JitPack](https://img.shields.io/jitpack/v/github/jenly1314/KVCache?logo=jitpack)](https://jitpack.io/#jenly1314/KVCache)
[![CI](https://img.shields.io/github/actions/workflow/status/jenly1314/KVCache/build.yml?logo=github)](https://github.com/jenly1314/KVCache/actions/workflows/build.yml)
[![Download](https://img.shields.io/badge/download-APK-brightgreen?logo=github)](https://raw.githubusercontent.com/jenly1314/KVCache/master/app/release/app-release.apk)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen?logo=android)](https://developer.android.com/guide/topics/manifest/uses-sdk-element#ApiLevels)
[![License](https://img.shields.io/github/license/jenly1314/KVCache?logo=open-source-initiative)](https://opensource.org/licenses/mit)

KVCache 是一个便于统一管理的键值缓存库；支持无缝切换缓存实现。

**主要有如下特点：**

* 支持无缝切换缓存的实现；（主要包括：**MMKV** 、**DataStore** 、**SharedPreferences**、**Memory** ）
* 你可以无需关心 API 之间的差异，使用时就像使用Map一样简单；
* 利用Kotlin的委托属性特性，使用更简洁。

## 引入

### Gradle:

1. 在Project的 **build.gradle** 或 **setting.gradle** 中添加远程仓库

    ```gradle
    repositories {
        //...
        mavenCentral()
    }
    ```

2. 在Module的 **build.gradle** 中添加依赖项

    ```gradle
    implementation 'com.github.jenly1314:kvcache:1.3.0'
    ```

## 使用

### KVCache 初始化
```kotlin

    // 建议在 Application 中进行初始化
    KVCache.initialize(this, provider)

```

KVCache初始化时，关于 **Provider** 参数的相关说明如下：

```kotlin
   /**
     * 使用 MMKV 提供缓存实现；需依赖 MMKV
     */
    Provider.MMKV_CACHE
    /**
     * 使用 DataStore 提供缓存实现；需依赖 DataStore
     */
    Provider.DATA_STORE_CACHE
    /**
     * 使用 SharedPreferences 提供缓存实现
     */
    Provider.SHARED_PREFERENCES_CACHE
    /**
     * 使用 Memory 提供缓存实现
     */
    Provider.MEMORY_CACHE
```

> 初始化 KVCache 时，如果不传 provider, 则会自动决定缓存实现：优先级从高到低依次为： MMKV -> DataStore -> SharedPreferences -> Memory

> 当需要进行单元测试时，使用`Memory` 是一个非常不错的选择。

### KVCache 基本使用示例

KVCache的主要函数大多以`put`和`get`开头，基本都是通过`put`缓存键值对，通过`get`获取键对应的缓存；使用方式和 **bundle** 类似；

```kotlin

    val f = 1.0F
    KVCache.put("float", f)
    Log.d(TAG, "$cacheProvider: float = ${KVCache.getFloat("float")}")

    val i = 2
    KVCache.put("int", i)
    Log.d(TAG, "$cacheProvider: int = ${KVCache.getInt("int")}")

    val d = 3.0
    KVCache.put("double", d)
    Log.d(TAG, "$cacheProvider: double = ${KVCache.getDouble("double")}")

    val l = 4L
    KVCache.put("long", l)
    Log.d(TAG, "$cacheProvider: long = ${KVCache.getLong("long")}")

    val b = true
    KVCache.put("boolean", b)
    Log.d(TAG, "$cacheProvider: boolean = ${KVCache.getBoolean("boolean")}")

    val s = KVCache::class.java.simpleName
    KVCache.put("string", s)
    Log.d(TAG, "$cacheProvider: string = ${KVCache.getString("string")}")

    val stringSet = setOf("A", "B", "C")
    KVCache.put("stringSet", stringSet)
    Log.d(TAG, "$cacheProvider: stringSet = ${KVCache.getStringSet("stringSet")}")

    val byteArray: ByteArray = byteArrayOf(1, 2, 3)
    KVCache.put("byteArray", byteArray)
    Log.d(TAG, "$cacheProvider: byteArray = ${KVCache.getByteArray("byteArray")?.toList()}")

    val p = ParcelableBean("ParcelableBean", 10, true)
    KVCache.put("parcelable", p)
    Log.d(TAG, "$cacheProvider: parcelable = ${KVCache.getParcelable<ParcelableBean>("parcelable")}")

```

### KVCache 属性委托使用示例

KVCache属性委托目前定义的函数都是 `kvCache` 开头，使用起来也比较简单；

示例如下：

```kotlin

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


    //... 使用

    // 属性委托：arg1 = 5 相当于：KVCache.put("arg1", 5)，再打印查看 KVCache.getInt("arg1") 的值
    arg1 = 5
    Log.d(TAG, "$cacheProvider: kvCache -> arg1 = ${KVCache.getInt("arg1")}")

    // 属性委托：arg2 = 6F 相当于：KVCache.put("argFloat", 6F)，再打印查看 KVCache.getFloat("argFloat") 的值
    arg2 = 6F
    Log.d(TAG, "$cacheProvider: kvCache -> arg2 = ${KVCache.getFloat("argFloat")}")

    // 属性委托：通过 KVCache 缓存值后，再打印查看 arg3 的值; 相当于：属性的 getValue 取 KVCache.getDouble("arg3") 的值
    KVCache.put("arg3", 7.0)
    Log.d(TAG, "$cacheProvider: kvCache -> arg3 = $arg3")

    // 属性委托：arg4 = true 相当于：KVCache.put("argBool", true)，再打印查看 KVCache.getBoolean("argBool") 的值
    arg4 = true
    Log.d(TAG, "$cacheProvider: kvCache -> arg4 = ${KVCache.getBoolean("argBool")}")

    // 因为 arg4 和 arg5 使用相同的 key，所以改变 arg4 = true 后，arg5 的值也将改变；即都是取的 KVCache.getBoolean("argBool") 的值
    Log.d(TAG, "$cacheProvider: kvCache -> arg5 = $arg5")
```

### 测试日志
```logcatfilter
// ---------------- Provider: null
CacheProvider: MMKVCache
MMKVCache: float = 1.0
MMKVCache: int = 2
MMKVCache: double = 3.0
MMKVCache: long = 4
MMKVCache: boolean = true
MMKVCache: string = KVCache
MMKVCache: stringSet = [A, B, C]
MMKVCache: byteArray = [1, 2, 3]
MMKVCache: parcelable = ParcelableBean(name=ParcelableBean, i=10, bool=true)
// -------- kvCache
MMKVCache: kvCache -> arg1 = 5
MMKVCache: kvCache -> arg2 = 6.0
MMKVCache: kvCache -> arg3 = 7.0
MMKVCache: kvCache -> arg4 = true
MMKVCache: kvCache -> arg5 = true
// ------------------------------------------------ //
// ---------------- Provider: MMKVCache
CacheProvider: MMKVCache
MMKVCache: float = 1.0
MMKVCache: int = 2
MMKVCache: double = 3.0
MMKVCache: long = 4
MMKVCache: boolean = true
MMKVCache: string = KVCache
MMKVCache: stringSet = [A, B, C]
MMKVCache: byteArray = [1, 2, 3]
MMKVCache: parcelable = ParcelableBean(name=ParcelableBean, i=10, bool=true)
// -------- kvCache
MMKVCache: kvCache -> arg1 = 5
MMKVCache: kvCache -> arg2 = 6.0
MMKVCache: kvCache -> arg3 = 7.0
MMKVCache: kvCache -> arg4 = true
MMKVCache: kvCache -> arg5 = true
// ------------------------------------------------ //
// ---------------- Provider: DataStoreCache
CacheProvider: DataStoreCache
DataStoreCache: float = 1.0
DataStoreCache: int = 2
DataStoreCache: double = 3.0
DataStoreCache: long = 4
DataStoreCache: boolean = true
DataStoreCache: string = KVCache
DataStoreCache: stringSet = [A, B, C]
DataStoreCache: byteArray = [1, 2, 3]
DataStoreCache: parcelable = ParcelableBean(name=ParcelableBean, i=10, bool=true)
// -------- kvCache
DataStoreCache: kvCache -> arg1 = 5
DataStoreCache: kvCache -> arg2 = 6.0
DataStoreCache: kvCache -> arg3 = 7.0
DataStoreCache: kvCache -> arg4 = true
DataStoreCache: kvCache -> arg5 = true
// ------------------------------------------------ //
// ---------------- Provider: SharedPreferencesCache
CacheProvider: SharedPreferencesCache
SharedPreferencesCache: float = 1.0
SharedPreferencesCache: int = 2
SharedPreferencesCache: double = 3.0
SharedPreferencesCache: long = 4
SharedPreferencesCache: boolean = true
SharedPreferencesCache: string = KVCache
SharedPreferencesCache: stringSet = [A, B, C]
SharedPreferencesCache: byteArray = [1, 2, 3]
SharedPreferencesCache: parcelable = ParcelableBean(name=ParcelableBean, i=10, bool=true)
// -------- kvCache
SharedPreferencesCache: kvCache -> arg1 = 5
SharedPreferencesCache: kvCache -> arg2 = 6.0
SharedPreferencesCache: kvCache -> arg3 = 7.0
SharedPreferencesCache: kvCache -> arg4 = true
SharedPreferencesCache: kvCache -> arg5 = true
// ------------------------------------------------ //
// ---------------- Provider: MemoryCache
CacheProvider: MemoryCache
MemoryCache: float = 1.0
MemoryCache: int = 2
MemoryCache: double = 3.0
MemoryCache: long = 4
MemoryCache: boolean = true
MemoryCache: string = KVCache
MemoryCache: stringSet = [A, B, C]
MemoryCache: byteArray = [1, 2, 3]
MemoryCache: parcelable = ParcelableBean(name=ParcelableBean, i=10, bool=true)
// -------- kvCache
MemoryCache: kvCache -> arg1 = 5
MemoryCache: kvCache -> arg2 = 6.0
MemoryCache: kvCache -> arg3 = 7.0
MemoryCache: kvCache -> arg4 = true
MemoryCache: kvCache -> arg5 = true
// ------------------------------------------------ //
```

更多使用详情，请查看[app](app)中的源码使用示例或直接查看[API帮助文档](https://jenly1314.github.io/KVCache/api/)

## 相关推荐

- [AndroidKTX](https://github.com/AndroidKTX/AndroidKTX) 一个简化 Android 开发的 Kotlin 工具类集。
- [AndroidUtil](http://github.com/AndroidUtil/AndroidUtil) 一个整理了Android常用工具类集合，平时在开发的过程中可能会经常用到。
- [AppUpdater](http://github.com/jenly1314/AppUpdater) 一个专注于App更新，一键傻瓜式集成App版本升级的轻量开源库。
- [RetrofitHelper](http://github.com/jenly1314/RetrofitHelper) 一个支持动态改变BaseUrl，动态配置超时时长的Retrofit帮助类。
- [BaseUrlManager](http://github.com/jenly1314/BaseUrlManager) 一个BaseUrl管理器，主要用于打测试包时，一个App可动态切换到不同的开发环境或测试环境。
- [SuperTextView](http://github.com/jenly1314/SuperTextView) 一个在TextView的基础上扩展了几种动画效果的控件。
- [ImageViewer](http://github.com/AndroidKTX/ImageViewer) 一个图片查看器，一般用来查看图片详情或查看大图时使用。
- [GuidePage](http://github.com/AndroidKTX/GuidePage) 一个App欢迎引导页。一般用于首次打开App时场景，通过引导页指南，概述App特色等相关信息。
- [LogX](http://github.com/jenly1314/LogX) 一个轻量而强大的日志框架；好用不解释。

<!-- end -->

## 版本日志

#### v1.3.0：2024-2-3
* 更新Gradle至v8.0
* 新增 `getOrDefault` 函数
* 优化Java调用方式

#### v1.2.0：2023-2-26
* 支持清空缓存（新增 `clear()` 函数）
* 优化细节

#### v1.1.0：2022-12-16
* 新增`MemoryCache`

#### v1.0.1：2022-7-21
* 支持属性委托

#### v1.0.0：2022-7-19
* KVCache初始版本

---

![footer](https://jenly1314.github.io/page/footer.svg)
