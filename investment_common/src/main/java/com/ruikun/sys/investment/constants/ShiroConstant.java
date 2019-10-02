package com.ruikun.sys.investment.constants;

public class ShiroConstant {
    /**
     * 散列算法
     */
    public static final String HASHALGORITHM_NAME = "md5";

    /**
     * 散列次数
     */
    public static final int HASHITERATIONS = 1024;

    /**
     * shiro redis缓存时长(秒)
     */
    public static final long GLOB_EXPIRE = 1800;

    /**
     * shiro cache key 前缀
     */
    public static final String CACHE_KEY_PREFIX = "shiro-redis-cache:";

    /**
     * session 超时时间(秒)
     */
    public static final long SESSION_TIMEOUT = 1800;

    /**
     * shiro redis session key 前缀
     */
    public static final String SESSION_KEY_PREFIX = "shiro-redis-session:";

}
