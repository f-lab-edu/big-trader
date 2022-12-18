import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.4"
    id("io.spring.dependency-management") version "1.0.14.RELEASE"
    id("org.asciidoctor.convert") version "1.5.8"
    id("io.freefair.lombok") version "6.5.1"
    id("checkstyle")
    id("com.google.cloud.tools.jib") version "3.3.1"
    id("java-test-fixtures")

    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
}

java.sourceCompatibility = JavaVersion.VERSION_11

allprojects {
    group = "com.flab"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

val nonDependenciesProjects = listOf("commons", "jasypt-config", "kafka-config", "redis-config")

configure(subprojects.filter { it.name in nonDependenciesProjects }) {
    apply(plugin = "checkstyle")
    apply(plugin = "io.freefair.lombok")
    apply(plugin = "java-test-fixtures")

    apply(plugin = "org.jetbrains.kotlin.jvm")

    val naverCheckStyleDir = File("${rootDir}/config/checkstyle/naver-checkstyle-rules.xml")
    val naverSuppressionDir =
        hashMapOf<String, Any>("suppressionFile" to "${rootDir}/config/checkstyle/naver-checkstyle-suppressions.xml")

    checkstyle {
        maxWarnings = 0
        configFile = naverCheckStyleDir
        configProperties = naverSuppressionDir
        toolVersion = "10.3.1"
    }
}

configure(subprojects.filter { it.name !in nonDependenciesProjects }) {
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.asciidoctor.convert")
    apply(plugin = "checkstyle")
    apply(plugin = "io.freefair.lombok")
    apply(plugin = "com.google.cloud.tools.jib")
    apply(plugin = "java-test-fixtures")

    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
        testImplementation("io.rest-assured:rest-assured:4.5.1")
    }

    val naverCheckStyleDir = File("${rootDir}/checkstyle-config/checkstyle/naver-checkstyle-rules.xml")
    val naverSuppressionDir =
        hashMapOf<String, Any>("suppressionFile" to "${rootDir}/checkstyle-config/checkstyle/naver-checkstyle-suppressions.xml")

    checkstyle {
        maxWarnings = 0
        configFile = naverCheckStyleDir
        configProperties = naverSuppressionDir
        toolVersion = "10.3.1"
    }
}
