package com.chenpp.spider.baidu;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.FilePersistentBase;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * @author April.Chen
 * @date 2024/1/16 16:52
 */
public class BaiduImagePipeline extends FilePersistentBase implements Pipeline {
    private String savePath = "/Users/chenpp/dev/images";

    public BaiduImagePipeline(String savePath) {
        this.savePath = savePath;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        List<String> urlList = resultItems.get("urlList");
        List<String> nameList = resultItems.get("nameList");
        for (int i = 0; i < urlList.size(); i++) {
            String url = urlList.get(i);
            String name = nameList.get(i);

            String fileFormat = "PNG";
            String[] arr = StringUtils.substringBefore(StringUtils.substringAfterLast(url, "/"), "?").split("&");
            for (String s : arr) {
                String[] pair = s.split("=");
                if (pair[0].equals("f")) {
                    fileFormat = pair[1];
                }
            }
            String filename = savePath + File.separator + name + "." + fileFormat;
            filename = filename.replaceAll("\\s", "_");
            File file = new File(filename);

            System.out.printf("save image %s to %s%n", url, filename);

            try {
                URL netUrl = new URL(url);
                IOUtils.copy(netUrl, file);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}