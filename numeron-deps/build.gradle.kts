plugins {
    kotlin("jvm")
}

dependencies {
    api("net.mamoe:mirai-core-jvm:2.15.0-M1")
    runtimeOnly("net.mamoe:mirai-logging-log4j2:2.15.0-M1")
//    api("org.slf4j:slf4j-api:2.0.5")
//    api("org.slf4j:slf4j-simple:2.0.5")
//    api("org.slf4j:slf4j-core:2.0.5")
    runtimeOnly("org.xerial:sqlite-jdbc:3.40.1.0")
    api("com.google.code.gson:gson:2.10.1")
    api("org.apache.logging.log4j:log4j-api:2.20.0")
    runtimeOnly("org.apache.logging.log4j:log4j-core:2.20.0")
    // https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-api
    api("org.junit.jupiter:junit-jupiter-api:5.9.2")
    api("com.squareup.okhttp3:okhttp:4.10.0")
}