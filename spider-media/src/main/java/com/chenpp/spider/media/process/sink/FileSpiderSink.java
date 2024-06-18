package com.chenpp.spider.media.process.sink;

import com.chenpp.spider.media.entity.Entity;
import com.chenpp.spider.media.entity.Relation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @author April.Chen
 * @date 2024/6/18 19:28
 */
public class FileSpiderSink implements SpiderSink {
    @Value("${spider.file.entity-file-path}")
    private String entityFilePath;
    @Value("${spider.file.relation-file-path}")
    private String relationFilePath;

    private static final String[] ENTITY_CSV_HEADERS = {"uid", "label", "name", "properties"};
    private static final String[] RELATION_CSV_HEADERS = {"uid", "label", "name", "startId", "endId", "properties"};

    @Override
    public void sinkEntity(Entity entity) {

    }

    @Override
    public void batchSinkEntity(Collection<Entity> entities) {

    }

    @Override
    public void sinkRelation(Relation relation) {

    }

    @Override
    public void batchSinkRelation(Collection<Relation> relations) {

    }
}
