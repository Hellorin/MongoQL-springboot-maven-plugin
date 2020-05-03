package io.github.hellorin.mongoql.springboot.templating.freemarker

import io.github.hellorin.mongoql.Type
import io.github.hellorin.mongoql.springboot.configuration.KotlinLanguage
import io.github.hellorin.mongoql.springboot.configuration.Language
import java.io.File

class RepositoryGenerator(
        private val language: Language = KotlinLanguage,
        private val folder: String = listOf(".", "target", "generated-sources", "src", "main").joinToString(separator = File.separator)) : MongoQLFreemarkerFileGenerator {
    override fun getTemplateFilename(): String = "repository.${language.name()}.ftl"

    fun generate(packageName: String, types: List<Type>) {
        val rootType = types.filter { it.isMainType() }[0]

        val inputData: Map<String, Any> = mapOf(
                "packageName" to packageName,
                "rootType" to rootType.typeName
        );

        processTemplate(
                baseFolder = listOf(folder, language.folderName(), packageName.replace(".", File.separator)).joinToString(separator = File.separator),
                generatedFilename = "Repository.${language.extension()}",
                inputData = inputData,
                types = types
        )
    }
}