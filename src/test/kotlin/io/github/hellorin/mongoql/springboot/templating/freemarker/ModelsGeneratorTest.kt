package io.github.hellorin.mongoql.springboot.templating.freemarker

import io.github.hellorin.mongoql.utils.cleanStringForAssertion
import io.github.hellorin.mongoql.Attribute
import io.github.hellorin.mongoql.Type
import io.github.hellorin.mongoql.db.MongoDBParams
import io.github.hellorin.mongoql.springboot.configuration.KotlinLanguage
import io.github.hellorin.mongoql.springboot.configuration.Language
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.io.File
import java.util.stream.Collectors

@RunWith(Parameterized::class)
internal class ModelsGeneratorTest(private val language: Language) {
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
    fun `generate models classes`() {
        // Given
        val generationFilePath = listOf(".", "target", "tests", "testFolder", "graphql").joinToString(separator = File.separator)
        val packageName = "com.hellorin"
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

        val mongoDbParams = MongoDBParams.Builder(
                dbName = "db",
                colName = "col").build()

        // When
        ModelsGenerator(folder=generationFilePath).generate(
                packageName = packageName,
                graphQLTypes = types,
                mongoDbParams = mongoDbParams
        )

        // Then
        val filePath = mutableListOf(generationFilePath)
        filePath.add(language.folderName())
        filePath.addAll(packageName.split("."))
        filePath.add("Models.${language.extension()}")

        Assert.assertTrue(File(filePath.joinToString(File.separator)).exists())

        val fileContent = File(filePath.joinToString(File.separator)).bufferedReader().lines().collect(Collectors.joining())

        val expectedFileContent = File(listOf(".", "src", "test", "resources", "test-resources", "models.${language.extension()}Test").joinToString(File.separator)).bufferedReader().lines().collect(Collectors.joining("\n"))

        Assert.assertEquals(cleanStringForAssertion(expectedFileContent), cleanStringForAssertion(fileContent))
    }
}