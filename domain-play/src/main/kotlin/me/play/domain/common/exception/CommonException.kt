package me.play.domain.common.exception

import me.play.domain.common.response.ResponseCode

class CommonException(
    val responseCode: ResponseCode,
    override val cause: Throwable? = null,
    val customMessage: String? = responseCode.message,
    val data: Any? = null
) : RuntimeException(customMessage ?: responseCode.message, cause)