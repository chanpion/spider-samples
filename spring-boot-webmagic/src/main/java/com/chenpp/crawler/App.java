package com.chenpp.crawler;

import com.chenpp.crawler.service.XXXTask;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

/**
 * Hello world!
 */
@SpringBootApplication
@MapperScan(basePackages = "com.chenpp.crawler.dao")
public class App implements CommandLineRunner {
    @Autowired
    private XXXTask xxxTask;

    public static void main(String[] args) throws IOException {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        // 爬取数据
        xxxTask.crawl();
    }
}
