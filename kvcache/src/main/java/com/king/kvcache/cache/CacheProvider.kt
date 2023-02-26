package com.king.kvcache.cache

/**
 * 缓存提供者
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
internal interface CacheProvider {

    /**
     * 获取缓存提供者
     */
    fun getProvider(): String
}