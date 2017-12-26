package zero.service.cache.redis;

import zero.service.cache.Cache;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author ningcheng
 * @date 2017/9/7
 */
public class RedisCache<K, T> implements Cache<K, T>, InitializingBean {

    private RedisTemplate<K, T> redisTemplate;
    private String name;
    private int timeout;

    @Override
    public void afterPropertiesSet() throws Exception {
        // 默认jdk的序列化
    }

    @Override
    public T get(K key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public List<T> mutiGet(Collection<K> keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }

    @Override
    public void mutiPut(Map<? extends K, ? extends T> map) {
        redisTemplate.opsForValue().multiSet(map);
    }

    @Override
    public void put(K key, T value) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    @Override
    public void evict(K key) {
        redisTemplate.delete(key);
    }

    @Override
    public void multiEvict(List<K> keys) {
        redisTemplate.delete(keys);
    }

    public <V> V eval(RedisScript<V> script, List<K> keys, Object... args) {
        return redisTemplate.execute(script, keys, args);
    }

    public RedisTemplate<K, T> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<K, T> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

}