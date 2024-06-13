package com.chenpp.spider.baidu;

import com.chenpp.spider.DownloadPicture;
import org.apache.commons.io.FileUtils;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

import java.io.File;
import java.io.IOException;

/**
 * @author April.Chen
 * @date 2024/1/16 16:53
 */
public class BaiduImageMain {

    public static void main(String[] args) throws IOException {
        //百度图片 关键词
        String key = "知识图谱";
        String savePath = "/Users/chenpp/dev/images/1";
        File file = new File(savePath);
        if (!file.exists()) {
            FileUtils.forceMkdir(new File(savePath));
        }

        //控制爬取页数，一页30张图片
        for (int i = 0; i < 2; i++) {
            DownloadPicture downloadPicture = new DownloadPicture();
            String url = "https://image.baidu.com/search/acjson?tn=resultjson_com&ipn=rj&queryWord=" + key + "&word=" + key + "&pn=" + i * 3 + "0";
            Spider.create(downloadPicture)
                    .addUrl(url)
                    .addPipeline(new ConsolePipeline())
                    .addPipeline(new BaiduImagePipeline(savePath))
                    .thread(2)
                    .run();
        }
    }
}
