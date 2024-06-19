package com.chenpp.spider.media.controller;

import com.chenpp.spider.media.entity.AppResponse;
import com.chenpp.spider.media.search.EntityDoc;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.stream.Collectors;

/**
 * @author April.Chen
 * @date 2024/6/19 10:05
 */
@RestController
@RequestMapping("/search")
public class SearchController {

    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;

    public AppResponse<?> search(String keyword, @PageableDefault(sort = "weight", direction = Sort.Direction.DESC) Pageable pageable) {
        QueryBuilder query = QueryBuilders.queryStringQuery(keyword);
        //使用queryStringQuery完成单字符串查询
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(query).withPageable(pageable).build();
        SearchHits<EntityDoc> searchHits = elasticsearchTemplate.search(searchQuery, EntityDoc.class);

        return AppResponse.success(searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList()));
    }
}
