dependencies {
    // kotlin dto serialization/deserialization
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // spring
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    implementation(project(":config:jasypt-config"))
    implementation(project(":config:kafka-config"))

    testImplementation(testFixtures(project(":config:kafka-config")))
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
