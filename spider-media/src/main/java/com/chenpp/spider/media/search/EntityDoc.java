package com.chenpp.spider.media.search;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Map;

/**
 * @author April.Chen
 * @date 2024/6/17 19:03
 */
@Data
@Document(indexName = "spider_entity")
public class EntityDoc {

    @Id
    @Field(store = true, index = true, type = FieldType.Text)
    private String uid;

    @Field(index = true, type = FieldType.Keyword)
    private String label;

    @Field(index = true, analyzer = "ik_smart", store = true, searchAnalyzer = "ik_smart", type = FieldType.Text)
    private String name;

    @Field(store = true, type = FieldType.Object)
    private Map<String, Object> properties;
}
