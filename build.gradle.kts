plugins {
    kotlin("jvm") version "2.3.0"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("io.mockk:mockk:1.14.9")
}

kotlin {
    jvmToolchain(24)
}

application {
    mainClass.set("app.nahiluhmot.kc8.MainKt")
}

tasks.test {
    useJUnitPlatform()
}