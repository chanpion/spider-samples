package com.chenpp.spider.csdn;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

/**
 * @author April.Chen
 * @date 2024/1/22 09:52
 */
public class CsdnMain {

    public static void main(String[] args) {
        String url = "https://so.csdn.net/so/search?spm=1001.2101.3001.4498&q=%E7%9F%A5%E8%AF%86%E5%9B%BE%E8%B0%B1&t=blog&u=";
        Spider.create(new CsdnPageProcessor())
                .addUrl(url)
                .addPipeline(new ConsolePipeline())
                .thread(2)
                .run();
    }
}
