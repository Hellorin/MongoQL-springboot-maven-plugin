package io.github.hellorin.mongoql.springboot.templating.freemarker

import io.github.hellorin.mongoql.Type
import java.io.File

class RepositoryGenerator(private val folder: String = listOf(".", "generated-sources", "src", "main", "kotlin").joinToString(separator = File.separator)) : MongoQLFreemarkerFileGenerator {
    override fun getTemplateFilename() = "repository.ftl"

    fun generate(packageName: String, types: List<Type>) {
        val rootType = types.filter { it.isMainType() }[0]

        val inputData: Map<String, Any> = mapOf(
                "packageName" to packageName,
                "rootType" to rootType.typeName
        );

        processTemplate(
                baseFolder = listOf(folder, packageName.replace(".", File.separator)).joinToString(separator = File.separator),
                generatedFilename = "Repository.kt",
                inputData = inputData,
                types = types
        )
    }
}