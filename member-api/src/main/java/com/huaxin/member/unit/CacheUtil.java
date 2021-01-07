package com.huaxin.member.unit;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Map;

public class CacheUtil {

    private static Logger logger = LogManager.getLogger(CacheUtil.class);
    public static final ProUtil proUtil = new ProUtil("/redis.properties");
    private static JedisPoolConfig config = new JedisPoolConfig();
    private static final String projectName;
    public static JedisPool jedisPool;
    private static final String HOSTNAME;
    private static final int PORT;
    private static final String PASSWORD;
    private static final int TIMEOUT;
    private static final int MaxIdle;
    private static final int MaxTotal;
    private static boolean TEST_ON_BORROW;

    public CacheUtil() {
    }

    private static void initialPool() {
        config.setBlockWhenExhausted(true);
        config.setLifo(true);
        config.setMaxIdle(MaxIdle);
        config.setMaxTotal(MaxTotal);
        config.setMaxWaitMillis(-1L);
        config.setMinEvictableIdleTimeMillis(1800000L);
        config.setTestOnBorrow(TEST_ON_BORROW);
        config.setTestWhileIdle(true);
        jedisPool = new JedisPool(config, HOSTNAME, PORT, TIMEOUT, PASSWORD);
    }

    private static Jedis getJedis() {
        if (jedisPool == null) {
            initialPool();
        }

        Jedis jedis = null;

        try {
            if (jedisPool != null) {
                jedis = jedisPool.getResource();
            }
        } catch (Exception var2) {
            var2.printStackTrace();
        }

        return jedis;
    }

    public static Long update(String groupName, String key, String value) {
        Jedis jedis = getJedis();
        Long s = null;

        try {
            groupName = projectName + groupName;
            s = jedis.hset(groupName, key, value);
        } catch (Exception var9) {
            var9.printStackTrace();
            logger.error("", var9);
        } finally {
            jedis.close();
        }

        return s;
    }

    public static String update(String groupName, Map<String, String> map) {
        Jedis jedis = getJedis();
        String s = null;

        try {
            groupName = projectName + groupName;
            s = jedis.hmset(groupName, map);
        } catch (Exception var8) {
            var8.printStackTrace();
            logger.error("", var8);
        } finally {
            jedis.close();
        }

        return s;
    }

    public static String get(String groupName) {
        Jedis jedis = getJedis();
        String s = null;

        try {
            groupName = projectName + groupName;
            s = jedis.hgetAll(groupName).toString();
        } catch (Exception var7) {
            var7.printStackTrace();
            logger.error("", var7);
        } finally {
            jedis.close();
        }

        return s;
    }

    public static String get(String groupName, String key) {
        Jedis jedis = getJedis();
        String s = null;

        try {
            groupName = projectName + groupName;
            s = jedis.hget(groupName, key);
        } catch (Exception var8) {
            var8.printStackTrace();
            logger.error("", var8);
        } finally {
            jedis.close();
        }

        return s;
    }

    public static Long delete(String groupName, String key) {
        Jedis jedis = getJedis();
        Long s = null;

        try {
            groupName = projectName + groupName;
            s = jedis.hdel(groupName, new String[]{key});
        } catch (Exception var8) {
            var8.printStackTrace();
            logger.error("", var8);
        } finally {
            jedis.close();
        }

        return s;
    }

    public static Long delete(String groupName) {
        Jedis jedis = getJedis();
        Long s = null;

        try {
            groupName = projectName + groupName;
            s = jedis.del(groupName);
        } catch (Exception var7) {
            var7.printStackTrace();
            logger.error("", var7);
        } finally {
            jedis.close();
        }

        return s;
    }

    public static String putMap(String groupName, Map<String, Object> map) {
        Jedis jedis = getJedis();
        String s = null;

        try {
            groupName = projectName + groupName;
            s = jedis.set(groupName.getBytes(), SerializeUtil.serialize(map));
        } catch (Exception var8) {
            var8.printStackTrace();
            logger.error("", var8);
        } finally {
            jedis.close();
        }

        return s;
    }

    public static Map<String, Object> getMap(String groupName) {
        Jedis jedis = getJedis();
        Map map = null;

        try {
            groupName = projectName + groupName;
            map = (Map)SerializeUtil.unserialize(jedis.get(groupName.getBytes()));
        } catch (Exception var7) {
            var7.printStackTrace();
            logger.error("", var7);
        } finally {
            jedis.close();
        }

        return map;
    }

    static {
        projectName = proUtil.getProperty("redis.projectName");
        jedisPool = null;
        HOSTNAME = proUtil.getProperty("redis.hostName");
        PORT = Integer.valueOf(proUtil.getProperty("redis.port"));
        PASSWORD = proUtil.getProperty("redis.passWord");
        TIMEOUT = Integer.valueOf(proUtil.getProperty("redis.timeOut"));
        MaxIdle = Integer.valueOf(proUtil.getProperty("redis.maxIdle"));
        MaxTotal = Integer.valueOf(proUtil.getProperty("redis.maxTotal"));
        TEST_ON_BORROW = Boolean.valueOf(proUtil.getProperty("redis.testOnBorrow"));
    }

}
