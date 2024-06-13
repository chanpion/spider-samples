package com.chenpp.spider.image;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.FilePersistentBase;

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
 * @date 2024/1/16 10:43
 */
public class ImagePipeline extends FilePersistentBase implements Pipeline {
    private Logger logger = LoggerFactory.getLogger(getClass());
    //文件保存路径,之后配置
    String savepath = null;


    /**
     * create a default path"/data/webmagic/"
     */
    public ImagePipeline() {
        setPath(" /Users/chenpp/dev/images");
    }

    public ImagePipeline(String path) {
        setPath(path);
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        //保存路径我设置为  自定义路径/网站名/标题名/文件名.png
        savepath = this.path + task.getUUID() + PATH_SEPERATOR + resultItems.get("title") + PATH_SEPERATOR;

        List<String> PicUrl = resultItems.get("PicUrl");
        // 当图片链接存在,生成savepath路径的文件夹,然后下载图片
        if (PicUrl.size() > 0) {
            checkAndMakeParentDirecotry(savepath);
            downloadPicture(PicUrl);
        } else {
            //当图片链接不存在
            System.out.println("该page图片链接不存在：" + resultItems.get("title"));
        }

    }

    //下载图片链接方法代码
    public void downloadPicture(List<String> urlList) {
        URL url = null;
        for (int i = 0; i < urlList.size(); i++) {
            try {
                String[] files = urlList.get(i).split("/");
                String name = files[files.length - 1];
                url = new URL(urlList.get(i));

                File file = new File(savepath + name);
                // 文件不存在
                if (!file.exists()) {
                    //referer防盗链
//                    URLConnection urlConnection=url.openConnection();
//                    urlConnection.setRequestProperty("referer","xxxurl");
//                    DataInputStream dataInputStream = new DataInputStream(urlConnection.getInputStream());

                    DataInputStream dataInputStream = new DataInputStream(url.openStream());
                    FileOutputStream fileOutputStream = new FileOutputStream(file);

                    byte[] buffer = new byte[1024 * 50];
                    int length;

                    while ((length = dataInputStream.read(buffer)) > 0) {
                        fileOutputStream.write(buffer, 0, length);
                    }
                    System.out.println("已经下载：" + savepath + name);
                    dataInputStream.close();
                    fileOutputStream.close();
                } else {
                    System.out.println("该文件已存在" + savepath + name);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                System.out.println("下载文件路径不存在：" + url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}