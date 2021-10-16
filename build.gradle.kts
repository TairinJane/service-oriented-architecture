import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.5.30"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    id("org.springframework.boot") version "2.4.1"
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
    kotlin("plugin.jpa") version kotlinVersion
    application
    war
}

group = "me.user"
version = "1.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven") }
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.2")
    implementation("javax:javaee-api:7.0")
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.hibernate.validator:hibernate-validator:7.0.0.Final")
    implementation("org.hibernate:hibernate-core:5.5.7.Final")
    implementation("com.google.code.gson:gson:2.8.8")
    implementation("org.postgresql:postgresql:42.2.16")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.5.5")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

val explodedWar by tasks.register<Copy>("explodedWar") {
    into("$buildDir/libs/exploded/soa-$version.war")
    with(tasks.war.get())
}