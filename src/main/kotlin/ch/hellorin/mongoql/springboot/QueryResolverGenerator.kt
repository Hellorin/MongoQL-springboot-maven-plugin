package ch.hellorin.mongoql.springboot

import com.hellorin.mongoql.Type
import java.io.File
import java.io.FileWriter

object QueryResolverGenerator {
    private const val FREEMARKER_TEMPLATE_NAME = "queryResolver.ftl"

    fun generate(packageName: String, types: List<Type>) {
        val template = FreemarkerConfiguration.getTemplate(QueryResolverGenerator.FREEMARKER_TEMPLATE_NAME)

        val rootType = types.filter { it.isMainType() }[0]

        val inputData : Map<String, Any> = mapOf(
                "packageName" to packageName,
                "rootType" to rootType.typeName
        );

        File("./generated-sources/src/main/kotlin/${packageName.replace(".", File.separator)}").mkdirs()
        FileWriter(File("./generated-sources/src/main/kotlin/${packageName.replace(".", File.separator)}${File.separator}QueryResolver.kt")).use { out ->
            template.process(inputData, out)
            out.flush()
        }
    }
}