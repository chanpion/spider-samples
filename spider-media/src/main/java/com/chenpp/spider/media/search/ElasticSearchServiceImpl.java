package com.chenpp.spider.media.search;

import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author April.Chen
 * @date 2024/6/13 15:54
 */
@Service
public class ElasticSearchServiceImpl implements ElasticSearchService {
    @Resource
    private ElasticSearchRepository repository;

    @Override
    public void save(ElasticsearchDocument article) {
        repository.save(article);
    }

    @Override
    public ElasticsearchDocument findById(Long id) {
        return repository.findById(id).orElse(new ElasticsearchDocument());
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public long count() {
        return repository.count();
    }


    @Override
    public List<SearchHit<ElasticsearchDocument>> findByTitleOrContent(String title, String content) {
        return repository.findByTitleOrContent(title, content);
    }

    @Override
    public List<ElasticsearchDocument> findByTitle(String title) {
        List<SearchHit<ElasticsearchDocument>> searchHits = repository.findByTitle(title);
        return searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
    }
}
