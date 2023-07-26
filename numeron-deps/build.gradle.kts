plugins {
    kotlin("jvm")
}

val miraiVersion = ext["miraiVersion"]

dependencies {
    api("net.mamoe:mirai-core-jvm:${miraiVersion}")
    api("net.mamoe:mirai-core-utils:${miraiVersion}")
    //runtimeOnly("net.mamoe:mirai-logging-log4j2:${miraiVersion}")
    //api("org.slf4j:slf4j-api:2.0.5")
    runtimeOnly("org.slf4j:slf4j-simple:2.0.5")
    runtimeOnly("org.xerial:sqlite-jdbc:3.40.1.0")
    api("com.google.code.gson:gson:2.10.1")
    api("org.apache.logging.log4j:log4j-api:2.20.0")
    runtimeOnly("org.apache.logging.log4j:log4j-core:2.20.0")
    //runtimeOnly("org.apache.logging.log4j:log4j-slf4j2-impl:2.20.0")
    api("com.squareup.okhttp3:okhttp:4.10.0")
    api("commons-io:commons-io:2.13.0")
    api("cglib:cglib:3.3.0")
}