package io.github.hellorin.mongoql.springboot

import io.github.hellorin.mongoql.MongoQLSchemaGenerator
import io.github.hellorin.mongoql.db.MongoDBParams
import io.github.hellorin.mongoql.springboot.templating.TypesSDLGenerator
import io.github.hellorin.mongoql.graphql.GraphQLParams
import io.github.hellorin.mongoql.springboot.templating.freemarker.*
import org.apache.maven.plugin.AbstractMojo
import org.apache.maven.plugins.annotations.Mojo
import org.apache.maven.plugins.annotations.Parameter
import java.io.File

@Mojo(name = "generateMongoqlClasses")
class SpringBootClassesGenerator : AbstractMojo() {

    @Parameter(property = "generateMongoqlClasses.skip", required = false)
    var skip: Boolean = false

    @Parameter(property = "generateMongoqlClasses.package", required = true)
    lateinit var packageName: String

    @Parameter(property = "generateMongoqlClasses.databaseName", required = true)
    lateinit var databaseName: String

    @Parameter(property = "generateMongoqlClasses.collectionName", required = true)
    lateinit var collectionName: String

    override fun execute() {
        if (!skip) {
            File("./generated-sources/src/main/kotlin").deleteRecursively()
            File("./generated-sources/src/main/kotlin").mkdirs()

            val mongoDbParams = MongoDBParams.Builder(databaseName, collectionName).build()

            val graphqlParams = GraphQLParams.Builder(rootTypeName = "Person").build()

            val graphQLTypes = MongoQLSchemaGenerator().generate(mongoDbParams, graphqlParams)

            ModelsGenerator().generate(packageName, graphQLTypes, mongoDbParams)
            RepositoryGenerator().generate(packageName, graphQLTypes)
            QueryResolverGenerator().generate(packageName, graphQLTypes)
            ConfigurationGenerator().generate(packageName, graphQLTypes)
            TypesSDLGenerator().generate(graphQLTypes)
            QueriesSDLGenerator().generate(graphQLTypes)
            ApplicationConfigurationGenerator().generate(graphQLTypes, mongoDbParams)
        } else {
            log.info("Generation skipped. Should be only used when no integration tests are ran")
        }
    }

}

fun main() {
    val springBootClassesGenerator = SpringBootClassesGenerator()
    springBootClassesGenerator.databaseName = "myDatabase"
    springBootClassesGenerator.collectionName = "myCollection"
    springBootClassesGenerator.packageName = "ch.hellorin.mongoql.springboot"
    springBootClassesGenerator.execute()
}