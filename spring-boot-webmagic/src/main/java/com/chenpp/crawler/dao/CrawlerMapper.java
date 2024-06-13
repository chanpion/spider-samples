package com.chenpp.crawler.dao;

import com.chenpp.crawler.entity.CmsContentPO;

/**
 * @author April.Chen
 * @date 2024/1/16 11:14
 */
public interface CrawlerMapper {
    int addCmsContent(CmsContentPO record);
}
