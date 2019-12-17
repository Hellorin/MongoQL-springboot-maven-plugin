package ch.hellorin.mongoql.springboot.templating

import com.hellorin.mongoql.Type
import java.io.File
import java.io.FileWriter

class TypesSDLGenerator(
        private val folder : String = listOf(".", "generated-resources", "graphql").joinToString(File.separator)
) : MongoQLFileGenerator {
    override fun processToFile(types: List<Type>, inputData: Map<String, Any>?, fileWriter: FileWriter) {
        fileWriter.write(types.joinToString(separator = "\n\n") { it.toString() })
        fileWriter.flush()
    }

    fun generate(types : List<Type>) {
        File(folder).mkdirs()

        File(listOf(folder, "types.graphqls").joinToString (File.separator)).createNewFile()
        FileWriter(listOf(folder, "types.graphqls").joinToString (File.separator)).use { out ->
            processToFile(types, mapOf(), out)
        }
    }
}