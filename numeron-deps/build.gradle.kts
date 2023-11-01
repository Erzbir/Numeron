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
    runtimeOnly("org.xerial:sqlite-jdbc:3.42.0.0")
    api("com.google.code.gson:gson:2.10.1")
    api("org.apache.logging.log4j:log4j-api:2.20.0")
    runtimeOnly("org.apache.logging.log4j:log4j-core:2.20.0")
    //runtimeOnly("org.apache.logging.log4j:log4j-slf4j2-impl:2.20.0")
    api("com.squareup.okhttp3:okhttp:4.10.0")
    api("commons-io:commons-io:2.13.0")
    api("cglib:cglib:3.3.0")
    api("org.ow2.asm:asm:9.5")
    api("org.projectlombok:lombok:1.18.30")
   // api("cn.hutool:hutool-all:5.8.22")
  //  api("org.springframework.boot:spring-boot-starter-aop:3.1.4")
   // api("org.springframework.boot:spring-boot:3.1.4")
}