package com.chenpp.spider.media.process.sink;

import com.chenpp.spider.media.entity.Entity;
import com.chenpp.spider.media.entity.Relation;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @author April.Chen
 * @date 2024/6/18 19:23
 */
@Service
public class MixedSpiderSink implements SpiderSink {
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
