package io.github.hellorin.mongoql.springboot

import io.github.hellorin.mongoql.MongoQLSchemaGenerator
import io.github.hellorin.mongoql.db.MongoDBParams
import io.github.hellorin.mongoql.graphql.GraphQLParams
import io.github.hellorin.mongoql.springboot.configuration.Language
import io.github.hellorin.mongoql.springboot.configuration.GenerationConfiguration
import io.github.hellorin.mongoql.springboot.configuration.JavaLanguage
import io.github.hellorin.mongoql.springboot.configuration.KotlinLanguage
import io.github.hellorin.mongoql.springboot.templating.TypesSDLGenerator
import io.github.hellorin.mongoql.springboot.templating.freemarker.*
import org.apache.maven.plugin.AbstractMojo
import org.apache.maven.plugins.annotations.Mojo
import org.apache.maven.plugins.annotations.Parameter
import java.io.File

@Mojo(name = "generateMongoqlClasses")
class SpringBootClassesGenerator(val mongoQLSchemaGenerator: MongoQLSchemaGenerator = MongoQLSchemaGenerator()) : AbstractMojo() {

    @Parameter(property = "generateMongoqlClasses.skip", required = false)
    var skip: Boolean = false

    @Parameter(property = "generateMongoqlClasses.package", required = true)
    lateinit var packageName: String

    @Parameter(property = "generateMongoqlClasses.databaseName", required = true)
    lateinit var databaseName: String

    @Parameter(property = "generateMongoqlClasses.collectionName", required = true)
    lateinit var collectionName: String

    @Parameter(property = "generateMongoqlClasses.rootTypeName", required = true)
    lateinit var rootTypeName: String

    @Parameter(property = "generateMongoqlClasses.username", required = false)
    var username: String? = null

    @Parameter(property = "generateMongoqlClasses.password", required = false)
    var password: String? = null

    @Parameter(property = "generateMongoqlClasses.host", required = false)
    var host: String? = null

    @Parameter(property = "generateMongoqlClasses.useURI", required = false)
    var useURI: Boolean = false

    @Parameter(property = "generateMongoqlClasses.clusterUost", required = false)
    var clusterHost: String? = null

    @Parameter(property = "generateMongoqlClasses.port", required = false)
    var port: Long? = null

    @Parameter(property = "generateMongoqlClasses.isUsingTLS", required = false)
    var isUsingTLS: Boolean = false

    @Parameter(property = "generateMongoqlClasses.authenticationDatabase", required = false)
    var authenticationDatabase: String? = null

    @Parameter(property = "generateMongoqlClasses.authenticationMechanism", required = false)
    var authenticationMechanism: String? = null

    @Parameter(property = "generateMongoqlClasses.language", required = true)
    var language: String = KotlinLanguage.name()

    override fun execute() {
        if (!skip) {
            val generationConfiguration = GenerationConfiguration.Builder().language(language).build()

            cleanupEnv(generationConfiguration.language)

            val builder = MongoDBParams.Builder(databaseName, collectionName)
                    .isUsingTLS(
                            isUsingTLS
                    ).useURI(
                            useURI
                    )

            builder.apply { username?.let { username(username!!) } }
            builder.apply { password?.let { password(password!!) } }
            builder.apply { host?.let { host(host!!) } }
            builder.apply { clusterHost?.let { clusterHost(clusterHost!!) } }
            builder.apply { port?.let { port(port!!) } }
            builder.apply { authenticationDatabase?.let { authenticationDatabase(authenticationDatabase!!) } }
            builder.apply { authenticationMechanism?.let { authenticationMechanism(authenticationMechanism!!) } }

            val mongoDbParams = builder
                    .build()
            log.info(mongoDbParams.toString())

            val graphqlParams = GraphQLParams.Builder(rootTypeName = rootTypeName).build()

            val graphQLTypes = mongoQLSchemaGenerator.generate(mongoDbParams, graphqlParams)

            ModelsGenerator(language = generationConfiguration.language).generate(packageName, graphQLTypes, mongoDbParams)
            RepositoryGenerator(language = generationConfiguration.language).generate(packageName, graphQLTypes)
            QueryResolverGenerator(language = generationConfiguration.language).generate(packageName, graphQLTypes)
            ConfigurationGenerator(language = generationConfiguration.language).generate(packageName, graphQLTypes)
            TypesSDLGenerator().generate(graphQLTypes)
            QueriesSDLGenerator().generate(graphQLTypes)
            ApplicationConfigurationGenerator().generate(graphQLTypes, mongoDbParams)
        } else {
            log.info("Generation skipped. Should be only used when no integration tests are ran")
        }
    }

    fun cleanupEnv(language: Language) {
        when(language) {
            is KotlinLanguage -> {
                File("./target/generated-sources/src/main/kotlin").deleteRecursively()
                File("./target/generated-sources/src/main/kotlin").mkdirs()
            }
            is JavaLanguage -> {
                File("./target/generated-sources/src/main/java").deleteRecursively()
                File("./target/generated-sources/src/main/java").mkdirs()
            }
        }
    }

}