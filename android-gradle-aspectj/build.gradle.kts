plugins {
    `kotlin-dsl`
    `maven-publish`
}

group = extra["aspectjPluginGroup"] as String
version = extra["aspectjPluginVersion"] as String

gradlePlugin {
    (plugins) {
        register("com.archinamon.aspectj") {
            id = "com.archinamon.aspectj"
            implementationClass = "com.archinamon.plugin.AspectJWrapper\$Standard"
        }

        register("com.archinamon.aspectj-dryRun") {
            id = "com.archinamon.aspectj-dryRun"
            implementationClass = "com.archinamon.plugin.AspectJWrapper\$DryRun"
        }

        register("com.archinamon.aspectj-ext") {
            id = "com.archinamon.aspectj-ext"
            implementationClass = "com.archinamon.plugin.AspectJWrapper\$Extended"
        }

        register("com.archinamon.aspectj-provides") {
            id = "com.archinamon.aspectj-provides"
            implementationClass = "com.archinamon.plugin.AspectJWrapper\$Provides"
        }

        register("com.archinamon.aspectj-junit") {
            id = "com.archinamon.aspectj-junit"
            implementationClass = "com.archinamon.plugin.AspectJWrapper\$Test"
        }
    }
}

val androidGradleVersion: String by extra
val aspectjVersion: String by extra

dependencies {
    compileOnly("com.android.tools.build:gradle:$androidGradleVersion")
    implementation("org.aspectj:aspectjrt:$aspectjVersion")
    implementation("org.aspectj:aspectjtools:$aspectjVersion")

    testImplementation(gradleTestKit())
    testImplementation("junit:junit:4.12")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.0.0-M3")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine:4.12.0-M1")
    testImplementation(kotlin("test-junit"))
}
