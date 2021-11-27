plugins {
    val kotlinVersion = "1.5.30"
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

dependencies {
    implementation("org.hibernate.validator:hibernate-validator:7.0.0.Final")
    implementation("org.hibernate:hibernate-core:5.5.7.Final")
    implementation("javax:javaee-api:7.0")
}