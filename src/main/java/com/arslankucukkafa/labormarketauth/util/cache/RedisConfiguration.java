package com.arslankucukkafa.labormarketauth.util.cache;

import com.arslankucukkafa.labormarketauth.idm.role.model.Permission;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.List;

@Configuration
public class RedisConfiguration {
    @Bean
    LettuceConnectionFactory connectionFactory() {
        return new LettuceConnectionFactory();
    }

    // todo: Burda type belirtmeden RedisTemplate bean oluşturmak istiyordum. Degiştirilicek sonra şuan sorunlar var. Permission Serlaztion sırasında.
    @Bean
    public RedisTemplate<String, List<Permission>> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, List<Permission>> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // JSON serileştirici ayarı
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);

        template.afterPropertiesSet();
        return template;
    }
}
