plugins {
    war
    id("fish.payara.micro-gradle-plugin") version "1.1.0"
}

dependencies {
    implementation("com.google.code.gson:gson:2.8.8")
    implementation("org.glassfish.jersey.core:jersey-client:3.0.3")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.0")
    implementation(project(":common"))
}

payaraMicro {
    commandLineOptions = mapOf("port" to 666, "contextRoot" to "/")
//    isUseUberJar = true
}