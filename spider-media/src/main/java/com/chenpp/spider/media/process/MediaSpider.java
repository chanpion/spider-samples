package com.chenpp.spider.media.process;

import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

/**
 * @author April.Chen
 * @date 2024/6/13 14:00
 */
@Slf4j
public class MediaSpider {

    private MediaPipeline mediaPipeline = new MediaPipeline();
    private MediaProcessor mediaProcessor = new MediaProcessor();

    public void start() {
        Spider.create(mediaProcessor)
                // 从https://www.xxx.com/explore开始抓
                .addUrl("https://baike.baidu.com/item/石川澪")
//                .addPipeline(new ConsolePipeline())
                // 抓取到的数据存数据库
                .addPipeline(mediaPipeline)
                // 开启2个线程抓取
                .thread(2)
                // 异步启动爬虫
                .start();
    }
}
