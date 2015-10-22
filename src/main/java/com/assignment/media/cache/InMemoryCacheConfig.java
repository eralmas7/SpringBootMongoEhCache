package com.assignment.media.cache;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheFactoryBean;
import org.springframework.cache.support.SimpleCacheManager;

/**
 * Cache configuration if user wants to go with in memory cache.
 */
public class InMemoryCacheConfig implements CacheConfig<ConcurrentMapCache> {

    @Override
    public FactoryBean<ConcurrentMapCache> getFactoryBean() {
        final ConcurrentMapCacheFactoryBean factoryBean = new ConcurrentMapCacheFactoryBean();
        return factoryBean;
    }

    @Override
    public CacheManager getCacheManager() {
        return new SimpleCacheManager();
    }
}
