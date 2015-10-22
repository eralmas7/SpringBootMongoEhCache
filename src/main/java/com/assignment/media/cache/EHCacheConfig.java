package com.assignment.media.cache;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.core.io.ClassPathResource;
import com.assignment.media.exception.InitializationException;

/**
 * Cache configuration if user wants to go with Ehcache
 */
public class EHCacheConfig implements CacheConfig<net.sf.ehcache.CacheManager> {

    @Override
    public FactoryBean<net.sf.ehcache.CacheManager> getFactoryBean() {
        final EhCacheManagerFactoryBean factoryBean = new EhCacheManagerFactoryBean();
        factoryBean.setConfigLocation(new ClassPathResource(System.getProperty("media.assignment.ehcache.fileName", "ehcache.xml")));
        factoryBean.setCacheManagerName(System.getProperty("media.assignment.ehcache.name"));
        factoryBean.setShared(true);
        return factoryBean;
    }

    @Override
    public CacheManager getCacheManager() {
        try {
            return new EhCacheCacheManager(getFactoryBean().getObject());
        } catch (Exception e) {
            throw new InitializationException("Exception while creating EHCache Manager", e);
        }
    }
}
