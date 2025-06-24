package me.play.domain.common.response

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.BAD_REQUEST

enum class ResponseCode(
    val code: Int,
    val httpStatus: HttpStatus,
    val message: String? = null
) {
    // common
    SUCCESS(2000, HttpStatus.OK),
    INVALID_PARAMETER(4000, BAD_REQUEST, "invalid_parameters"),
    NOT_FOUND_ID(4001, HttpStatus.NOT_FOUND, "저장소(store)의 해당하는 데이터가 존재하지 않습니다."),
    UNAUTHORIZED(4010, HttpStatus.UNAUTHORIZED),
    FORBIDDEN(4030, HttpStatus.FORBIDDEN, "forbidden"),
    NOT_FOUND(4040, HttpStatus.NOT_FOUND),
    EXISTS_DATA(4060, HttpStatus.CONFLICT, "데이터가 존재합니다."),
    DUPLICATE_RESOURCE(4090, HttpStatus.CONFLICT, "중복 데이터 존재"),

    SERVER_ERROR(5000, HttpStatus.INTERNAL_SERVER_ERROR),
}