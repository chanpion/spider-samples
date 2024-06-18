package com.chenpp.spider.media.process.sink;

import com.chenpp.spider.media.entity.Entity;
import com.chenpp.spider.media.entity.Relation;

import java.util.Collection;

/**
 * @author April.Chen
 * @date 2024/6/18 19:22
 */
public interface SpiderSink {

    /**
     * 存储实体
     *
     * @param entity 实体
     */
    void sinkEntity(Entity entity);

    /**
     * 批量存储实体
     *
     * @param entities 实体列表
     */
    void batchSinkEntity(Collection<Entity> entities);

    /**
     * 存储关系
     *
     * @param relation 关系
     */
    void sinkRelation(Relation relation);

    /**
     * 批量存储关系
     *
     * @param relations 关系列表
     */
    void batchSinkRelation(Collection<Relation> relations);
}
