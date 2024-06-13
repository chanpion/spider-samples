package com.chenpp.spider.media.process;

import com.chenpp.spider.media.entity.Media;
import com.chenpp.spider.media.service.MediaService;
import com.chenpp.spider.media.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.FilePersistentBase;

import javax.annotation.Resource;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * @author April.Chen
 * @date 2024/6/13 11:37
 */
@Slf4j
@Component
public class MediaPipeline extends FilePersistentBase implements Pipeline {

    private String savePath = "/tmp/media";

    @Resource
    private MediaService mediaService;

    @Override
    public void process(ResultItems resultItems, Task task) {
        log.info("get page: {}", resultItems.getRequest().getUrl());
        String title = resultItems.get("title");
        String content = resultItems.get("content");
        log.info("content: {}", content);
        log.info("title: {}", title);
    }


    public void saveData() {
        Media media = new Media();
        mediaService.save(media);
    }

    public void downloadPicture(List<String> urlList) {
        urlList.forEach(url -> {
            try {
                String name = StringUtils.substringAfterLast(url, "/");
                File file = new File(savePath + name);
                // 文件不存在
                if (!file.exists()) {
                    FileUtil.downloadFile(url, savePath, name);
                    log.info("已经下载：" + savePath + name);
                } else {
                    log.info("该文件已存在" + savePath + name);
                }
            } catch (Exception e) {
                log.error("下载文件失败", e);
            }


        });
        for (int i = 0; i < urlList.size(); i++) {

        }
    }
}
