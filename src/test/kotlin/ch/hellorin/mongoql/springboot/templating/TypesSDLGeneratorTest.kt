package ch.hellorin.mongoql.springboot.templating

import ch.hellorin.mongoql.utils.cleanStringForAssertion
import com.hellorin.mongoql.Attribute
import com.hellorin.mongoql.Type
import org.junit.Assert
import org.junit.Test
import java.io.File
import java.util.stream.Collectors

internal class TypesSDLGeneratorTest {
    @Test
    fun `generate sdl types file`() {
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

        // When
        TypesSDLGenerator(folder = filePath).generate(types)

        // Then
        val file = File(listOf(filePath, "types.graphqls").joinToString(File.separator))
        Assert.assertTrue(file.exists())

        val expectedFileContent = File(listOf(".", "src", "test", "resources", "test-resources", "types.graphqls").joinToString(File.separator)).bufferedReader().lines().collect(Collectors.joining("\n"))
        val fileContent = file.bufferedReader().lines().collect(Collectors.joining("\n"))
        Assert.assertEquals(cleanStringForAssertion(expectedFileContent), cleanStringForAssertion(fileContent))
    }
}