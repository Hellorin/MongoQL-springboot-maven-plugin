package io.github.hellorin.mongoql.springboot.templating.freemarker

import io.github.hellorin.mongoql.Type
import io.github.hellorin.mongoql.db.MongoDBParams
import java.io.File

class ApplicationConfigurationGenerator(
        private val folder : String = listOf(".", "target", "generated-resources").joinToString(File.separator)
) : MongoQLFreemarkerFileGenerator {
    override fun getTemplateFilename() = "applicationYaml.ftl"

    @Suppress("UNCHECKED_CAST")
    fun generate(types: List<Type>, mongoDBParams: MongoDBParams) {
        val inputData = mutableMapOf(
                "databaseName" to mongoDBParams.dbName,
                "useURI" to mongoDBParams.useURI,
                "clusterHost" to mongoDBParams.clusterHost
        )
        inputData.apply { mongoDBParams.host?.let { put("singleHost", "host=$it") }}
        inputData.apply { mongoDBParams.port?.let { put("portConfig", "port=$it") }}
        inputData.apply { mongoDBParams.username?.let { put("username", it) }}
        inputData.apply { mongoDBParams.password?.let { put("password", it) }}
        inputData.apply { mongoDBParams.authenticationDatabase?.let { put("authenticationDatabaseConfig", "authentication-database: $it") }}
        inputData.apply { mongoDBParams.authenticationMechanism?.let { put("authenticationMechanismConfig", "authentication-mechanism: $it") }}

        processTemplate(
                baseFolder = folder,
                generatedFilename = "application.yml",
                inputData = inputData.toMap() as Map<String, Any>,
                types = types
        )
    }
}