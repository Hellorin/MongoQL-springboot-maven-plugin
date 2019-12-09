package ch.hellorin.mongoql.springboot.templating

import com.hellorin.mongoql.Type
import java.io.File
import java.io.FileWriter

object TypesSDLGenerator : MongoQLFileGenerator {
    override fun processToFile(types: List<Type>, inputData: Map<String, Any>?, fileWriter: FileWriter) {
        fileWriter.write(types.joinToString(separator = "\n\n") { it.toString() })
        fileWriter.flush()
    }

    fun generate(types : List<Type>) {
        val folder = listOf(".", "generated-resources", "graphql").joinToString(File.separator)

        File(folder).mkdirs()

        File(listOf(folder, "types.graphqls").joinToString (File.separator)).createNewFile()
        FileWriter(listOf(folder, "types.graphqls").joinToString (File.separator)).use { out ->
            processToFile(types, mapOf(), out)
        }
    }
}