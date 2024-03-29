package com.swm.constants;

public class SystemConstants
{
    /**
     * 文章 草稿
     */
    public static final int ARTICLE_STATUS_DRAFT = 1;
    /**
     * 文章 已发布
     */
    public static final int ARTICLE_STATUS_NORMAL = 0;
    /**
     * 热门文章展示数量
     * */
    public static final int HOT_ARTICLE_COUNTS = 10;

    public static final String STATUS_NORMAL = "0";
    /**
     * 友链审核通过
     * */
    public static final String LINK_STATUS_NORMAL = "0";
    /**
     * 评论为根评论
     * */
    public static final int ROOT_COMMENT = -1;
    /**
     * 文章评论
     * */
    public static final String ARTICLE_COMMENT = "0";
    /**
     * 友链评论
     * */
    public static final String LINK_COMMENT = "1";
    /**
     * redis存储浏览量的key
     * */
    public static final String REDISKEY_VIEWCOUNT = "article:viewCount";

    public static final String MENU = "C";

    public static final String BUTTON = "F";
/**
 * 正常
 * */
    public static final String NORMAL = "0";
    public static final String ADMIN = "1";
}