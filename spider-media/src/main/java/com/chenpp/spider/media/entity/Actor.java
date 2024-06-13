package com.chenpp.spider.media.entity;

import lombok.Data;

import java.util.Date;

/**
 * 演员实体
 *
 * @author April.Chen
 * @date 2024/6/13 10:27
 */
@Data
public class Actor {
    private Long id;
    private String name;
    private Date born;
}
