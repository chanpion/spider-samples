package com.chenpp.spider.media.service;

import com.chenpp.spider.media.search.EntityDoc;
import org.elasticsearch.cluster.metadata.IndexMetadata;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author April.Chen
 * @date 2024/6/19 09:58
 */
@Service
public class SearchService {

    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;

    public void searchText(String keyword) {
        Pageable pageable = PageRequest.of(0, 10);
        QueryBuilder query = QueryBuilders.queryStringQuery(keyword);
        //使用queryStringQuery完成单字符串查询
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(query).withPageable(pageable).withSort(Sort.by(Sort.Direction.DESC, "weight")).build();
        SearchHits<EntityDoc> searchHits = elasticsearchTemplate.search(searchQuery, EntityDoc.class);
    }


    public void searchPhrase(String keyword, String field, Pageable pageable) {
        QueryBuilder query = QueryBuilders.matchPhrasePrefixQuery(field, keyword);
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(query).withPageable(pageable).build();
        SearchHits<EntityDoc> searchHits = elasticsearchTemplate.search(searchQuery, EntityDoc.class);
    }

    public void searchTerm(String keyword, String field, Pageable pageable) {
        QueryBuilder query = QueryBuilders.termQuery(field, keyword);

        QueryBuilder multiMatchQuery = QueryBuilders.multiMatchQuery(keyword, field);
        QueryBuilder matchQuery = QueryBuilders.matchQuery(field, keyword);


        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(query).withPageable(pageable).build();
        SearchHits<EntityDoc> searchHits = elasticsearchTemplate.search(searchQuery, EntityDoc.class);
    }

    public void buildQuery() {
        QueryBuilders.matchAllQuery();
        QueryBuilder query = QueryBuilders.boolQuery()
                .filter(QueryBuilders.termQuery("label", "person"))
                .must(QueryBuilders.termQuery("name", "张三"))
                .must(QueryBuilders.termsQuery("age", 18, 19))
                .must(QueryBuilders.rangeQuery("age").gte(18).lte(100))
                .mustNot(QueryBuilders.termQuery("age", 18))
                .must(QueryBuilders.matchAllQuery())
                .must(QueryBuilders.matchPhraseQuery("name","张三"))
                .must(QueryBuilders.existsQuery("field"))
                .must(QueryBuilders.prefixQuery("name","张"))
                .should(QueryBuilders.termQuery("age", 18));
    }

    public void createIndex() {
        Settings settings = Settings.builder().put(IndexMetadata.SETTING_NUMBER_OF_SHARDS, 1).put(IndexMetadata.SETTING_NUMBER_OF_REPLICAS, 0).build();
        elasticsearchTemplate.indexOps(EntityDoc.class).create(settings.keySet().stream().collect(Collectors.toMap(k -> k, settings::get)));
        elasticsearchTemplate.indexOps(EntityDoc.class).createMapping();
        elasticsearchTemplate.indexOps(EntityDoc.class).delete();
    }

    public void addDocs(List<EntityDoc> docs) {
        List<IndexQuery> indexQueries = docs.stream()
                .map(doc -> new IndexQueryBuilder().withObject(doc).withId(doc.getUid()).build())
                .collect(Collectors.toList());
        elasticsearchTemplate.bulkIndex(indexQueries, EntityDoc.class);
    }

    public void deleteDoc() {
        elasticsearchTemplate.delete(new EntityDoc());
        elasticsearchTemplate.delete("id", EntityDoc.class);
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(QueryBuilders.matchQuery("name", "学生")).build();
        elasticsearchTemplate.delete(searchQuery, EntityDoc.class);
    }
}
