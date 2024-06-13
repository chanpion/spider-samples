package com.chenpp.spider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chenpp.spider.image.ImagePipeline;
import com.chenpp.spider.util.FileUtil;
import com.sun.rowset.internal.Row;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.FilePersistentBase;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author April.Chen
 * @date 2024/1/16 10:10
 */
public class DownloadPicture implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000)
            .addHeader("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2")
            .addHeader("Connection", "keep-alive")
            .addHeader("Upgrade-Insecure-Requests", "1")
            .addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
    private List<String> urls;
    private List<String> names;

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public List<String> getUrls() {
        return urls;
    }

    public List<String> getNames() {
        return names;
    }

    /**
     * 下载图片
     * author:bruce_q
     * 2017-2-5 20:47
     *
     * @param urlList
     * @param nameList
     */
    private void downloadPicture(ArrayList<String> urlList, ArrayList<String> nameList, String key) {
        URL url = null;
        for (int i = 0; i < urlList.size(); i++) {
            try {
                url = new URL(urlList.get(i));
                DataInputStream dataInputStream = new DataInputStream(url.openStream());
                String imageName = i + ".jpg";
                //设置下载路径
                File file = new File("/Users/chenpp/dev/images/" + key);
                if (!file.isDirectory()) {
                    file.mkdirs();
                }
                FileOutputStream fileOutputStream = new FileOutputStream("/Users/chenpp/dev/images/" + key + "\\" + imageName.trim());
                byte[] buffer = new byte[1024];
                int length;
                while ((length = dataInputStream.read(buffer)) > 0) {
                    fileOutputStream.write(buffer, 0, length);
                }
                dataInputStream.close();
                fileOutputStream.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) throws InterruptedException {
        //百度图片 关键词
        String key = "知识图谱";

        HashMap<String, String> header = new HashMap<>();
        header.put("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
        header.put("Connection", "keep-alive");
        header.put("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:60.0) Gecko/20100101 Firefox/60.0");
        header.put("Upgrade-Insecure-Requests", "1");

        ArrayList<String> nameList = new ArrayList<>();
        ArrayList<String> urlList = new ArrayList<>();
        //控制爬取页数，一页30张图片
        for (int i = 0; i < 2; i++) {
            DownloadPicture downloadPicture = new DownloadPicture();
            String url = "https://image.baidu.com/search/acjson?tn=resultjson_com&ipn=rj&queryWord=" + key + "&word=" + key + "&pn=" + i * 3 + "0";
            Spider.create(downloadPicture)
                    .addUrl(url)
                    .addPipeline(new ConsolePipeline())
                    .addPipeline(new ImageDownloadPipeline())
                    .thread(2)
                    .run();
//            urlList.addAll(downloadPicture.getUrls());
//            nameList.addAll(downloadPicture.getNames());
//            downloadPicture.downloadPicture(urlList, nameList, key);
        }

        Thread.sleep(10000);
    }


    public static class ImageDownloadPipeline extends FilePersistentBase implements Pipeline {
        private String savePath = "/Users/chenpp/dev/images";

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
}