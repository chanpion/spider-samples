package com.chenpp.spider;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author April.Chen
 * @date 2024/1/16 17:11
 */
public class SinaBlogProcessor implements PageProcessor {
    private static final String URL_LIST = "http://blog\\\\.sina\\\\.com\\\\.cn/s/blog_\\\\w+\\\\.html";
    private static final String URL_POST = "http://blog\\\\.sina\\\\.com\\\\.cn/s/blog_\\\\w+\\\\.html";
    @Override
    public void process(Page page) {
        //列表页
        if (page.getUrl().regex(URL_LIST).match()) {
            page.addTargetRequests(page.getHtml().xpath("//div[@class=\"articleList\"]").links().regex(URL_POST).all());
            page.addTargetRequests(page.getHtml().links().regex(URL_LIST).all());
            //文章页
        } else {
            page.putField("title", page.getHtml().xpath("//div[@class='articalTitle']/h2"));
            page.putField("content", page.getHtml().xpath("//div[@id='articlebody']//div[@class='articalContent']"));
            page.putField("date",
                    page.getHtml().xpath("//div[@id='articlebody']//span[@class='time SG_txtc']").regex("\\((.*)\\)"));
        }
    }

    @Override
    public Site getSite() {
        return PageProcessor.super.getSite();
    }
}
