package com.chenpp.spider.media.service;

import com.chenpp.spider.media.entity.Entity;
import com.chenpp.spider.media.search.ElasticSearchRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author April.Chen
 * @date 2024/6/17 18:56
 */
@Slf4j
@Service
public class SpiderStorageService {

    @Resource
    private EntityService entityService;

    @Resource
    private ElasticSearchRepository elasticSearchRepository;


    public void saveEntity(Entity entity) {
        entityService.save(entity);
    }
}
