package ch.hellorin.mongoql.springboot

import com.hellorin.mongoql.Type
import java.io.File

object RepositoryGenerator : MongoQLFileGenerator {
    override fun getTemplateFilename() = "repository.ftl"

    fun generate(packageName: String, types: List<Type>) {
        val rootType = types.filter { it.isMainType() }[0]

        val inputData: Map<String, Any> = mapOf(
                "packageName" to packageName,
                "rootType" to rootType.typeName
        );

        processTemplate(
                baseFolder = listOf(".", "generated-sources", "src", "main", "kotlin", packageName.replace(".", File.separator)).joinToString(separator = File.separator),
                generatedFilename = "Repository.kt",
                inputData = inputData
        )
    }
}