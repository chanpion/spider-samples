CREATE TABLE `cms_content`(
    `contentId`   varchar(40)  NOT NULL COMMENT '内容ID',
    `title`       varchar(150) NOT NULL COMMENT '标题',
    `content`     longtext COMMENT '文章内容',
    `releaseDate` datetime     NOT NULL COMMENT '发布日期',
    PRIMARY KEY (`contentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS内容表';