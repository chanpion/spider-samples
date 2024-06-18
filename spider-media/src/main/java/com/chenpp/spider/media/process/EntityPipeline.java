package com.chenpp.spider.media.process;

import com.chenpp.spider.media.process.sink.SpiderSink;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.FilePersistentBase;

import javax.annotation.Resource;

/**
 * @author April.Chen
 * @date 2024/6/18 19:39
 */
public class EntityPipeline extends FilePersistentBase implements Pipeline {

    @Resource
    private SpiderSink spiderSink;

    @Override
    public void process(ResultItems resultItems, Task task) {

    }
}
