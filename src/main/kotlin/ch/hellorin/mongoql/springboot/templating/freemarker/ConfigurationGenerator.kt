package ch.hellorin.mongoql.springboot.templating.freemarker

import com.hellorin.mongoql.Type
import java.io.File

object ConfigurationGenerator : MongoQLFreemarkerFileGenerator {
    override fun getTemplateFilename() = "configuration.ftl"

    fun generate(packageName: String, types: List<Type>) {
        val inputData: Map<String, Any> = mapOf(
                "packageName" to packageName
        )
        processTemplate(
                listOf(".", "generated-sources", "src", "main", "kotlin", packageName.replace(".", File.separator)).joinToString(separator = File.separator),
                "Configuration.kt",
                inputData,
                types
        )
    }
}