package me.play.domain.service.redis

import me.play.domain.dto.redis.*
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
import java.time.LocalDate
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import java.util.concurrent.TimeUnit


@SpringBootTest(classes = [RedisTestConfig::class])
@ActiveProfiles("local")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RedisServiceTest {
    private val log = KotlinLogging.logger {}

    @Autowired
    lateinit var redisService: RedisService

    @Autowired
    lateinit var redisAnyService: RedisAnyService


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

    @Test
    @DisplayName("redis string increment & decrement test")
    fun redisStringIncrementDecrementTest() {
        val key = "testIncrDecrKey"
        val repeat = 10

        for (i in 1..repeat) {
            redisService.saveByIncrement(key)
        }

        var result = redisService.getByString(key)
        assertThat(result).isEqualTo(repeat.toString())

        for (i in 1..repeat) {
            redisService.saveByDecrement(key)
        }

        result = redisService.getByString(key)
        assertThat(result).isEqualTo("0")
    }


    @Test
    @DisplayName("redis list > save")
    fun redisListTest() {
        log.info { "redis list test" }
        //given
        val key = "testListKey"
        val values = listOf("test1", "test2", "test3")
        val reqDto = RedisListDto(key, values, true)

        //when
        redisService.delete(listOf(key))
        redisService.saveByList(reqDto)
        val results = redisService.getByListAll(key)

        //then
        assertThat(results.size).isEqualTo(values.size)
        assertThat(results[0]).isNotNull().isEqualTo("test3")
        assertThat(results[1]).isNotNull().isEqualTo("test2")
        assertThat(results[2]).isNotNull().isEqualTo("test1")

        val results2 = redisService.getListByLeftPop(RedisListGetDto(key, 3))
        assertThat(results2.size).isEqualTo(values.size)
        assertThat(results2[0]).isNotNull().isEqualTo("test3")
        assertThat(results2[1]).isNotNull().isEqualTo("test2")
        assertThat(results2[2]).isNotNull().isEqualTo("test1")

        //위에서 Pop을 하였으므로 Size 0이어야 함.
        val size = redisService.getByListSize(key)
        assertThat(size).isEqualTo("0")
    }


    @Test
    @DisplayName("ttl 설정 및 조회")
    fun ttlTest() {
        val key = "testListKey"
        val values = listOf("test1", "test2", "test3")
        val ttl = 60L

        val listReqDto = RedisListDto(key, values, false)
        val ttlReqDto = RedisExpireDto(key, ttl, ChronoUnit.SECONDS)

        redisService.saveByList(listReqDto)
        redisService.saveExpire(ttlReqDto)
        val result = redisService.getExpire(key, TimeUnit.SECONDS)

        assertThat(result).isLessThanOrEqualTo(ttl)
    }


    @Test
    @DisplayName("redis any service serializer 관련 테스트")
    fun serviceSerializerTest() {
        val key = "testSerialize"
        val value = RedisValueDto(
            name = "steven",
            age = 39,
            dateTime = LocalDate.of(2025, 6, 25).atTime(LocalTime.MIN)
        )
        val ttl = 60L
        val reqDto = RedisAnyDto(key, value, ttl)

        redisAnyService.saveByString(reqDto)
        val result = redisAnyService.getByString(key)

        log.info("result : ${result}")
        assertThat(result).isNotNull()
    }
}

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = ["me.play.domain"])
class RedisTestConfig