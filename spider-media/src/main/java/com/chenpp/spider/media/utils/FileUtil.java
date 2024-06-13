package com.chenpp.spider.media.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author April.Chen
 * @date 2024/1/16 16:24
 */
@Slf4j
public class FileUtil {

    public static void downloadFile(String url, String targetPath, String name) {
        File file = new File(targetPath + File.separator + name);
        URL netUrl;
        try {
            netUrl = new URL(url);
            DataInputStream dataInputStream = new DataInputStream(netUrl.openStream());
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            IOUtils.copy(dataInputStream, fileOutputStream);
            dataInputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            log.info("下载文件失败：" + url, e);
        }
    }
}
