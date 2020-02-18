package ch.hellorin.mongoql.springboot.templating.freemarker

import ch.hellorin.mongoql.utils.cleanStringForAssertion
import com.hellorin.mongoql.Attribute
import com.hellorin.mongoql.Type
import org.junit.Assert
import org.junit.Test
import java.io.File
import java.util.stream.Collectors

internal class ConfigurationGeneratorTest {
    @Test
    fun `generate spring boot configuration class`() {
        // Given
        val filePath = listOf(".", "target", "tests", "testFolder", "graphql").joinToString(separator = File.separator)
        val packageName = "com.hellorin"

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

        // When
        ConfigurationGenerator(filePath).generate(packageName, types)

        // Then
        val list = mutableListOf(filePath)
        list.addAll(packageName.split("."))
        list.add("Configuration.kt")

        Assert.assertTrue(File(list.joinToString(File.separator)).exists())

        val fileContent = File(list.joinToString(File.separator)).bufferedReader().lines().collect(Collectors.joining())

        val expectedFilePath = mutableListOf(".", "src", "test", "resources", "test-resources", "configuration.ktTest")
        val expectedFileContent = File(expectedFilePath.joinToString(File.separator)).bufferedReader().lines().collect(Collectors.joining("\n"))

        Assert.assertEquals(cleanStringForAssertion(expectedFileContent), cleanStringForAssertion(fileContent))
    }
}