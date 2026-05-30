plugins {
    kotlin("jvm") version "2.3.20"
    kotlin("plugin.serialization") version "2.3.20"
    id("me.champeau.jmh") version "0.7.3"
}

jmh {
    fork = 0
    warmup = "2s"
    warmupIterations = 2
    timeOnIteration = "2s"
    zip64 = true
}

group = "com.alexeycode"
version = "0.0.4"

dependencies {
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.jackson.databind)
    implementation(libs.gson)
    testImplementation(libs.junit)
}