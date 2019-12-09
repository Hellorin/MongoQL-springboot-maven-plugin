package ch.hellorin.mongoql.springboot.templating.freemarker

import ch.hellorin.mongoql.springboot.templating.TypesSDLGenerator
import com.hellorin.mongoql.Type
import java.io.File
import java.io.FileWriter

object QueriesSDLGenerator : MongoQLFreemarkerFileGenerator {
    override fun getTemplateFilename() = "queries.ftl"

    fun generate(types: List<Type>) {
        val rootDocumentType = types.filter { it.isMainType() }

        val inputData: Map<String, Any> = mapOf(
                "rootType" to rootDocumentType[0].typeName
        )

        val folder = listOf(".", "generated-resources", "graphql").joinToString(File.separator)
        File(folder).mkdirs()

        File(listOf(folder, "query.graphqls").joinToString (File.separator)).createNewFile()
        FileWriter(listOf(folder, "query.graphqls").joinToString (File.separator)).use { out ->
            processToFile(types, inputData, out)
        }
    }
}