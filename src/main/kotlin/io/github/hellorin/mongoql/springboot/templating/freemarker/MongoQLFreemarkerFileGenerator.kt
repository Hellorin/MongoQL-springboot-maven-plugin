package io.github.hellorin.mongoql.springboot.templating.freemarker

import freemarker.template.Template
import io.github.hellorin.mongoql.Type
import io.github.hellorin.mongoql.springboot.templating.MongoQLFileGenerator
import io.github.hellorin.mongoql.springboot.templating.freemarker.configuration.FreemarkerConfiguration
import java.io.FileWriter

interface MongoQLFreemarkerFileGenerator : MongoQLFileGenerator {
    fun getTemplateFilename(): String

    fun getTemplate(): Template = FreemarkerConfiguration.getTemplate(getTemplateFilename())

    override fun processToFile(types: List<Type>, inputData: Map<String, Any>?, fileWriter: FileWriter) {
        getTemplate().process(inputData, fileWriter)
        fileWriter.flush()
    }
}