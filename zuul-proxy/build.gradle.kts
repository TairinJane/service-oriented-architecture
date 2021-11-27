plugins {
    val kotlinVersion = "1.5.30"
    kotlin("plugin.spring") version kotlinVersion
    id("org.springframework.boot") version "2.3.7.RELEASE"
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
}

dependencies {
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-zuul:2.2.9.RELEASE")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client:2.2.9.RELEASE")
}