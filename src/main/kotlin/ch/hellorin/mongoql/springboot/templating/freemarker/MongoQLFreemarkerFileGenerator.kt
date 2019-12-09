package ch.hellorin.mongoql.springboot.templating.freemarker

import ch.hellorin.mongoql.springboot.templating.MongoQLFileGenerator
import ch.hellorin.mongoql.springboot.templating.freemarker.configuration.FreemarkerConfiguration
import com.hellorin.mongoql.Type
import freemarker.template.Template
import java.io.FileWriter

interface MongoQLFreemarkerFileGenerator : MongoQLFileGenerator {
    fun getTemplateFilename(): String

    fun getTemplate(): Template = FreemarkerConfiguration.getTemplate(getTemplateFilename())

    override fun processToFile(types: List<Type>, inputData: Map<String, Any>?, fileWriter: FileWriter) {
        getTemplate().process(inputData, fileWriter)
        fileWriter.flush()
    }
}