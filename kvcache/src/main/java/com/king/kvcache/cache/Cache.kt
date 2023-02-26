package com.king.kvcache.cache

import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import com.tencent.mmkv.MMKV

/**
 * 缓存基类
 *
 * 内置实现 [Cache]的子类主要有以下几个：
 *
 * [MMKVCache]：基于 [MMKV] 实现的键值对缓存
 *
 * [DataStoreCache]：基于 [DataStore] 实现的键值对缓存
 *
 * [SharedPreferencesCache]：基于 [SharedPreferences] 实现的键值对缓存
 *
 * [MemoryCache]：基于 [Map] 实现的键值对缓存；主要应用场景：只需使用内存进行缓存即可满足需求；如：单元测试
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
abstract class Cache : ICache, CacheProvider {

    /**
     * 获取缓存提供者
     */
    override fun getProvider(): String {
        return this::class.java.simpleName
    }

}
