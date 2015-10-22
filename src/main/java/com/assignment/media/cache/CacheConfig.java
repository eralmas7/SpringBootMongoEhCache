package com.assignment.media.cache;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.cache.CacheManager;

/**
 * Based on cache type user has selected, give back the cache manager factory and cache manger from
 * it.
 * 
 * @param <T> T is the type of cache that we selected.
 */
public interface CacheConfig<T> {

    /**
     * Get the factory to produce cache manager.
     * 
     * @return
     */
    public FactoryBean<T> getFactoryBean();

    /**
     * Get the cache manager that factory will generate.
     * 
     * @return
     */
    public CacheManager getCacheManager();
}
