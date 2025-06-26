package me.play.domain.utils

import org.mapstruct.Named

object MapperHelper {
    fun <T, R> mapList(sources: List<T>, mapper: (T) -> (R)): List<R> {
        return sources.map { mapper(it) }
    }


    @JvmStatic
    @Named("trimString")
    fun trimString(source: String) = source.trim()

    @JvmStatic
    @Named("trimAndUpperCase")
    fun trimAndUpperCase(source: String) = source.trim().uppercase()
}