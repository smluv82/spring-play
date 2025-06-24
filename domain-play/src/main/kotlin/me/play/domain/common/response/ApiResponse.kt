package me.play.domain.common.response

import me.play.domain.common.exception.CommonException
import org.apache.commons.lang3.exception.ExceptionUtils

data class ApiResponse<T>(
    private val responseCode: ResponseCode,
    val statusCode: Int = responseCode.code,
    val httpStatus: Int = responseCode.httpStatus.value(),
    val data: T? = null,
    val errorMessage: String? = null
) {
    companion object {
        fun success() = ApiResponse<Unit>(ResponseCode.SUCCESS)
        fun <T> success(data: T?) = ApiResponse(ResponseCode.SUCCESS, data = data)

        fun error(code: ResponseCode, exception: Throwable? = null) = ApiResponse<Unit>(
            responseCode = code,
            errorMessage = code.message ?: ExceptionUtils.getMessage(exception)
        )

        fun <T> error(exception: CommonException, data: T? = null): ApiResponse<T> {
            return ApiResponse(
                responseCode = exception.responseCode,
                errorMessage = exception.customMessage ?: exception.responseCode.message ?: exception.message,
                data = data,
            )
        }

        fun <T> error(exception: Throwable, data: T? = null): ApiResponse<T> {
            return ApiResponse(
                responseCode = ResponseCode.SERVER_ERROR,
                errorMessage = exception.message,
                data = data,
            )
        }
    }
}