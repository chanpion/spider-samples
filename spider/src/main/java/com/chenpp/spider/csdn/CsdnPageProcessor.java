package com.chenpp.spider.csdn;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

/**
 * @author April.Chen
 * @date 2024/1/22 09:52
 */
public class CsdnPageProcessor implements PageProcessor {
    @Override
    public void process(Page page) {
        Selectable selectable = page.getHtml().xpath("//div[@class='so-items-normal']");
        System.out.println(selectable);
    }

    @Override
    public Site getSite() {
        return Site.me().setRetryTimes(1).setSleepTime(1000)
                .addHeader("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2")
                .addHeader("Connection", "keep-alive")
                .addHeader("Upgrade-Insecure-Requests", "1")
                .addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
    }
}
