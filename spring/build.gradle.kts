plugins {
    id("org.springframework.boot") version "2.6.2"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("net.linguica.maven-settings") version "0.5"
    id("org.hibernate.build.maven-repo-auth") version "3.0.2"
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.spring") version "1.6.10"
    `maven-publish`
}

group = "org.datausagetracing.integration"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
    compileOnly("org.springframework.boot:spring-boot-starter-web")
    compileOnly("org.springframework.boot:spring-boot-starter-webflux")
    implementation("net.bytebuddy:byte-buddy-dep:1.12.7")
    implementation("net.bytebuddy:byte-buddy-agent:1.12.7")
    api(project(":common"))
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks {
    jar {
        archiveFileName.set("spring.jar")
    }
}

publishing {
    repositories {
        maven {
            name = "github"
            setUrl("https://maven.pkg.github.com/TUB-CNPE-TB/hawk-java-integration")
        }
    }
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}