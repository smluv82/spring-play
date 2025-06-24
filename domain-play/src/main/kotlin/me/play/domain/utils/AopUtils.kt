package me.play.domain.utils

import jakarta.servlet.http.HttpServletRequest
import mu.KotlinLogging
import org.aspectj.lang.ProceedingJoinPoint
import org.springframework.http.HttpMethod
import java.io.PrintWriter
import java.io.StringWriter

private val log = KotlinLogging.logger {}

/**
 * API 호출 시 request, response에 대한 logging util
 * Http Method가 GET인 경우, Exception 발생시에만 logging 하도록 하였음.
 */
fun communicationLogging(
    request: HttpServletRequest,
    joinPoint: ProceedingJoinPoint,
    ret: Any?,
    throwable: Throwable?
) {
    // HttpMethod가 GET 방식이고, Exception이 null인 경우에는 로깅하지 않음.
    if (HttpMethod.GET.name() == request.method && throwable == null) return

    var logMessage = """
            [Api Communication Info]
            method=${request.method}
            uri=${request.requestURI}
            headers=${getHeaders(request)}
            handlerMethod=${joinPoint.signature.toShortString()}
            args=${argsToString(joinPoint.args)}
        """.trimIndent()

    if (ret != null) {
        logMessage = logMessage.plus("\nreturns=${ret.javaClass.simpleName}{$ret}")
    }

    throwable?.let {
        logMessage = logMessage.plus("\nexceptions=${toString(it)}")
    }

    log.info(logMessage)
}

private fun getHeaders(request: HttpServletRequest): Map<String, String> =
    request.headerNames.asSequence().associateWith { request.getHeader(it) }

/**
 * 기본형(Long, Int, Boolean 등)은 그대로 출력
 * 객체는 JSON으로 변환
 */
private fun argsToString(args: Array<Any?>): String {
    return args.joinToString(", ") { arg ->
        when (arg) {
            is Number, is Boolean, is String -> arg.toString()
            else -> objectToJsonWithClassName(arg)
        }
    }
}

private fun objectToJsonWithClassName(obj: Any?): String {
    return try {
        val className = obj?.let { it::class.simpleName } ?: "Unknown"
        "$className : ${objectMapper().writeValueAsString(obj)}"
    } catch (e: Exception) {
        // 변환 실패시 객체의 toString()을 사용
        obj.toString()
    }
}

private fun toString(t: Throwable): String {
    val sw = StringWriter()
    val pw = PrintWriter(sw)
    t.printStackTrace(pw)
    pw.flush()
    return sw.toString()
}