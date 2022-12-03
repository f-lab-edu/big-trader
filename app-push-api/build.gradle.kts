dependencies {
    // kafka
    implementation("org.springframework.boot:spring-boot-starter-amqp")
    implementation("org.springframework.kafka:spring-kafka")

    // kotlin dto serialization/deserialization
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // spring
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    implementation(project(":commons:jasypt-config"))
    implementation(project(":commons:kafka-config"))

    // kafka test
    testImplementation("org.springframework.amqp:spring-rabbit-test")
    testImplementation("org.springframework.kafka:spring-kafka-test")
}

// java 파일 분리 후 제거
configure<SourceSetContainer> {
    named("main") {
        java.srcDir("src/main/kotlin")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

val snippetsDir by extra { file("build/generated-snippets") }
tasks {
    test {
        outputs.dir(snippetsDir)
    }

    asciidoctor {
        inputs.dir(snippetsDir)
        sourceDir("src/main/asciidoc")
        dependsOn(test)
    }
}

jib {
    from {
        image = "adoptopenjdk/openjdk11:alpine"
    }
    to {
        image = "big-trader-docker-registry.kr.ncr.ntruss.com/app-push-api"
    }
}
