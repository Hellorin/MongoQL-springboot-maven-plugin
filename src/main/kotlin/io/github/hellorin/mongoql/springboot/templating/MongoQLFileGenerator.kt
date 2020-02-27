package io.github.hellorin.mongoql.springboot.templating

import io.github.hellorin.mongoql.Type
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