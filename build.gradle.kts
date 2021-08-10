plugins {
    `kotlin-dsl`
    `maven-publish`
}

repositories {
    google()
    mavenCentral()
}

group = extra["aspectjPluginGroup"] as String
version = extra["aspectjPluginVersion"] as String

gradlePlugin {
    plugins {
        create("dev.nevack.aspectj") {
            id = "dev.nevack.aspectj"
            implementationClass = "dev.nevack.plugin.AspectJWrapper\$Standard"
        }

        create("dev.nevack.aspectj-dryRun") {
            id = "dev.nevack.aspectj-dryRun"
            implementationClass = "dev.nevack.plugin.AspectJWrapper\$DryRun"
        }

        create("dev.nevack.aspectj-ext") {
            id = "dev.nevack.aspectj-ext"
            implementationClass = "dev.nevack.plugin.AspectJWrapper\$Extended"
        }

        create("dev.nevack.aspectj-provides") {
            id = "dev.nevack.aspectj-provides"
            implementationClass = "dev.nevack.plugin.AspectJWrapper\$Provides"
        }

        create("dev.nevack.aspectj-junit") {
            id = "dev.nevack.aspectj-junit"
            implementationClass = "dev.nevack.plugin.AspectJWrapper\$Test"
        }
    }
}

val androidGradleVersion: String by extra
val aspectjVersion: String by extra

dependencies {
    implementation("com.android.tools.build:gradle:$androidGradleVersion")
    implementation("org.aspectj:aspectjrt:$aspectjVersion")
    implementation("org.aspectj:aspectjtools:$aspectjVersion")
}

tasks.wrapper {
    version = "7.0.2"
    distributionType = Wrapper.DistributionType.ALL
}
