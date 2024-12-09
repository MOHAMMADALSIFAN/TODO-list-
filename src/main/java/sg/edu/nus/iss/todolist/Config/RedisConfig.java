package sg.edu.nus.iss.todolist.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import sg.edu.nus.iss.todolist.Constant.Util;

@Configuration
public class RedisConfig {
  @Value("${spring.data.redis.host}")
  private String redisHost;

  @Value("${spring.data.redis.port}")
  private Integer redisPort;

  @Value("${spring.data.redis.username}")
  private String redisUsername;

  @Value("${spring.data.redis.password}")
  private String redisPassword;

  @Bean
  public JedisConnectionFactory jedisConnectionFactory() {
    RedisStandaloneConfiguration rsc = new RedisStandaloneConfiguration();
    rsc.setHostName(redisHost);
    rsc.setPort(redisPort);

    if (redisUsername.trim().length() > 0) {
      rsc.setUsername(redisUsername);
      rsc.setPassword(redisPassword);
    }

    JedisClientConfiguration jcc = JedisClientConfiguration.builder().build();
    JedisConnectionFactory jcf = new JedisConnectionFactory(rsc, jcc);
    jcf.afterPropertiesSet();

    return jcf;
  }

  @Bean(Util.rediskey)
  public RedisTemplate<String, String> redisObjectTemplate01() {
    RedisTemplate<String, String> template = new RedisTemplate<>();
    template.setConnectionFactory(jedisConnectionFactory());
    template.setKeySerializer(new StringRedisSerializer());
    template.setValueSerializer(new StringRedisSerializer());
    template.setHashKeySerializer(new StringRedisSerializer());
    template.setHashValueSerializer(new StringRedisSerializer());
    return template;
  }
}