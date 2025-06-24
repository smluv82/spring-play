package me.play.kotlinapi.common.aop

import jakarta.servlet.http.HttpServletRequest
import me.play.domain.utils.communicationLogging
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

@Aspect
@Component
class KotlinApiPrintLogAspect {
    @Throws(Throwable::class)
    @Around("within(me.play.kotlinapi.controller..*)")
    fun printLogAround(joinPoint: ProceedingJoinPoint): Any? {
        val request: HttpServletRequest =
            (RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes).request
        var ret: Any? = null
        var throwable: Throwable? = null

        try {
            ret = joinPoint.proceed()
            return ret
        } catch (t: Throwable) {
            throwable = t
            throw t
        } finally {
            communicationLogging(request, joinPoint, ret, throwable)
        }
    }
}