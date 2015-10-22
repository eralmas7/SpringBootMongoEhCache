package com.assignment.media.cache;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class CacheConfigFactory {

    private static final CacheConfig ehCacheConfig = new EHCacheConfig();
    private static final CacheConfig inMemoryCacheConfig = new InMemoryCacheConfig();
    private static final Map<CacheTypes, CacheConfig> cacheConfigMap = new HashMap<>();
    private static final String CACHE_TYPE_PROPERTY = "media.data.cache.type";
    static {
        cacheConfigMap.put(CacheTypes.EHCACHE, ehCacheConfig);
        cacheConfigMap.put(CacheTypes.INMEMORY, inMemoryCacheConfig);
    }

    /**
     * Based on cache type selected by user in properties file, return the cache configuration
     * associated.
     * 
     * @return
     */
    public static CacheConfig getCacheConfig() {
        return cacheConfigMap.getOrDefault(CacheTypes.valueOf(System.getProperty(CACHE_TYPE_PROPERTY, CacheTypes.EHCACHE.name())), inMemoryCacheConfig);
    }
}
