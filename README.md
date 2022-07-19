# KVCache

[![Download](https://img.shields.io/badge/download-App-blue.svg)](https://raw.githubusercontent.com/jenly1314/KVCache/master/app/release/app-release.apk)
[![MavenCentral](https://img.shields.io/maven-central/v/com.github.jenly1314/kvcache)](https://repo1.maven.org/maven2/com/github/jenly1314/kvcache)
[![JitPack](https://jitpack.io/v/jenly1314/KVCache.svg)](https://jitpack.io/#jenly1314/KVCache)
[![CI](https://travis-ci.com/jenly1314/KVCache.svg?branch=master)](https://travis-ci.com/jenly1314/KVCache)
[![CircleCI](https://circleci.com/gh/jenly1314/KVCache.svg?style=svg)](https://circleci.com/gh/jenly1314/KVCache)
[![API](https://img.shields.io/badge/API-21%2B-blue.svg?style=flat)](https://android-arsenal.com/api?level=21)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](https://opensource.org/licenses/mit-license.php)
[![Blog](https://img.shields.io/badge/blog-Jenly-9933CC.svg)](https://jenly1314.github.io/)
[![QQGroup](https://img.shields.io/badge/QQGroup-20867961-blue.svg)](http://shang.qq.com/wpa/qunwpa?idkey=8fcc6a2f88552ea44b1411582c94fd124f7bb3ec227e2a400dbbfaad3dc2f5ad)

KVCache 是一个便于统一管理的键值缓存库；支持无缝切换缓存实现。（你可以无需关心 API 之间的差异，无缝切换至：**MMKV** 、 **DataStore** 、 **SharedPreferences** 缓存的实现）

## 引入

### Gradle:

1. 在Project的 **build.gradle** 里面添加远程仓库

```gradle
allprojects {
    repositories {
        //...
        mavenCentral()
    }
}
```

2. 在Module的 **build.gradle** 里面添加引入依赖项
```gradle
implementation 'com.github.jenly1314:kvcache:1.0.0'

```

## 示例

Provider 说明
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
```
> 在初始化 KVCache 时，可能会用到 Provider

KVCache 初始化
```kotlin

// 建议在 Application 中进行初始化
KVCache.initialize(this, provider)

```
> 初始化 KVCache 时，如果不传 provider, 则会自动决定缓存实现：优先级从高到低依次为： MMKV -> DataStore -> SharedPreferences

KVCache 的使用（键值对的读写）
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

    val stringSet = setOf("1", "2", "3")
    KVCache.put("stringSet", stringSet)
    Log.d(TAG, "$cacheProvider: stringSet = ${KVCache.getStringSet("stringSet")}")

```

KVCache 的使用（补充：MMKV 额外缓存支持的类型）
```kotlin

    // 如果使用的是 MMKV 缓存实现，则额外支持缓存 ByteArray 和 Parcelable
    if (KVCache.cacheProvider() == KVCache.Provider.MMKV_CACHE) {

        val byteArray: ByteArray = byteArrayOf(1, 2, 3)
        KVCache.put("byteArray", byteArray)
        Log.d(TAG, "$cacheProvider: byteArray = ${KVCache.getByteArray("byteArray")?.toList()}")

        val p = ParcelableBean("ParcelableBean", 10, true)
        KVCache.put("parcelable", p)
        Log.d(TAG, "$cacheProvider: parcelable = ${KVCache.getParcelable<ParcelableBean>("parcelable")}")

    }

```

测试的缓存读写日志
```
// ---------------- Provider: MMKVCache
CacheProvider: MMKVCache
MMKVCache: float = 1.0
MMKVCache: int = 2
MMKVCache: double = 3.0
MMKVCache: long = 4
MMKVCache: boolean = true
MMKVCache: string = KVCache
MMKVCache: stringSet = [1, 2, 3]
MMKVCache: byteArray = [1, 2, 3]
MMKVCache: parcelable = ParcelableBean(name=ParcelableBean, i=10, bool=true)
// ------------------------------------------------ //

// ---------------- Provider: DataStoreCache
CacheProvider: DataStoreCache
DataStoreCache: float = 1.0
DataStoreCache: int = 2
DataStoreCache: double = 3.0
DataStoreCache: long = 4
DataStoreCache: boolean = true
DataStoreCache: string = KVCache
DataStoreCache: stringSet = [1, 2, 3]
// ------------------------------------------------ //

// ---------------- Provider: SharedPreferencesCache
CacheProvider: SharedPreferencesCache
SharedPreferencesCache: float = 1.0
SharedPreferencesCache: int = 2
SharedPreferencesCache: double = 3.0
SharedPreferencesCache: long = 4
SharedPreferencesCache: boolean = true
SharedPreferencesCache: string = KVCache
SharedPreferencesCache: stringSet = [1, 2, 3]
// ------------------------------------------------ //

```


更多使用详情，请查看[Demo](app)中的源码使用示例或直接查看[API帮助文档](https://jitpack.io/com/github/jenly1314/KVCache/latest/javadoc/)

## 感谢

[MMKV](https://github.com/tencent/MMKV)

[DataStore](https://developer.android.google.cn/jetpack/androidx/releases/datastore)


## 版本记录

#### v1.0.0：2022-7-19
*  KVCache初始版本

## 赞赏
如果你喜欢KVCache，或感觉KVCache帮助到了你，可以点右上角“Star”支持一下，你的支持就是我的动力，谢谢 :smiley:<p>
你也可以扫描下面的二维码，请作者喝杯咖啡 :coffee:
<div>
<img src="https://jenly1314.github.io/image/pay/wxpay.png" width="280" heght="350">
<img src="https://jenly1314.github.io/image/pay/alipay.png" width="280" heght="350">
<img src="https://jenly1314.github.io/image/pay/qqpay.png" width="280" heght="350">
<img src="https://jenly1314.github.io/image/alipay_red_envelopes.jpg" width="233" heght="350">
</div>

## 关于我
Name: <a title="关于作者" href="https://about.me/jenly1314" target="_blank">Jenly</a>

Email: <a title="欢迎邮件与我交流" href="mailto:jenly1314@gmail.com" target="_blank">jenly1314#gmail.com</a> / <a title="给我发邮件" href="mailto:jenly1314@vip.qq.com" target="_blank">jenly1314#vip.qq.com</a>

CSDN: <a title="CSDN博客" href="http://blog.csdn.net/jenly121" target="_blank">jenly121</a>

CNBlogs: <a title="博客园" href="https://www.cnblogs.com/jenly" target="_blank">jenly</a>

GitHub: <a title="GitHub开源项目" href="https://github.com/jenly1314" target="_blank">jenly1314</a>

Gitee: <a title="Gitee开源项目" href="https://gitee.com/jenly1314" target="_blank">jenly1314</a>

加入QQ群: <a title="点击加入QQ群" href="http://shang.qq.com/wpa/qunwpa?idkey=8fcc6a2f88552ea44b1411582c94fd124f7bb3ec227e2a400dbbfaad3dc2f5ad" target="_blank">20867961</a>
   <div>
       <img src="https://jenly1314.github.io/image/jenly666.png">
       <img src="https://jenly1314.github.io/image/qqgourp.png">
   </div>


   
