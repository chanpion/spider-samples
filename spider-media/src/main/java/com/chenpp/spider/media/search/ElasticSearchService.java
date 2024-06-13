package com.chenpp.spider.media.search;

import org.springframework.data.elasticsearch.core.SearchHit;

import java.util.List;

/**
 * @author April.Chen
 * @date 2024/6/13 15:54
 */
public interface ElasticSearchService {

    //保存和修改
    void save(ElasticsearchDocument article);

    //查询id
    ElasticsearchDocument findById(Long id);

    //删除指定ID数据
    void deleteById(Long id);

    long count();

    List<SearchHit<ElasticsearchDocument>> findByTitleOrContent(String title, String content);

    /**
     * 根据标题查询
     *
     * @param title 标题
     * @return 文档
     */
    List<ElasticsearchDocument> findByTitle(String title);

}
