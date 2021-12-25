plugins {
    war
    id("org.jetbrains.kotlin.plugin.noarg") version "1.6.10"
}

dependencies {
    implementation("com.google.code.gson:gson:2.8.8")
    implementation("org.glassfish.jersey.core:jersey-client:3.0.3")
    implementation("javax:javaee-api:7.0")
    implementation("javax.ejb:javax.ejb-api:3.2.2")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.0")
    compileOnly("org.glassfish.jersey.containers:jersey-container-servlet:2.25.1")
    compileOnly("javax.ws.rs:javax.ws.rs-api:2.1.1")
    compileOnly("javax.servlet:javax.servlet-api:4.0.1")
    compileOnly("javax.xml.ws:jaxws-api:2.3.1")
    compileOnly("javax.jws:javax.jws-api:1.1")
    implementation(project(":common"))
    implementation(project(":ejb-interface"))
}

noArg {
    annotation("javax.xml.bind.annotation.XmlRootElement")
}