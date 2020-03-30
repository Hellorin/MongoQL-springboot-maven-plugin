package io.github.hellorin.mongoql.springboot.templating.freemarker

import io.github.hellorin.mongoql.Type
import io.github.hellorin.mongoql.db.MongoDBParams
import java.io.File

class ApplicationConfigurationGenerator(
        private val folder : String = listOf(".", "target", "generated-resources").joinToString(File.separator)
) : MongoQLFreemarkerFileGenerator {
    override fun getTemplateFilename() = "applicationYaml.ftl"

    fun generate(types: List<Type>, mongoDBParams: MongoDBParams) {
        val inputData = mapOf(
                "databaseName" to mongoDBParams.dbName
        )

        processTemplate(
                baseFolder = folder,
                generatedFilename = "application.yml",
                inputData = inputData,
                types = types
        )
    }
}