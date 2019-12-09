package ch.hellorin.mongoql.springboot.templating

import com.hellorin.mongoql.Type
import java.io.File
import java.io.FileWriter

interface MongoQLFileGenerator {
    fun processToFile(types: List<Type>, inputData: Map<String, Any>?, fileWriter: FileWriter)

    fun processTemplate(
            baseFolder: String,
            generatedFilename: String,
            inputData: Map<String, Any>,
            types: List<Type>) {
        File(baseFolder).mkdirs()
        FileWriter(File(baseFolder + File.separator + generatedFilename)).use { out ->
            processToFile(types, inputData, out)
        }
    }
}