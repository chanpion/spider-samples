package com.chenpp.spider.media;

import com.chenpp.spider.media.search.ElasticSearchService;
import com.chenpp.spider.media.search.ElasticsearchDocument;
import com.chenpp.spider.media.search.EntityDoc;
import com.chenpp.spider.media.search.EntityRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author April.Chen
 * @date 2024/6/13 15:58
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpiderMediaApplication.class)
public class EsTest {
    @Resource
    private ElasticSearchService elasticSearchService;

    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;


    @Resource
    private EntityRepository entityRepository;

    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;

    /**
     * 创建索引和映射
     */
    @Test
    public void createIndex() {
        elasticsearchTemplate.indexOps(ElasticsearchDocument.class).create();
    }

    /**
     * 添加文档或者修改文档(以id为准)
     */
    @Test
    public void saveElasticSearch() {
        ElasticsearchDocument elasticSearch = new ElasticsearchDocument();
        elasticSearch.setId(1L);
        elasticSearch.setTitle("SpringData ElasticSearch");
        elasticSearch.setContent("Spring Data ElasticSearch 基于 spring data API 简化 elasticSearch操作，将原始操作elasticSearch的客户端API 进行封装 \n" +
                "    Spring Data为Elasticsearch Elasticsearch项目提供集成搜索引擎");
        elasticSearchService.save(elasticSearch);
    }

    @Test
    public void findById() {
        ElasticsearchDocument byId = elasticSearchService.findById(1L);
        System.out.println(byId);
    }

    @Test
    public void deleteById() {
        elasticSearchService.deleteById(100L);

    }

    @Test
    public void count() {
        long count = elasticSearchService.count();
        System.out.println(count);
    }


    @Test
    public void findByTitleOrContent() {
        List<SearchHit<ElasticsearchDocument>> byTitleOrContent = elasticSearchService.findByTitleOrContent("xxxxxxSpringData", "elasticSearch");
        for (SearchHit<ElasticsearchDocument> elasticSearchService : byTitleOrContent) {
            List<String> title = elasticSearchService.getHighlightField("title");
            System.out.println(title);
            List<String> content = elasticSearchService.getHighlightField("content");
            System.out.println(content);
        }
    }

    @Test
    public void addEntityDoc() {
        EntityDoc doc = new EntityDoc();
        doc.setUid(System.nanoTime() + "");
        doc.setLabel("person");
        doc.setName("张三");

        Map<String, Object> properties = new HashMap<>();
        properties.put("age", 18);
        properties.put("sex", "男");
        properties.put("name", "张三");

        doc.setProperties(properties);

        entityRepository.save(doc);
    }
}
