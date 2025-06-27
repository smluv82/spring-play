import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
val bootJar: BootJar by tasks
val queryDslVersion = "5.1.0"

group = "me.play"
version = "0.0.1-SNAPSHOT"

bootJar.enabled = false
jar.enabled = true

dependencies {

    // querydsl
    implementation("com.querydsl:querydsl-jpa:${queryDslVersion}:jakarta")
    implementation("com.querydsl:querydsl-core:${queryDslVersion}")
    kapt("com.querydsl:querydsl-apt:${queryDslVersion}:jakarta")

}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.Embeddable")
    annotation("jakarta.persistence.MappedSuperclass")
}

val querydslSrcDir = "src/main/generated"

tasks {
    clean {
        delete(files(querydslSrcDir))
    }
}
