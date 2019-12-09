package ch.hellorin.mongoql.springboot

import com.hellorin.mongoql.Type
import java.io.File
import java.io.FileWriter

object TypesSDLGenerator {
    fun generate(types : List<Type>) {
        File("./generated-resources/graphql").mkdirs()
        File("./generated-resources/graphql/types.graphqls").createNewFile()
        FileWriter(File("./generated-resources/graphql/types.graphqls")).use { out ->
            out.write(types.joinToString(separator = "\n\n") { it.toString() })
            out.flush()
        }
    }
}