package com.chenpp.spider.baidu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author April.Chen
 * @date 2024/1/16 16:50
 */
public class BaiduImagePageProcessor implements PageProcessor {
    @Override
    public Site getSite() {
        return Site.me().setRetryTimes(1).setSleepTime(1000)
                .addHeader("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2")
                .addHeader("Connection", "keep-alive")
                .addHeader("Upgrade-Insecure-Requests", "1")
                .addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
    }

    @Override
    public void process(Page page) {
        List<String> urlList = new ArrayList<>();
        List<String> nameList = new ArrayList<>();
        JSONObject raw = JSON.parseObject(page.getRawText());
        JSONArray data = raw.getJSONArray("data");
        for (int i = 0; i < data.size(); i++) {
            String url = (String) data.getJSONObject(i).get("thumbURL");
            String name = (String) data.getJSONObject(i).get("fromPageTitleEnc");
            if (url != null) {
                urlList.add(url);
                nameList.add(name);
            }
        }
        page.putField("urlList", urlList);
        page.putField("nameList", nameList);


        // 部分三：从页面发现后续的url地址来抓取
        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-]+/[\\w\\-]+)").all());
    }
}
