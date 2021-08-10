package dev.nevack.utils

import com.android.build.api.transform.JarInput
import dev.nevack.api.transform.BuildPolicy
import java.io.File

internal fun logBypassTransformation() {
    println("---------- AspectJ tasks bypassed with no outputs ----------")
}

internal fun logCompilationStart() {
    println("---------- Starting AspectJ sources compilation ----------")
}

internal fun logCompilationFinish() {
    println("---------- Finish AspectJ compiler ----------")
}

internal fun logAugmentationStart() {
    println("---------- Starting augmentation with AspectJ transformer ----------")
}

internal fun logAugmentationFinish() {
    println("---------- Finish AspectJ transformer ----------")
}

internal fun logNoAugmentation() {
    println("---------- Exit AspectJ transformer w/o processing ----------")
}

internal fun logEnvInvalid() {
    println("Ajc classpath doesn't has needed runtime environment")
}

internal fun logWeaverBuildPolicy(policy: BuildPolicy) {
    println("Weaving in ${policy.name.toLowerCase()} mode")
}

internal fun logIgnoreInpathJars() {
    println("Ignoring include/exclude option of -inpath parameter in simple mode.\n" +
            "Switch to `aspectj-ext` plugin to enable this behavior!")
}

internal fun logJarInpathAdded(jar: JarInput) {
    println("include jar :: ${jar.file.absolutePath}")
}

internal fun logJarInpathRemoved(jar: JarInput) {
    println("exclude jar :: ${jar.file.absolutePath}")
}

internal fun logJarAspectAdded(jar: JarInput) {
    println("include aspects from :: ${jar.file.absolutePath}")
}

internal fun logJarAspectAdded(file: File) {
    println("include aspects from :: ${file.absolutePath}")
}

internal fun logExtraAjcArgumentAlreadyExists(arg: String) {
    println("extra AjC argument $arg already exists in build config")
}

internal fun logBuildParametersAdapted(args: MutableCollection<String>, logfile: String) {
    var dash = false
    val params = buildString {
        for (arg in args) when {
            arg.startsWith('-') -> {
                if (dash) appendLine()
                append(arg)
                dash = true
            }
            arg.contains(':') -> {
                appendLine()
                arg.split(':').forEach {
                    append("    ")
                    appendLine(it)
                }
                dash = false
            }
            else -> {
                dash = false
                append(' ')
                appendLine(arg)
            }
        }
    }

    println("Ajc config:\n$params")
    println("Detailed log in $logfile")
}
