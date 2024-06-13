package com.chenpp.spider.media.process;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author April.Chen
 * @date 2024/6/13 11:32
 */
@Slf4j
@Component
public class MediaProcessor implements PageProcessor {
    private static final String START_URL = "https://baike.baidu.com/item/关键词";

    private Site site = Site.me().setCycleRetryTimes(5).setRetryTimes(5).setSleepTime(500).setTimeOut(3 * 60 * 1000)
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:38.0) Gecko/20100101 Firefox/38.0")
            .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
            .addHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
            .setCharset("UTF-8")
            .setSleepTime(1000);


    @Override
    public void process(Page page) {
//        page.addTargetRequests(page.getHtml().links().regex("https://www\\.xxx\\.com/question/\\d+/answer/\\d+.*").all());
//        page.putField("title", page.getHtml().xpath("//h1[@class='QuestionHeader-title']/text()").toString());
//        page.putField("answer", page.getHtml().xpath("//div[@class='QuestionAnswer-content']/tidyText()").toString());
//        if (page.getResultItems().get("title") == null) {
//            // 如果是列表页，跳过此页，pipeline不进行后续处理
//            page.setSkip(true);
//        }
        // 提取页面数据，例如标题、摘要等
//        page.putField("title", page.getHtml().xpath("//div[@class='lemma-summary']"));
        // 提取链接到队列进行后续爬取
//        page.addTargetRequests(page.getHtml().links().regex(".*/item/.*").all());
        //获取页面内容
        String content = page.getHtml().xpath("//meta[@name='description']/@content").toString();
        String title = page.getHtml().xpath("//title/text()").toString();
        page.putField("content", content);
        page.putField("title", title);
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static String mySplitBaiDu(Page page) {
        String wordname = page.getUrl().toString().split("item/")[1];
        String basehtml = page.getJson().toString();
        String content = basehtml.split("bdText: \"")[1].split("@")[0];
        return content;
    }

}
