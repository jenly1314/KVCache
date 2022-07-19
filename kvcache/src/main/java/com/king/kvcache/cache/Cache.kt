package com.king.kvcache.cache


/**
 * 缓存
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
abstract class Cache : ICache, CacheProvider {

    override fun cacheProvider(): String {
        return this::class.java.simpleName
    }

}
