package com.chenpp.crawler.entity;

import java.util.Date;

/**
 * @author April.Chen
 * @date 2024/1/16 11:14
 */
public class CmsContentPO {
    private String contentId;

    private String title;

    private String content;

    private Date releaseDate;

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }
}
