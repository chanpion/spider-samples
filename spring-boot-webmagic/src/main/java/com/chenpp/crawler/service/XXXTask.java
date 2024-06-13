package com.chenpp.crawler.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author April.Chen
 * @date 2024/1/16 11:18
 */
@Component
public class XXXTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(XXXPipeline.class);

    @Autowired
    private XXXPipeline XXXPipeline;

    @Autowired
    private XXXPageProcessor xxxPageProcessor;

    private ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();

    public void crawl() {
        // 定时任务，每10分钟爬取一次
        timer.scheduleWithFixedDelay(() -> {
            Thread.currentThread().setName("xxxCrawlerThread");

            try {
                Spider.create(xxxPageProcessor)
                        // 从https://www.xxx.com/explore开始抓
                        .addUrl("https://www.xxx.com/explore")
                        // 抓取到的数据存数据库
                        .addPipeline(XXXPipeline)
                        // 开启2个线程抓取
                        .thread(2)
                        // 异步启动爬虫
                        .start();
            } catch (Exception ex) {
                LOGGER.error("定时抓取数据线程执行异常", ex);
            }
        }, 0, 10, TimeUnit.MINUTES);
    }
}