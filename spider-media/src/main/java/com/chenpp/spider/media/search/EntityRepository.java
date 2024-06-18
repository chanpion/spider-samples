package com.chenpp.spider.media.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author April.Chen
 * @date 2024/6/17 19:02
 */
public interface EntityRepository extends ElasticsearchRepository<EntityDoc, String> {
}
