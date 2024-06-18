package com.chenpp.spider.media.process.sink;

import com.chenpp.spider.media.entity.Entity;
import com.chenpp.spider.media.entity.Relation;
import com.chenpp.spider.media.search.EntityDoc;
import com.chenpp.spider.media.search.EntityRepository;
import com.chenpp.spider.media.search.RelationDoc;
import com.chenpp.spider.media.search.RelationRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author April.Chen
 * @date 2024/6/18 19:28
 */
@Service
public class ElasticsearchSpiderSink implements SpiderSink {
    @Resource
    private EntityRepository entityRepository;

    @Resource
    private RelationRepository relationRepository;

    @Override
    public void sinkEntity(Entity entity) {
        EntityDoc entityDoc = new EntityDoc();
        BeanUtils.copyProperties(entity, entityDoc);
        entityRepository.save(entityDoc);
    }

    @Override
    public void batchSinkEntity(Collection<Entity> entities) {
        List<EntityDoc> docs = entities.stream().map(entity -> {
            EntityDoc entityDoc = new EntityDoc();
            BeanUtils.copyProperties(entity, entityDoc);
            return entityDoc;
        }).collect(Collectors.toList());
        entityRepository.saveAll(docs);
    }

    @Override
    public void sinkRelation(Relation relation) {
        RelationDoc relationDoc = new RelationDoc();
        BeanUtils.copyProperties(relation, relationDoc);
        relationRepository.save(relationDoc);
    }

    @Override
    public void batchSinkRelation(Collection<Relation> relations) {
        List<RelationDoc> docs = relations.stream().map(relation -> {
            RelationDoc relationDoc = new RelationDoc();
            BeanUtils.copyProperties(relation, relationDoc);
            return relationDoc;
        }).collect(Collectors.toList());

        relationRepository.saveAll(docs);
    }
}
