package io.github.hellorin.mongoql.springboot.templating.freemarker

import io.github.hellorin.mongoql.Attribute
import io.github.hellorin.mongoql.Type
import io.github.hellorin.mongoql.springboot.configuration.JavaLanguage
import io.github.hellorin.mongoql.springboot.configuration.KotlinLanguage
import io.github.hellorin.mongoql.springboot.configuration.Language
import io.github.hellorin.mongoql.utils.cleanStringForAssertion
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.io.File
import java.util.stream.Collectors


@RunWith(Parameterized::class)
internal class ConfigurationGeneratorTest(private val language: Language) {
    companion object {
        @JvmStatic
        @Parameterized.Parameters
        open fun data(): Collection<Array<Any?>> {
            return listOf(
                    arrayOf<Any?>(KotlinLanguage),
                    arrayOf<Any?>(JavaLanguage)
            )
        }
    }

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
        ConfigurationGenerator(
                language = language,
                folder = filePath).generate(packageName, types)

        // Then
        val list = mutableListOf(filePath)
        list.add(language.folderName())
        list.addAll(packageName.split("."))
        list.add("GraphQLConfiguration.${language.extension()}")

        Assert.assertTrue(File(list.joinToString(File.separator)).exists())

        val fileContent = File(list.joinToString(File.separator)).bufferedReader().lines().collect(Collectors.joining())

        val expectedFilePath = mutableListOf(".", "src", "test", "resources", "test-resources", "graphQLConfiguration.${language.extension()}Test")
        val expectedFileContent = File(expectedFilePath.joinToString(File.separator)).bufferedReader().lines().collect(Collectors.joining("\n"))

        Assert.assertEquals(cleanStringForAssertion(expectedFileContent), cleanStringForAssertion(fileContent))
    }
}