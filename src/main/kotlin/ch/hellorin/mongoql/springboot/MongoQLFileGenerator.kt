package ch.hellorin.mongoql.springboot

import freemarker.template.Template
import java.io.File
import java.io.FileWriter

interface MongoQLFileGenerator {
    fun getTemplateFilename() : String

    fun getTemplate(): Template = FreemarkerConfiguration.getTemplate(getTemplateFilename())

    fun processTemplate(
            baseFolder: String,
            generatedFilename: String,
            inputData: Map<String, Any>) {
        val template = getTemplate()

        File(baseFolder).mkdirs()
        FileWriter(File(baseFolder + File.separator + generatedFilename)).use { out ->
            getTemplate().process(inputData, out)
            out.flush()
        }
    }
}