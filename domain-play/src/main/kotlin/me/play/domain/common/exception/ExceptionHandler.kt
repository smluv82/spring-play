package me.play.domain.common.exception

import me.play.domain.common.response.ApiResponse
import me.play.domain.common.response.ResponseCode
import mu.KotlinLogging
import org.apache.commons.lang3.exception.ExceptionUtils
import org.springframework.beans.BeanInstantiationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.resource.NoResourceFoundException

@RestControllerAdvice
class ExceptionHandler {
    private val log = KotlinLogging.logger {}

    @ExceptionHandler(CommonException::class)
    fun commonExceptionHandle(
        exception: CommonException
    ): ResponseEntity<ApiResponse<Unit>> {
        log.warn { exception }
        return ResponseEntity(ApiResponse.error(exception), exception.responseCode.httpStatus)
    }

    @ExceptionHandler(NoSuchElementException::class)
    fun noSuchElementExceptionHandle(
        exception: NoSuchElementException
    ): ResponseEntity<ApiResponse<Unit>> {
        log.warn { exception }
        return ResponseEntity(
            ApiResponse(
                responseCode = ResponseCode.NOT_FOUND_ID,
                errorMessage = ExceptionUtils.getMessage(exception)
            ),
            HttpStatus.NOT_FOUND
        )
    }

    @ExceptionHandler(BeanInstantiationException::class)
    fun beanInstantiationExceptionHandle(
        exception: BeanInstantiationException
    ): ResponseEntity<ApiResponse<Unit>> {
        log.warn { exception }
        return (exception.cause as? CommonException)?.let { commonExceptionHandle(it) } ?: exceptionHandle(exception)
    }

    @ExceptionHandler(NoResourceFoundException::class)
    fun handleNoResourceFoundException(exception: NoResourceFoundException): ResponseEntity<ApiResponse<Unit>> {
        log.warn { exception }
        return ResponseEntity(ApiResponse.error(ResponseCode.NOT_FOUND, exception), HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(Throwable::class)
    fun exceptionHandle(
        exception: Throwable
    ): ResponseEntity<ApiResponse<Unit>> {
        log.error { exception }
        return ResponseEntity(ApiResponse.error(exception), HttpStatus.INTERNAL_SERVER_ERROR)
    }
}