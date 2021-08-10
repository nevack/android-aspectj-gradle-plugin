buildscript {
    repositories {
        google()
        mavenCentral()
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.wrapper {
    version = "7.0.2"
    distributionType = Wrapper.DistributionType.ALL
}
