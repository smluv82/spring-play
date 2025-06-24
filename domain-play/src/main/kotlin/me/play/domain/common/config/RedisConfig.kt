package me.play.domain.common.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.boot.autoconfigure.data.redis.RedisProperties
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration

@Configuration
@EnableCaching
class RedisConfig(
    private val redisProperties: RedisProperties
) {
    @Bean
    fun redisConnectionFactory(): LettuceConnectionFactory {
        val config = RedisStandaloneConfiguration(redisProperties.host, redisProperties.port)
        return LettuceConnectionFactory(config)
    }


    @Bean
    fun redisTemplate(
        connectionFactory: RedisConnectionFactory,
        objectMapper: ObjectMapper,
    ): RedisTemplate<String, Any> {
//    ): RedisTemplate<String, Any> {
//        return RedisTemplate<String, Any>().apply {
        return RedisTemplate<String, Any>().apply {
            this.connectionFactory = connectionFactory
            this.keySerializer = StringRedisSerializer()
            this.valueSerializer = GenericJackson2JsonRedisSerializer(objectMapper)
            this.hashKeySerializer = StringRedisSerializer()
            this.hashValueSerializer = StringRedisSerializer()
        }
    }

    @Bean
    fun cacheManager(cf: RedisConnectionFactory?, objectMapper: ObjectMapper): RedisCacheManager {
        val typeValidator = BasicPolymorphicTypeValidator.builder().allowIfSubType(Object::class.java).build()
        val om = objectMapper.copy()
        om.registerModules(KotlinModule.Builder().build())
        om.activateDefaultTyping(typeValidator, ObjectMapper.DefaultTyping.NON_FINAL)

        val redisCacheConfiguration: RedisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer()))
            .serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(
                    GenericJackson2JsonRedisSerializer(om)
                )
            )
            .entryTtl(Duration.ofMinutes(10L))

        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(cf!!)
            .cacheDefaults(redisCacheConfiguration).build()
    }
}