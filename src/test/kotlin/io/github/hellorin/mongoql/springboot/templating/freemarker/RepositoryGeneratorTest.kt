package io.github.hellorin.mongoql.springboot.templating.freemarker

import io.github.hellorin.mongoql.Attribute
import io.github.hellorin.mongoql.Type
import io.github.hellorin.mongoql.springboot.configuration.KotlinLanguage
import io.github.hellorin.mongoql.springboot.configuration.Language
import io.github.hellorin.mongoql.utils.cleanStringForAssertion
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.io.File
import java.util.stream.Collectors

@RunWith(Parameterized::class)
class RepositoryGeneratorTest(private val language: Language) {
    companion object {
        @JvmStatic
        @Parameterized.Parameters
        open fun data(): Collection<Array<Any?>> {
            return listOf(
                    arrayOf<Any?>(KotlinLanguage)
            )
        }
    }

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
        RepositoryGenerator(language = language, folder = generationFilePath).generate(packageName, types)

        // Then
        val filepath = mutableListOf(generationFilePath)
        filepath.add(language.folderName())
        filepath.addAll(packageName.split("."))
        filepath.add("Repository.${language.extension()}")

        assertTrue(File(filepath.joinToString(File.separator)).exists())

        val fileContent = File(filepath.joinToString(File.separator)).bufferedReader().lines().collect(Collectors.joining())
        val expectedFileContent = File(listOf(".", "src", "test", "resources", "test-resources", "repository.${language.extension()}Test").joinToString(File.separator)).bufferedReader().lines().collect(Collectors.joining("\n"))

        assertEquals(cleanStringForAssertion(expectedFileContent), cleanStringForAssertion(fileContent))
    }
}