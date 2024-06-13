package com.chenpp.spider.image;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.selenium.SeleniumDownloader;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * @author April.Chen
 * @date 2024/1/16 10:42
 */
public class ImagePageProcessor  implements PageProcessor {


    @Override
    public void process(Page page) {

        /* 第一步,从当前页面中发现指定格式链接添加进目标用来进行下一步爬取 */
        page.addTargetRequests(page.getHtml().links().regex("https://www\\.****\\.com/.*\\.html").all());

        /* 第二步,从当前页面爬取数据 */

        // 获取title标题(使用xpah获取指定数据)
        String title=page.getHtml().xpath("//h1[@class='title']/text()").toString();

        // 判断是否有标题,无标题跳过当前页
        if (StringUtils.isEmpty(title)) {

            // 跳过当前页,pipeline不会进行输出
            page.setSkip(true);
        }

        //页面title装入page中的resultItems
        page.putField("title", title);

        //页面中得到指定位置的照片url,即img元素的src属性(使用xpah获取指定数据)
        List<String> PicUrl = page.getHtml().xpath("//div[@class='image']/p/span").css("img", "src").all();
        //另一种写法
        //List<String> PicUrl = page.getHtml().xpath("//div[@class='image']/p/span/img/@src").all();

        //照片路径装入page中的resultItems
        page.putField("PicUrl",PicUrl);

        // console打印当前页面全部内容(测试时用)
        //System.out.println(page.getHtml().toString());
    }

    @Override
    public Site getSite() {
        return Site.me().setRetryTimes(1).setSleepTime(2000);
    }

    //程序执行主程序入口
    public static void main(String[] args) {

        // 当需要使用seleniudownloader类时,加载webmaigc定义的属性参数,自定义指定路径
        System.getProperties().setProperty("selenuim_config","E:\\***\\src\\main\\resources\\config.ini");
        //spider创建ImagePageProcessor核心对象
        Spider.create(new ImagePageProcessor())
                //填写带照片的目标网址
                .addUrl("https://www.****.com/***/***.html")
                //实现console打印page中加入的resultItems元素
                .addPipeline(new ConsolePipeline())

                /*实现自定义ImagePiple打印page中加入的resultItems元素
                 *保存至E:\\spider文件下
                 */
                .addPipeline(new ImagePipeline("E:\\spider"))

                //一般不配置,不使用
                //.setScheduler(new QueueScheduler())

                //设置浏览器核心,当网页使用动态加载图片时需要配置seleniumDownloader
                .setDownloader(new SeleniumDownloader("E:\\***\\src\\main\\resources\\chromedriver.exe"))
                //设置线程数
                .thread(2)
                .run();

    }
    /**
     *SeleniumDownloader功能测试
     *该代码使用的是chrome浏览器,chromedriver百度自行下载
     *chrome和chromedriver版本保持严格匹配,我因为用错版本一直报错
     */
    @Test
    public void test2(){
        System.setProperty("webdriver.chrome.driver",
                "E:\\***\\src\\main\\resources\\chromedriver.exe");
        // 第二步：初始化驱动
        WebDriver driver = new ChromeDriver();
        // 第三步：获取目标网页
        driver.get("https://blog.csdn.net/qqzjyywx1/article/details/107702834");
        // 第四步：解析。以下就可以进行解了。使用webMagic等进行必要的解析。
        System.out.println("Page title is: " + driver.getTitle());
        System.out.println("Page title is: " + driver.getPageSource());

    }
}