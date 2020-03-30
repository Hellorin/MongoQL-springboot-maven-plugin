package io.github.hellorin.mongoql.springboot.templating.freemarker

import io.github.hellorin.mongoql.Type
import java.io.File

class ConfigurationGenerator(
        private val folder: String = listOf(".", "target", "generated-sources", "src", "main", "kotlin").joinToString(File.separator)
) : MongoQLFreemarkerFileGenerator {
    override fun getTemplateFilename() = "configuration.ftl"

    fun generate(packageName: String, types: List<Type>) {
        val inputData: Map<String, Any> = mapOf(
                "packageName" to packageName
        )
        processTemplate(
                listOf(folder, packageName.replace(".", File.separator)).joinToString(separator = File.separator),
                "Configuration.kt",
                inputData,
                types
        )
    }
}