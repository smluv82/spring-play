import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    kotlin("kapt") version "1.9.25"
    kotlin("plugin.jpa") version "1.9.25"
    id("org.springframework.boot") version "3.5.7"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "me.play"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}


subprojects {
    apply(plugin = "java")
    apply(plugin = "idea")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.jetbrains.kotlin.plugin.jpa")
    apply(plugin = "kotlin-kapt")

    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(21)
        }
    }

    repositories {
        mavenCentral()
        // ksp
        maven("https://oss.sonatype.org/content/repositories/snapshots")
    }

    val swaggerVersion by extra("2.6.0")
    val mapstructVersion by extra("1.6.3")
    val springCloudAwsVersion by extra("3.2.0")
    val awsVersion by extra("2.28.19")
//    val sentryVersion by extra("7.15.0")
    val mysqlConnectorVersion by extra("9.0.0")
    val kotlinLoggingVersion by extra("3.0.5")
    val commonsCollectionVersion by extra("4.4")
    val commonsIoVersion by extra("2.17.0")
    val micrometerVersion by extra("1.4.5")
    val poiVersion by extra("5.3.0")
    val easyExcelVersion by extra("4.0.3")
    val slackClientVersion by extra("1.43.1")
//    val kotlinJdslVersion by extra("3.5.2")
    val httpclientVersion by extra("5.4.3")

    dependencies {
        // BOM
        implementation(platform("io.awspring.cloud:spring-cloud-aws-dependencies:${springCloudAwsVersion}"))
        implementation(platform("software.amazon.awssdk:bom:${awsVersion}"))

        // spring
        implementation("org.springframework.boot:spring-boot-starter")
        implementation("org.springframework.boot:spring-boot-starter-actuator")
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-validation")
        implementation("org.springframework.boot:spring-boot-starter-aop")
        implementation("org.springframework.boot:spring-boot-starter-data-redis")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
//        implementation("org.springframework.boot:spring-boot-starter-security")
//        implementation("org.springframework.security:spring-security-test")
//        implementation("org.springframework.session:spring-session-data-redis")
        implementation("org.springframework.cloud:spring-cloud-config-client")

        // kotlin
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")

        // kotlin-corutine
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")

        // swagger
        implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${swaggerVersion}")

        // commons
        implementation("org.apache.commons:commons-lang3")
        implementation("org.apache.commons:commons-collections4:${commonsCollectionVersion}")
        implementation("commons-io:commons-io:${commonsIoVersion}")
        implementation("commons-validator:commons-validator:1.9.0")


        // mapStruct
        kapt("org.mapstruct:mapstruct-processor:${mapstructVersion}")
        implementation("org.mapstruct:mapstruct:${mapstructVersion}")

        // aws
//        implementation("io.awspring.cloud:spring-cloud-aws-starter")
//        implementation("io.awspring.cloud:spring-cloud-aws-starter-secrets-manager")
//
//        implementation("software.amazon.awssdk:auth")
//        implementation("software.amazon.awssdk:sso")
//        implementation("software.amazon.awssdk:ssooidc")
//        implementation("software.amazon.awssdk:s3")
//        implementation("software.amazon.awssdk:secretsmanager")
//        implementation("software.amazon.awssdk:ses")

        // sentry
//        implementation("io.sentry:sentry-spring-boot-starter-jakarta:${sentryVersion}")
//        implementation("io.sentry:sentry-logback:${sentryVersion}")

        // db
        implementation("com.mysql:mysql-connector-j:${mysqlConnectorVersion}")

        // http client
        implementation("org.apache.httpcomponents.client5:httpclient5:${httpclientVersion}")

        // kotlin jdsl (https://kotlin-jdsl.gitbook.io/docs/jpql-with-kotlin-jdsl, https://engineering.linecorp.com/ko/blog/kotlinjdsl-jpa-criteria-api-with-kotlin)
//        implementation("com.linecorp.kotlin-jdsl:jpql-dsl:${kotlinJdslVersion}")
//        implementation("com.linecorp.kotlin-jdsl:jpql-render:${kotlinJdslVersion}")
//        implementation("com.linecorp.kotlin-jdsl:spring-data-jpa-support:${kotlinJdslVersion}")

        // poi
        implementation("org.apache.poi:poi:$poiVersion")
        implementation("org.apache.poi:poi-ooxml:$poiVersion")

        //EasyExcel
        implementation("com.alibaba:easyexcel:${easyExcelVersion}")

        // slack
        implementation("com.slack.api:slack-api-client:${slackClientVersion}")

        //logging
        implementation("io.github.microutils:kotlin-logging-jvm:${kotlinLoggingVersion}")
        implementation("io.micrometer:micrometer-tracing-bridge-brave:${micrometerVersion}")

        // test
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
        testImplementation("io.kotest:kotest-runner-junit5-jvm:5.9.1")
        testImplementation("io.kotest:kotest-assertions-core-jvm:5.9.1")
        testImplementation("io.kotest:kotest-property-jvm:5.9.1")
        testImplementation("io.mockk:mockk:1.13.17")
    }

    kapt {
        keepJavacAnnotationProcessors = true
        arguments {
            arg("mapstruct.defaultComponentModel", "spring")
            arg("mapstruct.defaultInjectionStrategy", "constructor")
        }
    }

    extra["springCloudVersion"] = "2025.0.0"

    dependencyManagement {
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
        }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            freeCompilerArgs += listOf("-Xopt-in=kotlin.RequiresOptIn")
            jvmTarget = "21"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    tasks.register("prepareKotlinBuildScriptModel") {}
}


project(":domain-play") {

}

project(":kotlin-api") {

    dependencies {
        implementation(project(":domain-play"))
    }

}
