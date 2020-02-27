package io.github.hellorin.mongoql.springboot.templating.freemarker

import io.github.hellorin.mongoql.Type
import java.io.File

class QueriesSDLGenerator(
        private val folder : String = listOf(".", "generated-resources", "graphql").joinToString(separator = File.separator)
) : MongoQLFreemarkerFileGenerator {
    override fun getTemplateFilename() = "queries.ftl"

    fun generate(types: List<Type>) {
        val rootDocumentType = types.filter { it.isMainType() }

        val inputData: Map<String, Any> = mapOf(
                "rootType" to rootDocumentType[0].typeName
        )

        processTemplate(
                baseFolder = folder,
                generatedFilename = "query.graphqls",
                inputData = inputData,
                types = types
        )
    }
}