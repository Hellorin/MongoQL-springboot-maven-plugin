package ch.hellorin.mongoql.springboot.templating.freemarker

import com.hellorin.mongoql.Type
import java.io.File

object QueriesSDLGenerator : MongoQLFreemarkerFileGenerator {
    override fun getTemplateFilename() = "queries.ftl"

    fun generate(types: List<Type>) {
        val rootDocumentType = types.filter { it.isMainType() }

        val inputData: Map<String, Any> = mapOf(
                "rootType" to rootDocumentType[0].typeName
        )

        processTemplate(
                baseFolder = listOf(".", "generated-resources", "graphql").joinToString(separator = File.separator),
                generatedFilename = "query.graphqls",
                inputData = inputData,
                types = types
        )
    }
}