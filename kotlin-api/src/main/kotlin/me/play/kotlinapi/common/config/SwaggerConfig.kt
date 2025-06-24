package me.play.kotlinapi.common.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    @Bean
    fun kotlinApi(): OpenAPI {
        return OpenAPI().info(Info().title("Kotlin API").version("1.0.0"))
    }
}