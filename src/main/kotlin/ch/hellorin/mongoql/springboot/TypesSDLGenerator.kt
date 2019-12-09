package ch.hellorin.mongoql.springboot

import com.hellorin.mongoql.Type
import java.io.File
import java.io.FileWriter

object TypesSDLGenerator {
    fun generate(types : List<Type>) {
        val folder = listOf(".", "generated-resources", "graphql").joinToString(File.separator)

        File(folder).mkdirs()

        File(listOf(folder, "types.graphqls").joinToString (File.separator)).createNewFile()
        FileWriter(listOf(folder, "types.graphqls").joinToString (File.separator)).use { out ->
            out.write(types.joinToString(separator = "\n\n") { it.toString() })
            out.flush()
        }
    }
}