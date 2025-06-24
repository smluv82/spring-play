import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
val bootJar: BootJar by tasks

group = "me.play"
version = "0.0.1-SNAPSHOT"

bootJar.enabled = true
jar.enabled = false

dependencies {
}