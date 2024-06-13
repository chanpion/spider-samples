package com.chenpp.crawler.service;

import com.chenpp.crawler.dao.CrawlerMapper;
import com.chenpp.crawler.entity.CmsContentPO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Date;
import java.util.UUID;

/**
 * @author April.Chen
 * @date 2024/1/16 11:16
 */
@Component
public class XXXPipeline implements Pipeline {
    private static final Logger LOGGER = LoggerFactory.getLogger(XXXPipeline.class);

    @Autowired
    private CrawlerMapper crawlerMapper;

    public void process(ResultItems resultItems, Task task) {
        String title = resultItems.get("title");
        String answer = resultItems.get("answer");

        CmsContentPO contentPO = new CmsContentPO();
        contentPO.setContentId(UUID.randomUUID().toString());
        contentPO.setTitle(title);
        contentPO.setReleaseDate(new Date());
        contentPO.setContent(answer);

        try {
            boolean success = crawlerMapper.addCmsContent(contentPO) > 0;
            LOGGER.info("保存文章成功：{}", title);
        } catch (Exception ex) {
            LOGGER.error("保存文章失败", ex);
        }
    }
}