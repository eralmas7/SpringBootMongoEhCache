package com.assignment.media.config;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import com.assignment.media.cache.CacheConfigFactory;

/**
 * All basic configuration needed by our media application to work.
 */
@Configuration
@EnableAutoConfiguration
@EnableCaching
@Component
public class CacheBootConfig {

    /**
     * Register cache manager with Spring.
     * 
     * @return
     */
    @Bean
    public CacheManager getCacheManager() {
        return CacheConfigFactory.getCacheConfig().getCacheManager();
    }

    /**
     * Factory which will give away the cache manager according to type selected.
     * 
     * @return
     */
    @Bean
    @SuppressWarnings("rawtypes")
    public FactoryBean getCacheFactory() {
        return CacheConfigFactory.getCacheConfig().getFactoryBean();
    }
}
