plugins {
    id("org.springframework.boot") version "2.7.4"
    id("io.spring.dependency-management") version "1.0.14.RELEASE"
}


dependencies {
    api("org.springframework.boot:spring-boot-starter-data-redis")

    testFixturesImplementation("it.ozimov:embedded-redis:0.7.2")
    testFixturesImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.bootJar {
    enabled = false
}
