package ch.hellorin.mongoql.springboot.templating.freemarker

import com.hellorin.mongoql.Type
import com.hellorin.mongoql.db.MongoDBParams
import java.io.File
import java.io.FileWriter

class ModelsGenerator(
        private val folder : String = listOf(".", "generated-sources", "src", "main", "kotlin").joinToString(File.separator)
) : MongoQLFreemarkerFileGenerator {
    override fun getTemplateFilename(): String = "modelClasses.ftl"

    fun generate(
            packageName : String,
            graphQLTypes: List<Type>,
            mongoDbParams : MongoDBParams
    ) {
        val rootDocumentType = graphQLTypes.filter { it.isMainType() }
        val rootTypeAttributes =  rootDocumentType[0].attributes
        val otherTypes = graphQLTypes.filter { !it.isMainType() }

        val inputData : Map<String, Any> = mapOf(
                "packageName" to packageName,
                "collectionName" to mongoDbParams.colName,
                "rootType" to rootDocumentType[0].typeName,
                "attributes" to rootTypeAttributes,
                "types" to otherTypes
        );

        processTemplate(
                baseFolder = listOf(folder, packageName.replace(".", File.separator)).joinToString (File.separator),
                generatedFilename = "Models.kt",
                inputData = inputData,
                types = graphQLTypes
        )
    }
}