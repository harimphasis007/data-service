package com.data.service.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.data.service.domain.Project.class.getName());
            createCache(cm, com.data.service.domain.Program.class.getName());
            createCache(cm, com.data.service.domain.Program.class.getName() + ".projects");
            createCache(cm, com.data.service.domain.Project.class.getName() + ".projectLogs");
            createCache(cm, com.data.service.domain.Application.class.getName());
            createCache(cm, com.data.service.domain.Application.class.getName() + ".loans");
            createCache(cm, com.data.service.domain.Member.class.getName());
            createCache(cm, com.data.service.domain.Commitment.class.getName());
            createCache(cm, com.data.service.domain.Assignment.class.getName());
            createCache(cm, com.data.service.domain.Assignment.class.getName() + ".workerHistories");
            createCache(cm, com.data.service.domain.Worker.class.getName());
            createCache(cm, com.data.service.domain.WorkerHistory.class.getName());
            createCache(cm, com.data.service.domain.NotificationHistory.class.getName());
            createCache(cm, com.data.service.domain.ProjectLog.class.getName());
            createCache(cm, com.data.service.domain.InfoBeneficiaries.class.getName());
            createCache(cm, com.data.service.domain.Review.class.getName());
            createCache(cm, com.data.service.domain.Loan.class.getName());
            createCache(cm, com.data.service.domain.Loanpool.class.getName());
            createCache(cm, com.data.service.domain.Project.class.getName() + ".notificationHistories");
            createCache(cm, com.data.service.domain.Project.class.getName() + ".loans");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }

}
