package ch.hellorin.mongoql.springboot.templating.freemarker

import ch.hellorin.mongoql.utils.cleanStringForAssertion
import com.hellorin.mongoql.Attribute
import com.hellorin.mongoql.Type
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test
import java.io.File
import java.util.stream.Collectors

class RepositoryGeneratorTest {
    @Test
    fun `generate spring repository`() {
        // Given
        val packageName = "com.hellorin"
        val generationFilePath = listOf(".", "target", "tests", "testFolder", "graphql").joinToString(separator = File.separator)

        val attributes1 = listOf(
                Attribute("_id", setOf("String"), false),
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

        // When
        RepositoryGenerator(generationFilePath).generate(packageName, types)

        // Then
        val filepath = mutableListOf(generationFilePath)
        filepath.addAll(packageName.split("."))
        filepath.add("Repository.kt")

        Assert.assertTrue(File(filepath.joinToString(File.separator)).exists())

        val fileContent = File(filepath.joinToString(File.separator)).bufferedReader().lines().collect(Collectors.joining())
        val expectedFileContent = File(listOf(".", "src", "test", "resources", "test-resources", "repository.ktTest").joinToString(File.separator)).bufferedReader().lines().collect(Collectors.joining("\n"))

        Assert.assertEquals(cleanStringForAssertion(expectedFileContent), cleanStringForAssertion(fileContent))
    }
}