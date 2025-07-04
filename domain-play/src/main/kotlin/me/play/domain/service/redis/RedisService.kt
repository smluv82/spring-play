package me.play.domain.service.redis

import me.play.domain.dto.redis.*
import org.springframework.data.redis.connection.RedisConnection
import org.springframework.data.redis.connection.RedisStringCommands
import org.springframework.data.redis.connection.StringRedisConnection
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.types.Expiration
import org.springframework.stereotype.Service
import java.time.Duration
import java.util.concurrent.TimeUnit


@Service
class RedisService(
    private val redisTemplate: RedisTemplate<String, String>
) {

    /**
     * string 조회
     *
     * redis-cli 명령어 : GET key
     * ex) get test:string
     */
    fun getByString(key: String): String? {
        return redisTemplate.opsForValue()[key]
    }

    /**
     * string 저장
     *
     * redis-cli 명령어 : SET key value
     * ex) set test:string testValue
     */
    fun saveByString(reqDto: RedisStringDto) {
        redisTemplate.opsForValue().set(reqDto.key, reqDto.value, Duration.ofMinutes(reqDto.ttl))
    }


    /**
     * redisConnection 한번에 여러개 데이터 저장
     */
    fun saveByBulk(reqDtos: Collection<RedisStringDto>) {
        redisTemplate.executePipelined { connection: RedisConnection ->
            val stringRedisConnection = connection as StringRedisConnection
            reqDtos.forEach {
                stringRedisConnection[it.key, it.value, Expiration.from(it.ttl, it.timeUnit)] =
                    RedisStringCommands.SetOption.UPSERT
            }
            null
        }
    }


    /**
     * 정수형 숫자 1씩 증가 (count 파라미터의 값을 넣어주면 그 숫자만큼 증가)
     *
     * 사용처 : 카운팅, 선착순 등에서 사용 할 수 있을꺼 같음.
     *
     * redis-cli 명령어 : INCR key (1씩 증가), INCRBY key count
     * ex) INCR test:count, INCRBY test:count 10
     */
    fun saveByIncrement(key: String, count: Long = 1): Long =
        redisTemplate.opsForValue().increment(key, count) ?: 0

    /**
     * 정수형 숫자 1씩 감소
     *
     * redis-cli 명령어 : DECR key (1씩 감소), DECRBY key count
     * ex) DECR test:count, DECRBY test:count 10
     */
    fun saveByDecrement(key: String): Long =
        redisTemplate.opsForValue().decrement(key) ?: 0


    /**
     * list 전체 조회
     * 조회만 하고 데이터가 삭제 되지는 않음.
     *
     * redis-cli 명령어 : LRANGE key start end
     *
     */
    fun getByListAll(key: String): List<String> {
        return redisTemplate.opsForList().range(key, 0L, -1L) ?: emptyList()
    }

    /**
     * list 사이즈 조회
     */
    fun getByListSize(key: String): Long = redisTemplate.opsForList().size(key) ?: 0

    /**
     * left 기반으로 pop
     *
     * redis-cli 명령어 : LPOP key [count]    (count는 생략 가능하며 생략하면 1개씩 가져옴)
     * ex) LPOP test:list 2
     */
    fun getListByLeftPop(reqDto: RedisListGetDto): List<String> {
        return when {
            reqDto.count != null -> redisTemplate.opsForList().leftPop(reqDto.key, reqDto.count) ?: emptyList()
            // 값이 없으면 timeout 시간 만큼 대기
            reqDto.timeout != null -> {
                val result = redisTemplate.opsForList().leftPop(reqDto.key, Duration.ofMinutes(reqDto.timeout))
                return result?.let { listOf(it) } ?: emptyList()
            }

            else -> {
                val result = redisTemplate.opsForList().leftPop(reqDto.key)
                return result?.let { listOf(it) } ?: emptyList()
            }
        }
    }

    /**
     * right 기반으로 pop
     *
     * redis-cli 명령어 : RPOP key [count]    (count는 생략 가능하며 생략하면 1개씩 가져옴)
     * ex) RPOP test:list 2
     */
    fun getListByRightPop(reqDto: RedisListGetDto): List<String> {
        return when {
            reqDto.count != null -> redisTemplate.opsForList().rightPop(reqDto.key, reqDto.count) ?: emptyList()
            // 값이 없으면 timeout 시간 만큼 대기
            reqDto.timeout != null -> {
                val result = redisTemplate.opsForList().rightPop(reqDto.key, Duration.ofMinutes(reqDto.timeout))
                return result?.let { listOf(it) } ?: emptyList()
            }

            else -> {
                val result = redisTemplate.opsForList().rightPop(reqDto.key)
                return result?.let { listOf(it) } ?: emptyList()
            }
        }
    }

    /**
     * list 저장
     *
     * Left 기반으로 Push 후 Right 기반으로 Pop : FIFO (First-In First Out)
     * Left 기반으로 Push 후 Left 기반으로 Pop : LIFO (Last-In First Out)
     *
     *
     * 사용처 : 빠르게 조회가 필요한 경우 (페이징), 생산자 - 소비자 패턴에서도 사용 가능 할 듯
     *
     *
     * redis-cli 명령어 : LPUSH key value..., RPUSH key value...
     *
     */
    fun saveByList(reqDto: RedisListDto) {
        // 한번에 저장
        if (reqDto.isLeft) {
            redisTemplate.opsForList().leftPushAll(
                reqDto.key,
                reqDto.values
            )
        } else {
            redisTemplate.opsForList().rightPushAll(
                reqDto.key,
                reqDto.values
            )
        }

        // 개별 적으로 저장
//        if (reqDto.isLeft) {
//            for (value in reqDto.values) {
//                redisTemplate.opsForList().leftPush(
//                    reqDto.key,
//                    value
//                )
//            }
//        } else {
//            for (value in reqDto.values) {
//                redisTemplate.opsForList().rightPush(
//                    reqDto.key,
//                    value
//                )
//            }
//        }
    }


    /**
     * hashes 저장
     *
     * 많은 키를 만들기 부담스러울때 사용해도 좋을꺼 같고, 동일한 구조의 데이터를 반복적으로 저장할 때
     *
     * Hashes : key, hashKey, hashValue로 구성되어져 있고, hashKey는 유일 값이다.
     *
     */
    fun saveByHash(reqDto: RedisHashDto) {
        redisTemplate.opsForHash<String, String>().putAll(reqDto.key, reqDto.hashKeyToHashValue)
    }


    /**
     * hashes 조회
     */
    fun getByHash(key: String, hashKey: String): String? {
        return redisTemplate.opsForHash<String, String>()[key, hashKey]
    }


    /**
     * hashes 각 hashKeys 조회
     */
    fun getByKeys(key: String): Set<String> {
        return redisTemplate.opsForHash<String, String>().keys(key)
    }


    /**
     * hashes 각 hashValues 조회
     */
    fun getByValues(key: String): List<String> {
        return redisTemplate.opsForHash<String, String>().values(key)
    }


    /**
     * ZSet 저장
     *
     * 값의 score를 줘서 순위 매김, 선착순 대기열 (티켓팅) 같은 곳에서 활용 가능
     */
    fun saveByZSet(reqDto: RedisZSetDto) {
        redisTemplate.opsForZSet().add(reqDto.key, reqDto.value, reqDto.score)
    }


    /**
     * ZSet 랭크 조회
     */
    fun getByZSetRank(reqDto: RedisZSetRankDto): Long? {
        return if (reqDto.reverse)
            redisTemplate.opsForZSet().reverseRank(reqDto.key, reqDto.value)
        else
            redisTemplate.opsForZSet().rank(reqDto.key, reqDto.value)
    }


    /**
     * ZSet 범위 조회
     * 파라미터 end가 -1인 경우 뒤에 있는거 전체 조회
     */
    fun getByZSetRange(reqDto: RedisZSetRangeDto): Set<String>? {
        return if (reqDto.reverse)
            redisTemplate.opsForZSet().reverseRange(reqDto.key, reqDto.start, reqDto.end)
        else
            redisTemplate.opsForZSet().range(reqDto.key, reqDto.start, reqDto.end)
    }


    /**
     * ZSet 삭제
     * VALUE : 단건 value 삭제
     * RANGE : 범위 기반 삭제
     * SCORE : Score 기반 범위 삭제
     */
    fun removeByZSet(reqDto: RedisZSetRemoveDto) {
        when (reqDto.removeType) {
            ZSetRemoveType.VALUE -> {
                requireNotNull(reqDto.value)
                redisTemplate.opsForZSet().remove(reqDto.key, reqDto.value)
            }

            ZSetRemoveType.RANGE -> {
                requireNotNull(reqDto.start)
                requireNotNull(reqDto.end)
                redisTemplate.opsForZSet().removeRange(reqDto.key, reqDto.start.toLong(), reqDto.end.toLong())
            }

            ZSetRemoveType.SCORE -> {
                requireNotNull(reqDto.start)
                requireNotNull(reqDto.end)
                redisTemplate.opsForZSet()
                    .removeRangeByScore(reqDto.key, reqDto.start.toDouble(), reqDto.end.toDouble())
            }
        }
    }

    /**
     * ttl 설정
     * Lists, Hashes 등에도 TTL 설정이 필요한 경우 사용 할 듯
     *
     * redis-cli 명령어 :
     */
    fun saveExpire(reqDto: RedisExpireDto) {
        redisTemplate.expire(reqDto.key, Duration.of(reqDto.ttl, reqDto.unit))
    }


    /**
     * ttl 조회
     */
    fun getExpire(key: String, unit: TimeUnit): Long {
        return redisTemplate.getExpire(key, unit)
    }


    /**
     * 키 삭제 (블로킹 방식)
     *
     * redis-cli 명령어 :
     */
    fun delete(keys: Collection<String>) {
        redisTemplate.delete(keys)
    }


    /**
     * 키 삭제 (비동기 형태로 삭제할 키의 keyspace만 삭제하고, 다른 스레드에서 실제로 데이터를 삭제함)
     * delete의 시간 복잡도는 element 개수(M)만큼 O(M) 이지만, unlink는 O(1)이다.
     */
    fun unlink(keys: Collection<String>) {
        redisTemplate.unlink(keys)
    }
}


@Service
class RedisAnyService(
    private val redisTemplate: RedisTemplate<String, Any>
) {
    fun saveByString(reqDto: RedisAnyDto) {
        redisTemplate.opsForValue().set(reqDto.key, reqDto.value, reqDto.ttl, TimeUnit.MINUTES)
    }

    fun getByString(key: String): Any? {
        return redisTemplate.opsForValue()[key]
    }
}



