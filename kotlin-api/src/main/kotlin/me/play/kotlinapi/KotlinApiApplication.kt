package me.play.kotlinapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.web.config.EnableSpringDataWebSupport

@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
@ComponentScan(
    basePackages = [
        "me.play.kotlinapi",
        "me.play.domain"
    ]
)
@SpringBootApplication
class KotlinApiApplication

fun main(args: Array<String>) {
    runApplication<KotlinApiApplication>(*args)
}
