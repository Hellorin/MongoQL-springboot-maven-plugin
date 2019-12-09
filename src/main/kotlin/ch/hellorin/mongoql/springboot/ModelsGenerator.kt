package ch.hellorin.mongoql.springboot

import com.hellorin.mongoql.Type
import com.hellorin.mongoql.db.MongoDBParams
import java.io.File
import java.io.FileWriter
import java.io.StringWriter

object ModelsGenerator {
    private const val FREEMARKER_TEMPLATE_NAME = "modelClasses.ftl"

    fun generate(
            packageName : String,
            graphQLTypes: List<Type>,
            mongoDbParams : MongoDBParams
    ) {
        val template = FreemarkerConfiguration.getTemplate(FREEMARKER_TEMPLATE_NAME)

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

        File("./generated-sources/src/main/kotlin/${packageName.replace(".", File.separator)}").mkdirs()
        FileWriter(File("./generated-sources/src/main/kotlin/${packageName.replace(".", File.separator)}${File.separator}Models.kt")).use { out ->
            template.process(inputData, out)
            out.flush()
        }
    }
}