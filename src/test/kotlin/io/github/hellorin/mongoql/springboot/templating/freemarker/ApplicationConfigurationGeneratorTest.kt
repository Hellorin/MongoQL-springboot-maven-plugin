package io.github.hellorin.mongoql.springboot.templating.freemarker

import io.github.hellorin.mongoql.Attribute
import io.github.hellorin.mongoql.Type
import io.github.hellorin.mongoql.db.MongoDBParams
import io.github.hellorin.mongoql.utils.cleanStringForAssertion
import org.junit.Assert
import org.junit.Test
import java.io.File
import java.util.stream.Collectors


internal class ApplicationConfigurationGeneratorTest {
    @Test
    fun `generate application yaml`() {
        // Given
        val filePath = listOf(".", "target", "tests", "testFolder", "graphql").joinToString(separator = File.separator)
        val attributes1 = listOf(
                Attribute("name", setOf("String"), false),
                Attribute("age", setOf("Int"), true),
                Attribute("bankAccount", setOf("Money"), true)
        )
        val type1 = Type("Person", attributes1)

        val attributes2 = listOf(
                Attribute("debit", setOf("Int"), false),
                Attribute("credit", setOf("Int"), false)
        )
        val type2 = Type("Money", attributes2)

        val types = listOf(
                type1,
                type2
        )

        val mongoDBParams = MongoDBParams.Builder(
                dbName = "db",
                colName = "colName"
        ).build()

        // When
        ApplicationConfigurationGenerator(filePath).generate(types, mongoDBParams)

        // Then
        val expectedFileContent = File(listOf(".", "src", "test", "resources", "test-resources", "application.yml").joinToString(File.separator)).bufferedReader().lines().collect(Collectors.joining("\n"))
        val fileContent = File(listOf(filePath, "application.yml").joinToString(File.separator)).bufferedReader().lines().collect(Collectors.joining("\n"))

        Assert.assertEquals(cleanStringForAssertion(expectedFileContent), cleanStringForAssertion(fileContent))
    }

}