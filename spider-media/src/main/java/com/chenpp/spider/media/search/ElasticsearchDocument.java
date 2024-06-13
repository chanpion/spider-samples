package com.chenpp.spider.media.search;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * @author April.Chen
 * @date 2024/6/13 15:42
 */
@Document(indexName = "cpp_media", createIndex = true)
@Data
public class ElasticsearchDocument {

    @Id
    @Field(store = true, index = false, type = FieldType.Long)
    private Long id;

    @Field(index = true, analyzer = "ik_smart", store = true, searchAnalyzer = "ik_smart", type = FieldType.Text)
    private String title;

    @Field(index = true, analyzer = "ik_smart", store = true, searchAnalyzer = "ik_smart", type = FieldType.Text)
    private String content;

    @Field(type = FieldType.Date)
    private Date releaseDate;

}
