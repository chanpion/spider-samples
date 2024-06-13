package com.chenpp.spider.media;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author April.Chen
 * @date 2024/1/17 12:21
 */
public class MediaPageProcessor implements PageProcessor {
    @Override
    public void process(Page page) {

    }

    @Override
    public Site getSite() {
        return PageProcessor.super.getSite();
    }

    private void writeFile(String filename, Map<String, String> map) {
        List<String> lines = map.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.toList());
        File file = new File(filename);
        try {
            FileUtils.writeLines(file, lines, System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
