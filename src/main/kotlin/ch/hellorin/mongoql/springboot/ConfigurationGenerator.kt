package ch.hellorin.mongoql.springboot

import com.hellorin.mongoql.Type
import java.io.File
import java.io.FileWriter

object ConfigurationGenerator {
    private const val FREEMARKER_TEMPLATE_NAME = "configuration.ftl"

    fun generate(packageName: String, types : List<Type>) {
        val template = FreemarkerConfiguration.getTemplate(ConfigurationGenerator.FREEMARKER_TEMPLATE_NAME)

        val inputData : Map<String, Any> = mapOf(
                "packageName" to packageName
        )

        File("./generated-sources/src/main/kotlin/${packageName.replace(".", File.separator)}").mkdirs()
        FileWriter(File("./generated-sources/src/main/kotlin/${packageName.replace(".", File.separator)}${File.separator}Configuration.kt")).use { out ->
            template.process(inputData, out)
            out.flush()
        }
    }
}