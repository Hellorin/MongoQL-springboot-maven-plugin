package io.github.hellorin.mongoql.springboot.templating.freemarker

import io.github.hellorin.mongoql.Type
import io.github.hellorin.mongoql.springboot.configuration.KotlinLanguage
import io.github.hellorin.mongoql.springboot.configuration.Language
import java.io.File

class ConfigurationGenerator(
        private val language : Language = KotlinLanguage,
        private val folder: String = listOf(".", "target", "generated-sources", "src", "main").joinToString(File.separator)
) : MongoQLFreemarkerFileGenerator {
    override fun getTemplateFilename(): String = "configuration.${language.name()}.ftl"

    fun generate(packageName: String, types: List<Type>) {
        val inputData: Map<String, Any> = mapOf(
                "packageName" to packageName
        )
        processTemplate(
                listOf(folder, language.folderName(), packageName.replace(".", File.separator)).joinToString(separator = File.separator),
                "GraphQLConfiguration.${language.extension()}",
                inputData,
                types
        )
    }
}