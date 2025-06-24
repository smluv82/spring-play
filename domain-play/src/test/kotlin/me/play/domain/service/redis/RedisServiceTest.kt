package me.play.domain.service.redis

import me.play.domain.dto.redis.RedisStringDto
import mu.KotlinLogging
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.ActiveProfiles


@SpringBootTest(classes = [RedisTestConfig::class])
@ActiveProfiles("local")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RedisServiceTest {
    private val log = KotlinLogging.logger {}

    @Autowired
    lateinit var redisService: RedisService


    @Test
    @DisplayName("redis string > set, get test")
    fun redisStringTest() {
        log.info { "redis string test" }
        val key = "testKey"
        val value = "testValue"
        val ttl = 10L

        val reqDto = RedisStringDto(key, value, ttl)

        redisService.saveByString(reqDto)

        val getValue = redisService.getByString(key)

        assertThat(getValue).isNotNull()
        assertThat(getValue).isEqualTo(value)
    }
}

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = ["me.play.domain"])
class RedisTestConfig