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
    @DisplayName("redis bulk set test")
    fun redisBulkSetTest() {
        log.info { "redis bulk set test" }
        val reqDtos = listOf(
            RedisStringDto(
                key = "testKey1",
                value = "testValue1",
                ttl = 10L,
                timeUnit = TimeUnit.MINUTES
            ),
            RedisStringDto(
                key = "testKey2",
                value = "testValue2",
                ttl = 10L,
                timeUnit = TimeUnit.MINUTES
            ),
            RedisStringDto(
                key = "testKey3",
                value = "testValue3",
                ttl = 10L,
                timeUnit = TimeUnit.MINUTES
            )
        )

        redisService.saveByBulk(reqDtos)

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
        assertThat(size).isEqualTo(0L)
    }


    @Test
    @DisplayName("hashes 관련 test")
    fun hashesTest() {
        log.info { "hashes test" }
        val key = "testHashKey"
        val hashKeyToHashValue = mapOf(
            "name" to "steven",
            "age" to "39",
            "country" to "korea"
        )

        val reqDto = RedisHashDto(key, hashKeyToHashValue)

        redisService.saveByHash(reqDto)

        val hashKeys = redisService.getByKeys(key)
        assertThat(hashKeys).isNotNull()
        assertThat(hashKeys.containsAll(setOf("name", "age", "country"))).isTrue()
        val hashValues = redisService.getByValues(key)
        assertThat(hashValues).isNotNull()
        assertThat(hashValues.containsAll(listOf("steven", "39", "korea"))).isTrue()
    }



    @Test
    @DisplayName("ZSet 관련 test")
    fun zSetTest() {
        log.info { "zSet test" }
        val key = "testZSetKey"

        val valueToScore = mapOf(
            "steven" to 1.0,
            "lina" to 4.0,
            "derek" to 2.0,
            "peter" to 3.0
        )

        valueToScore.forEach { (name, score) ->
            redisService.saveByZSet(RedisZSetDto(key, name, score))
        }

        var result = redisService.getByZSetRank(RedisZSetRankDto(key, "steven", false))
        assertThat(result).isEqualTo(0)
        result = redisService.getByZSetRank(RedisZSetRankDto(key, "steven", true))
        assertThat(result).isEqualTo(3)


        val result2 = redisService.getByZSetRange(RedisZSetRangeDto(key, 0, -1, false))
        assertThat(result2).isNotNull()
        result2?.forEachIndexed { index, s ->
            if (index == 0)
                assertThat(s).isEqualTo("steven")
            else if (index == 1)
                assertThat(s).isEqualTo("derek")
            else if (index == 2)
                assertThat(s).isEqualTo("peter")
            else if (index == 3)
                assertThat(s).isEqualTo("lina")
        }

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