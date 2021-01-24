import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack

plugins {
    kotlin("multiplatform") version "1.4.21"
    kotlin("plugin.serialization") version "1.4.10"
    application
}

group = "de.geosearchef"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
    maven { url = uri("https://dl.bintray.com/kotlin/kotlin-js-wrappers") }
    maven { url = uri("https://dl.bintray.com/kotlin/kotlinx") }
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        testRuns["test"].executionTask.configure {
            useJUnit()
        }
        withJava()
    }
    js(LEGACY) {
        browser {
            binaries.executable()
            webpackTask {
                cssSupport.enabled = true
            }
            runTask {
                cssSupport.enabled = true
            }
            testTask {
                useKarma {
                    useChromeHeadless()
                    webpackConfig.cssSupport.enabled = true
                }
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation("com.sparkjava:spark-kotlin:1.0.0-alpha")
                implementation("org.slf4j:slf4j-simple:2.0.0-alpha1")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")
            }
        }
        val jsMain by getting {
            dependencies {
//                implementation("org.jetbrains:kotlin-react:16.13.1-pre.113-kotlin-1.4.0")
//                implementation("org.jetbrains:kotlin-react-dom:16.13.1-pre.113-kotlin-1.4.0")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")
            }
        }
//        val commonTest by getting {
//            dependencies {
//                implementation(kotlin("test-common"))
//                implementation(kotlin("test-annotations-common"))
//            }
//        }
//        val jvmTest by getting {
//            dependencies {
//                implementation(kotlin("test-junit"))
//            }
//        }
//        val jsTest by getting {
//            dependencies {
//                implementation(kotlin("test-js"))
//            }
//        }
    }
}

application {
    mainClassName = "CardSimulatorServerKt"
}

tasks.getByName<KotlinWebpack>("jsBrowserProductionWebpack") {
    outputFileName = "cards.js"
}

tasks.getByName<Jar>("jvmJar") {
    dependsOn(tasks.getByName("jsBrowserProductionWebpack"))
    val jsBrowserProductionWebpack = tasks.getByName<KotlinWebpack>("jsBrowserProductionWebpack")
    from(File(jsBrowserProductionWebpack.destinationDirectory, jsBrowserProductionWebpack.outputFileName))
//    into(project.file("static/"))
}

tasks.getByName<JavaExec>("run") {
    dependsOn(tasks.getByName<Jar>("jvmJar"))
    classpath(tasks.getByName<Jar>("jvmJar"))
}