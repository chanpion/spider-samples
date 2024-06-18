package com.chenpp.spider.media.config;

import com.chenpp.spider.media.process.sink.ElasticsearchSpiderSink;
import com.chenpp.spider.media.process.sink.FileSpiderSink;
import com.chenpp.spider.media.process.sink.MixedSpiderSink;
import com.chenpp.spider.media.process.sink.SpiderSink;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author April.Chen
 * @date 2024/6/18 19:40
 */
@Configuration
public class SpiderConfig {

    @Value("${spider.sink-type}")
    private String sinkType;

    @Bean
    SpiderSink spiderSink() {
        switch (sinkType) {
            case "es":
                return new ElasticsearchSpiderSink();
            case "mixed":
                return new MixedSpiderSink();
            case "file":
            default:
                return new FileSpiderSink();
        }
    }
}
