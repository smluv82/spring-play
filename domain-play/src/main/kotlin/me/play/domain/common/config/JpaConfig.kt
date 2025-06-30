package me.play.domain.common.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.data.auditing.DateTimeProvider
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.time.Instant
import java.time.temporal.TemporalAccessor
import java.util.*

@Configuration
@EntityScan(basePackages = ["me.play.domain.entity"])
@EnableJpaRepositories(basePackages = ["me.play.domain.repository"])
@EnableTransactionManagement
@EnableJpaAuditing(
    auditorAwareRef = "auditorAware",
    dateTimeProviderRef = "dateTimeProvider"
)
class JpaConfig {

    @Bean
    fun auditorAware(): AuditorAware<String> = AuditorAwareImpl()


    @Bean
    @Lazy
    fun dateTimeProvider(): DateTimeProvider = DateTimeProviderImpl()
}



class AuditorAwareImpl : AuditorAware<String> {
    override fun getCurrentAuditor(): Optional<String> {
        //FIXME 인증 추가 후 인증 정보로 등록되도록 수정 필요함.
        return Optional.of("anonymous")
    }
}

class DateTimeProviderImpl : DateTimeProvider {
    override fun getNow(): Optional<TemporalAccessor> {
        return Optional.of(Instant.now())
    }
}