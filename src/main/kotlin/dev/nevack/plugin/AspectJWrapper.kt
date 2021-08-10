package dev.nevack.plugin

import com.android.build.gradle.AppExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.TestedExtension
import dev.nevack.AndroidConfig
import dev.nevack.AspectJExtension
import dev.nevack.api.transform.*
import dev.nevack.utils.LANG_AJ
import dev.nevack.plugin.configProject
import org.gradle.api.Plugin
import org.gradle.api.Project
import javax.inject.Inject

sealed class AspectJWrapper(private val scope: ConfigScope): Plugin<Project> {

    class DryRun @Inject constructor(): AspectJWrapper(ConfigScope.STANDARD) {
        override fun getTransformer(project: Project): AspectJTransform = StandardTransformer(project)
    }

    class Standard @Inject constructor(): AspectJWrapper(ConfigScope.STANDARD) {
        override fun getTransformer(project: Project): AspectJTransform = StandardTransformer(project)
    }

    class Provides @Inject constructor(): AspectJWrapper(ConfigScope.PROVIDE) {
        override fun getTransformer(project: Project): AspectJTransform = ProvidesTransformer(project)
    }

    class Extended @Inject constructor(): AspectJWrapper(ConfigScope.EXTEND) {
        override fun getTransformer(project: Project): AspectJTransform = ExtendedTransformer(project)
    }

    class Test @Inject constructor(): AspectJWrapper(ConfigScope.JUNIT) {
        override fun getTransformer(project: Project): AspectJTransform = TestsTransformer(project)
    }

    private val noTransformsScopes = arrayOf(
            ConfigScope.PROVIDE,
            ConfigScope.JUNIT
    )

    override fun apply(project: Project) {
        val config = AndroidConfig(project, scope)
        val settings = project.extensions.create(LANG_AJ, AspectJExtension::class.java).apply {
            dryRun = this@AspectJWrapper is DryRun
        }

        configProject(project, config, settings)

        val module: TestedExtension
        val transformer: AspectJTransform
        if (config.isLibraryPlugin) {
            transformer = LibraryTransformer(project)
            module = project.extensions.getByType(LibraryExtension::class.java)
        } else {
            transformer = getTransformer(project)
            module = project.extensions.getByType(AppExtension::class.java)
        }

        if (this is DryRun) {
            return
        }

        if (scope in noTransformsScopes) {
            return
        }

        transformer.withConfig(config).prepareProject()
        module.registerTransform(transformer)
    }

    internal abstract fun getTransformer(project: Project): AspectJTransform
}