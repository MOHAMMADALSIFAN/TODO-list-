package sg.edu.nus.iss.todolist.Repo;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.todolist.Constant.Util;

@Repository
public class Hashrepo {

  @Autowired
  @Qualifier(Util.rediskey)
  RedisTemplate<String,String> template;

    // day 15 - slide 36
    public void create(String redisKey, String hashKey, String hashValue) {

        template.opsForHash().put(redisKey, hashKey, hashValue);
    }

    // day 15 - slide 37
    public Object get(String redisKey, String hashKey) {
        return template.opsForHash().get(redisKey, hashKey);
    }

    // day 15 - slide 38
    public Long delete(String redisKey, String hashKey) {
        return template.opsForHash().delete(redisKey, hashKey);
    }

    // day 15 - slide 39
    public Boolean keyExists(String redisKey, String hashKey) {
        return template.opsForHash().hasKey(redisKey, hashKey);
    }

    // day 15 - slide 40 (twisted)
    // <Object, Object> = <HashKeys, HaskValues>
    public Map<Object, Object> getEntries(String redisKey) {
        return template.opsForHash().entries(redisKey);
    }

    // day 15 - slide 40
    public Set<Object> getKeys(String redisKey) {
        return template.opsForHash().keys(redisKey);
    }

    public List<Object> getValues(String redisKey) {
        return template.opsForHash().values(redisKey);
    }

    // day 15 - slide 41
    public Long size(String redisKey) {
        return template.opsForHash().size(redisKey);
    }

    public void expire(String redisKey, Long expireValue) {
        Duration expireDuration = Duration.ofSeconds(expireValue);
        template.expire(redisKey, expireDuration);
    }
    // Retrieve a value for a specific key in a hash
    public String getValueFromMap(String redisKey, String key) {
      return (String) template.opsForHash().get(redisKey, key);
  }
    // Check if a hash exists
    public boolean hashExists(String redisKey) {
      return template.hasKey(redisKey);
  }
}
