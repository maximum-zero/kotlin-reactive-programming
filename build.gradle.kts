plugins {
    kotlin("jvm") version "1.9.21"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.slf4j:slf4j-api:1.7.32")
    testImplementation("ch.qos.logback:logback-classic:1.2.3")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}