package com.chenpp.spider.media.utils;

import com.chenpp.spider.media.entity.Entity;
import com.chenpp.spider.media.search.EntityDoc;
import org.springframework.beans.BeanUtils;

/**
 * @author April.Chen
 * @date 2024/6/18 19:30
 */
public class ObjectConverter {

    public static EntityDoc convert(Entity entity) {
        EntityDoc entityDoc = new EntityDoc();
        BeanUtils.copyProperties(entity, entityDoc);
        return entityDoc;
    }
}
