package com.chenpp.spider.media;

import com.chenpp.spider.media.process.MediaSpider;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author April.Chen
 * @date 2024/6/13 10:20
 */
@Slf4j
@MapperScan("com.chenpp.spider.media.mapper")
@SpringBootApplication
public class SpiderMediaApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpiderMediaApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("start media spider");
        new MediaSpider().start();
    }
}
