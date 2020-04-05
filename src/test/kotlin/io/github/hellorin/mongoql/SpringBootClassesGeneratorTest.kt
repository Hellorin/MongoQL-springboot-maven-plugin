package io.github.hellorin.mongoql

import io.github.hellorin.mongoql.db.MongoDBParams
import io.github.hellorin.mongoql.graphql.GraphQLParams
import io.github.hellorin.mongoql.springboot.SpringBootClassesGenerator
import org.junit.After
import org.junit.Test
import java.io.File

class SpringBootClassesGeneratorTest {
    @Test
    fun `smoke test`() {
        val springBootClassesGenerator = SpringBootClassesGenerator(MockMongoQLSchemaGenerator())
        springBootClassesGenerator.databaseName = "myDatabase"
        springBootClassesGenerator.collectionName = "myCollection"
        springBootClassesGenerator.packageName = "ch.hellorin.mongoql.springboot"
        springBootClassesGenerator.rootTypeName = "Person"
        springBootClassesGenerator.clusterHost = "clusterhost"
        springBootClassesGenerator.authenticationDatabase = "admin"
        springBootClassesGenerator.authenticationMechanism = "SCRAM-SHA-1"
        springBootClassesGenerator.port = 27017L
        springBootClassesGenerator.isUsingTLS = true
        springBootClassesGenerator.execute()
    }

    @After
    fun cleanup() {
        File("./target/generated-resources").deleteRecursively()
        File("./target/generated-sources").deleteRecursively()
    }
}

private class MockMongoQLSchemaGenerator() : MongoQLSchemaGenerator() {

    override fun generate(mongoDBParams: MongoDBParams, graphQLParams: GraphQLParams): List<Type> {
        return listOf(
                Type("Person",
                        listOf(
                                Attribute("_id", setOf("ObjectID"), false),
                                Attribute("name", setOf("String"), false)
                        )
                )
        )
    }
}